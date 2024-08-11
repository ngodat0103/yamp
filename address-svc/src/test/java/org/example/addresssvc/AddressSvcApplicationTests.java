package org.example.addresssvc;

import org.example.addresssvc.dto.AddressDto;
import org.example.addresssvc.persistence.document.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddressSvcApplicationTests {
    @Autowired
    WebTestClient webTestClient ;
    private Address address;

    private final  String baseUri = "/api/v1/user/address";

    @Test
    void contextLoads() {
    }

    @Test
    @Order(1)
    public void testCreateAddress(){
        Address address = new Address();
        address.setCustomerUuid(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"));
        address.setName("Home 1");
        address.setCityName("Ho Chi Minh");
        address.setPhoneNumber("0123456789");
        address.setProvince("Ho Chi Minh");
        address.setStreet("123 bla bla bla");
        address.setDistrict("Tan Phu");
        address.setWard("Tan Thanh");
        address.setAddressType("Nha Rieng");

        webTestClient.post()
                .uri(baseUri)
                .bodyValue(address)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @Order(2)
    public void testGetAddresses(){
        UUID customerUuid = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        String query = String.format("?customerUuid=%s", customerUuid);
        webTestClient.get().uri(baseUri.concat(query))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.customerUuid").isEqualTo(customerUuid.toString())
                .jsonPath("$.elements").isEqualTo(1);

    }

}
