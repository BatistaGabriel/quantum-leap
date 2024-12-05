package com.quantum_leap.api.repository;

import com.quantum_leap.api.domain.voucher.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoucherRepository extends JpaRepository<Voucher, UUID> {
}
