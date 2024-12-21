package com.quantum_leap.api.controller;

import com.quantum_leap.api.domain.voucher.Voucher;
import com.quantum_leap.api.domain.voucher.VoucherRequestDTO;
import com.quantum_leap.api.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/voucher")
@RequiredArgsConstructor
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Voucher> addVoucherToEvent(
            @PathVariable UUID eventId,
            @RequestBody VoucherRequestDTO data
            ){
        Voucher voucher = voucherService.addVoucherToEvent(eventId, data);

        return ResponseEntity.ok(voucher);
    }
}
