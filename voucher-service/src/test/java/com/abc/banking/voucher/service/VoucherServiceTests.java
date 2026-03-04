package com.abc.banking.voucher.service;

import com.abc.banking.voucher.model.Voucher;
import com.abc.banking.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class VoucherServiceTests {

    @Autowired
    private VoucherService service;

    @MockitoBean
    private VoucherRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void beforeEach() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    @DisplayName("Test getByPhone")
    void testGetByPhone() {
        // Setup our mock
        List<Voucher> vouchers = List.of(
                new Voucher("12345", "d2a9nRzhFZIiv923Y2gz7eDjJCTl2JdkVMMRgjcVX60vEMeWH2YtfVfAhXU43eUz"),
                new Voucher("12345", "l3nsNDA7MKJHGnJQOJYtRjfNvwBPI1kworX/ksE/7/FW17TaSSMzK1ru1CJqC12t")
        );
        doReturn(vouchers).when(repository).findByPhone("12345");

        // Execute the service call
        List<Voucher> results = service.getByPhone("12345");

        Assertions.assertEquals(2, results.size(), "getByPhone should return 2 vouchers");
    }

    @Test
    @DisplayName("Test getCode")
    void testGetCode() throws Exception {
        // Setup our mock
        Voucher newEncryptedVoucher = new Voucher("12345", "d2a9nRzhFZIiv923Y2gz7eDjJCTl2JdkVMMRgjcVX60vEMeWH2YtfVfAhXU43eUz");
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(service.getVendorServiceUrl() + "/get-voucher-code")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(newEncryptedVoucher.getCode())
                );
        doReturn(newEncryptedVoucher).when(repository).save(any());

        // Execute the service call
        Voucher returnedVoucher = service.getCode(new Voucher("12345", null));

        Assertions.assertEquals(returnedVoucher, new Voucher("12345", "aaaaaa"));
    }
}
