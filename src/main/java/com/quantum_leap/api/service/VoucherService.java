package com.quantum_leap.api.service;

import com.quantum_leap.api.domain.event.Event;
import com.quantum_leap.api.domain.voucher.Voucher;
import com.quantum_leap.api.domain.voucher.VoucherRequestDTO;
import com.quantum_leap.api.repository.EventRepository;
import com.quantum_leap.api.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class VoucherService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private VoucherRepository voucherRepository;

    public Voucher addVoucherToEvent(UUID eventId, VoucherRequestDTO data){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Voucher voucher = new Voucher();
        voucher.setCode(data.code());
        voucher.setDiscount(data.discount());
        voucher.setValid(new Date(data.valid()));
        voucher.setEvent(event);

        voucherRepository.save(voucher);

        return voucher;
    }
}
