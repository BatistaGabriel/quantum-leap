package com.quantum_leap.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.quantum_leap.api.domain.event.Event;
import com.quantum_leap.api.domain.event.EventRequestDTO;
import com.quantum_leap.api.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private AmazonS3 amazonS3Client;
    @Value("${aws.bucket.name}")
    private String bucketName;
    @Autowired
    private EventRepository repository;

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

        return event;
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
}
