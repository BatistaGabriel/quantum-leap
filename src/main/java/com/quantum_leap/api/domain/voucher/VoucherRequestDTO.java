package com.quantum_leap.api.domain.voucher;

public record VoucherRequestDTO(
        String code,
        Integer discount,
        Long valid
) {
}
