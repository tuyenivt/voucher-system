package com.abc.banking.vendor.controller;

import com.abc.banking.vendor.service.VendorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class VendorControllerTests {
    @MockitoBean
    private VendorService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /get-voucher-code - Success")
    void testGetVoucherCodeSuccess() throws Exception {
        // Set up our mocked service
        doReturn("voucher-code-1").when(service).getCode();

        // Execute the GET request
        mockMvc.perform(get("/get-voucher-code"))

                // Validate the response code
                .andExpect(status().isOk())

                // Validate the returned fields
                .andExpect(content().string("voucher-code-1"));
    }
}
