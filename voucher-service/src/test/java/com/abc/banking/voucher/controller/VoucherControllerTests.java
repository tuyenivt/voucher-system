package com.abc.banking.voucher.controller;

import com.abc.banking.voucher.model.Voucher;
import com.abc.banking.voucher.service.VoucherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class VoucherControllerTests {
    @MockitoBean
    private VoucherService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /vouchers?phone=12345 - Success")
    void testGetVoucherByPhoneSuccess() throws Exception {
        // Setup our mocked service
        List<Voucher> vouchers = List.of(new Voucher("12345", "voucher-code-1"), new Voucher("12345", "voucher-code-2"));
        doReturn(vouchers).when(service).getByPhone("12345");

        // Execute the GET request
        mockMvc.perform(get("/vouchers?phone={phone}", 12345))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$[0].phone", is("12345")))
                .andExpect(jsonPath("$[0].code", is("voucher-code-1")));
    }

    @Test
    @DisplayName("POST /vouchers - Success")
    void testCreateVoucher() throws Exception {
        // Setup mocked service
        doReturn(new Voucher("12345", "voucher-code-1")).when(service).getCode(new Voucher("12345", null));

        mockMvc.perform(post("/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new Voucher("12345", null))))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.phone", is("12345")))
                .andExpect(jsonPath("$.code", is("voucher-code-1")));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
