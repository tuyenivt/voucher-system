package com.abc.banking.vendor.controller;

import com.abc.banking.vendor.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VendorController {

    private final VendorService service;

    @GetMapping("/get-voucher-code")
    public String code() {
        return service.getCode();
    }
}
