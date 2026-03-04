package com.abc.banking.voucher.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

    private @Id
    @GeneratedValue Long id;
    private String phone;
    private String code;

    public Voucher(String phone, String code) {
        this.phone = phone;
        this.code = code;
    }
}
