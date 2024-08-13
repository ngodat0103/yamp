package com.example.userservice;


import com.example.userservice.controller.AddressController;
import com.example.userservice.exception.CustomerNotFoundException;
import com.example.userservice.service.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.eq;


@WebMvcTest(value = AddressController.class)
@ActiveProfiles("local-dev")
public class AddressControllerTest {

    private final static String CUSTOMER_UUID_HEADER = "X-Customer-Uuid";
    @MockBean
    private AddressService addressService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getAddressWhenCustomerUuidNotFound() throws Exception {
        UUID customerUuid = UUID.randomUUID();
        when(addressService.getAddresses(eq(customerUuid))).thenThrow(new CustomerNotFoundException(customerUuid));
        mockMvc.perform(get("/api/v1/user/address")
                .header(CUSTOMER_UUID_HEADER, customerUuid.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Customer not found for UUID: " + customerUuid));

    }





//    @Test
//    public void getAddressTest() throws Exception {
//        UUID customerUuid = UUID.randomUUID();
//        AddressDto addressDto = AddressDto.builder()
//                .name("Ten gi do")
//                .cityName("Tphcm")
//                .phoneNumber("0123456789")
//                .province("Tphcm")
//                .street("101 Duong abc")
//                .ward("Tan Thanh")
//                .district("Tan Phu")
//                .addressType("addressType")
//                .build();
//
//        AddressResponseDto addressResponseDto = AddressResponseDto.builder()
//                .customerUuid(customerUuid)
//                .addresses(Set.of(addressDto))
//                .currentElements(1)
//                .totalElements(1)
//                .totalPages(1)
//                .currentPage(1)
//                .build();
//
//        when(addressService.getAddresses(eq(customerUuid))).thenReturn(addressResponseDto);
//        mockMvc.perform(get("/api/v1/user/address")
//                .header(CUSTOMER_UUID_HEADER, customerUuid.toString())
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.customerUuid").value(customerUuid.toString()))
//                .andExpect(jsonPath("$.currentElements").value(1))
//                .andExpect(jsonPath("$.totalElements").value(1))
//                .andExpect(jsonPath("$.totalPages").value(1))
//                .andExpect(jsonPath("$.currentPage").value(1))
//                .andExpect(jsonPath("$.addresses[0].name").value("Ten gi do"))
//                .andExpect(jsonPath("$.addresses[0].cityName").value("Tphcm"))
//                .andExpect(jsonPath("$.addresses[0].phoneNumber").value("0123456789"))
//                .andExpect(jsonPath("$.addresses[0].province").value("Tphcm"))
//                .andExpect(jsonPath("$.addresses[0].street").value("101 Duong abc"))
//                .andExpect(jsonPath("$.addresses[0].ward").value("Tan Thanh"))
//                .andExpect(jsonPath("$.addresses[0].district").value("Tan Phu"))
//                .andExpect(jsonPath("$.addresses[0].addressType").value("addressType"));
//
//
//    }


}
