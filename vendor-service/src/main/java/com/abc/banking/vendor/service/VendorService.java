package com.abc.banking.vendor.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class VendorService {

    @Getter
    @Value("${vendor.voucher.url}")
    private String vendorVoucherUrl;

    private final RestTemplate restTemplate;

    public String getCode() {
        var response = restTemplate.getForEntity(vendorVoucherUrl, String.class);
        return response.getBody();
    }
}
