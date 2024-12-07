package com.quantum_leap.api.domain.event;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public record EventRequestDTO(
        String title,
        String description,
        Date date,
        String city,
        String uf,
        Boolean remote,
        String eventUrl,
        MultipartFile image
) {
}
