package com.abc.banking.voucher.repository;

import com.abc.banking.voucher.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    List<Voucher> findByPhone(String phone);
}
