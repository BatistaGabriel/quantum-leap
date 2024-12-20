package com.quantum_leap.api.domain.voucher;

import com.quantum_leap.api.domain.event.Event;

import java.util.Date;

public record VoucherRequestDTO(
        String code,
        Integer discount,
        Long valid
) {
}
