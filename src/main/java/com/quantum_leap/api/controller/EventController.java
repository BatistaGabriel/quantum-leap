package com.quantum_leap.api.controller;

import com.quantum_leap.api.domain.event.Event;
import com.quantum_leap.api.domain.event.EventRequestDTO;
import com.quantum_leap.api.domain.event.EventResponseDTO;
import com.quantum_leap.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Event> create(
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("date") Long date,
            @RequestParam("city") String city,
            @RequestParam("uf") String uf,
            @RequestParam("remote") Boolean remote,
            @RequestParam("eventUrl") String eventUrl,
            @RequestParam(value = "image",  required = false)MultipartFile image
            ){
        EventRequestDTO data = new EventRequestDTO(
                title, description, date, city,
                uf, remote, eventUrl, image
        );
        Event event = this.eventService.createEvent(data);

        return ResponseEntity.ok(event);
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getEvents(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size){
        List<EventResponseDTO> events = this.eventService.getUpcomingEvents(page, size);

        return ResponseEntity.ok(events);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EventResponseDTO>> getFilteredEvents(@RequestParam(defaultValue = "0")int page,
                                                                    @RequestParam(defaultValue = "10")int size,
                                                                    @RequestParam(required = false)String title,
                                                                    @RequestParam(required = false)String city,
                                                                    @RequestParam(required = false)String uf,
                                                                    @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date startDate,
                                                                    @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date endDate){
        List<EventResponseDTO> events = eventService.getFilteredEvents(page, size, title, city, uf, startDate, endDate);

        return ResponseEntity.ok(events);
    }
}
