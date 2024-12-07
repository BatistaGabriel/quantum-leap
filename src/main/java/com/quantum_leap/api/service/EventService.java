package com.quantum_leap.api.service;

import com.quantum_leap.api.domain.event.Event;
import com.quantum_leap.api.domain.event.EventRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class EventService {
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

        return event;
    }

    private String uploadImage(MultipartFile file){
        return null;
    }
}
