//package com.example.yamp.usersvc.controller;
//import com.example.yamp.usersvc.dto.address.AddressDto;
//import com.example.yamp.usersvc.dto.address.AddressResponseDto;
//import com.example.yamp.usersvc.exception.NotFoundException;
//import com.example.yamp.usersvc.service.AddressService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import java.util.Set;
//import java.util.UUID;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.mockito.ArgumentMatchers.eq;
//
//
//
//@WebMvcTest(AddressController.class)
//@ActiveProfiles("unit-test")
//@AutoConfigureMockMvc(addFilters = false)
//class AddressControllerTest {
//    private final static String CUSTOMER_UUID_HEADER = "X-Customer-Uuid";
//    @MockBean
//    private AddressService addressService;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    ObjectMapper objectMapper;
//    AddressDto addressDto;
//
//    @BeforeEach
//    public void setUp() {
//        addressDto = AddressDto.builder()
//                .name("Ten gi do")
//                .cityName("Tphcm")
//                .phoneNumber("0123456789")
//                .province("Tphcm")
//                .street("101 Duong abc")
//                .ward("Tan Thanh")
//                .district("Tan Phu")
//                .addressType("addressType")
//                .build();
//    }
//
////    @Test
////    @WithMockUser(username = cus,roles = "CUSTOMER")
////    void getAddressWhenCustomerUuidNotFound() throws Exception {
////        when(addressService.getAddresses()).thenThrow(new NotFoundException("Customer not found for UUID: " + customerUuid));
////        mockMvc.perform(get("/address")
////                .header(CUSTOMER_UUID_HEADER, customerUuid.toString())
////                .contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isNotFound())
////                .andExpect(jsonPath("$.message")
////                        .value("Customer not found for UUID: " + customerUuid));
////    }
//
//    @Test
//    void getAddressWhenCustomerUuidIsNull() throws Exception {
//        mockMvc.perform(get("/address")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message")
//                        .value("Required request header 'X-Customer-Uuid' for method parameter type UUID is not present"));
//    }
//
////
////    @Test void updateAddressWhenAddressUuidNotFound() throws Exception {
////        UUID addressUuid = UUID.randomUUID();
////        UUID customerUuid = UUID.randomUUID();
////
////        doThrow(new NotFoundException("Address not found for UUID: " + addressUuid))
////                .when(addressService).updateAddress(customerUuid,addressUuid,addressDto);
////        mockMvc.perform(put("/address/{addressUuid}",addressUuid)
////                .header(CUSTOMER_UUID_HEADER,customerUuid.toString())
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(objectMapper.writeValueAsString(addressDto)))
////                .andExpect(status().isNotFound())
////                .andExpect(jsonPath("$.message")
////                        .value("Address not found for UUID: " + addressUuid));
////    }
//
//
//
//    @Test
//    void getAddressTest() throws Exception {
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
//        when(addressService.getAddresses(any())).thenReturn(addressResponseDto);
//        mockMvc.perform(get("/address")
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
//    }
//
//
//}
