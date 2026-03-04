package com.abc.banking.voucher.controller;

import com.abc.banking.voucher.model.Voucher;
import com.abc.banking.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService service;

    @GetMapping("/vouchers")
    public List<Voucher> byPhone(@RequestParam(required = false) String phone) {
        return Optional.ofNullable(phone).map(service::getByPhone).orElseGet(Collections::emptyList);
    }

    @PostMapping("/vouchers")
    public Voucher newVoucher(@RequestBody Voucher newVoucher) {
        return service.getCode(newVoucher);
    }
}
