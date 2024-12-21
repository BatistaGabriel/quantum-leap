package com.quantum_leap.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.quantum_leap.api.domain.event.Event;
import com.quantum_leap.api.domain.event.EventRequestDTO;
import com.quantum_leap.api.domain.event.EventResponseDTO;
import com.quantum_leap.api.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class EventService {
    @Autowired
    private AmazonS3 amazonS3Client;
    @Value("${aws.bucket.name}")
    private String bucketName;
    @Autowired
    private EventRepository repository;
    @Autowired
    private AddressService addressService;

    public Event createEvent(EventRequestDTO data){
        String imgUrl = null;
        if(data.image() != null){
            imgUrl = this.uploadImage(data.image());
        }

        Event event = new Event();
        event.setTitle(data.title());
        event.setDescription(data.description());
        event.setEventUrl(data.eventUrl());
        event.setDate(new Date(data.date()));
        event.setImageUrl(imgUrl);
        event.setRemote(data.remote());

        repository.save(event);

        if(!data.remote()){
            this.addressService.createAddress(data, event);
        }

        return event;
    }

    public List<EventResponseDTO> getUpcomingEvents(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = this.repository.findUpcomingEvents(new Date(), pageable);

        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() != null ? event.getAddress().getCity() : "N/A",
                event.getAddress() != null ? event.getAddress().getUf() : "N/A",
                event.getRemote(),
                event.getEventUrl(),
                event.getImageUrl()
        )).stream().toList();
    }

    public List<EventResponseDTO> getFilteredEvents(int page,
                                                    int size,
                                                    String title,
                                                    String city,
                                                    String uf,
                                                    Date startDate,
                                                    Date endDate){
        title = (title != null) ? title : "";
        city = (city != null) ? title : "";
        uf = (uf != null) ? uf : "";
        startDate = (startDate != null) ? startDate: new Date();
        endDate = (endDate != null) ? endDate: getDateWith30DaysAhead();

        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = this.repository.findFilteredEvents(title, city, uf, startDate, endDate, pageable);

        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() != null ? event.getAddress().getCity() : "N/A",
                event.getAddress() != null ? event.getAddress().getUf() : "N/A",
                event.getRemote(),
                event.getEventUrl(),
                event.getImageUrl()
        )).stream().toList();
    }

    private String uploadImage(MultipartFile multipartFile){
        String fileName = UUID.randomUUID()+"-"+multipartFile.getOriginalFilename();

        try {
            File file = this.convertMultipartToFile(multipartFile);
            amazonS3Client.putObject(bucketName,fileName, file);
            file.delete();

            return amazonS3Client.getUrl(bucketName, fileName).toString();
        } catch (Exception exception) {
            throw new RuntimeException("error while uploading image: "+exception.getMessage(), exception);
        }
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();

        return convertedFile;
    }

    private Date getDateWith30DaysAhead(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);

        return calendar.getTime();
    }
}
