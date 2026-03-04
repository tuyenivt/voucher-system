package com.abc.banking.voucher.service;

import com.abc.banking.voucher.model.Voucher;
import com.abc.banking.voucher.repository.VoucherRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherService {

    @Getter
    @Value("${vendor.service.url}")
    private String vendorServiceUrl;

    private final StringEncryptor stringEncryptor;
    private final RestTemplate restTemplate;
    private final VoucherRepository repository;

    public List<Voucher> getByPhone(String phone) {
        var result = repository.findByPhone(phone);
        result.forEach(v -> v.setCode(stringEncryptor.decrypt(v.getCode())));
        return result;
    }

    public Voucher getCode(Voucher newVoucher) {
        var response = restTemplate.getForEntity(vendorServiceUrl + "/get-voucher-code", String.class);
        newVoucher.setCode(stringEncryptor.encrypt(response.getBody()));
        Voucher voucher = repository.save(newVoucher);
        voucher.setCode(stringEncryptor.decrypt(voucher.getCode()));
        return voucher;
    }
}
