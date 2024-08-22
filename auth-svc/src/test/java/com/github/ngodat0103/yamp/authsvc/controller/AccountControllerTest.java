package com.github.ngodat0103.yamp.authsvc.controller;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.ngodat0103.yamp.authsvc.dto.AccountDto;
import com.github.ngodat0103.yamp.authsvc.exception.ConflictException;
import com.github.ngodat0103.yamp.authsvc.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("application-test")
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;
    @Autowired
    ObjectMapper objectMapper ;

    AccountDto accountDto = AccountDto.builder()
            .username("test")
            .password("password")
            .accountUuid(UUID.randomUUID().toString())
            .email("example@gmail.com")
            .build();

    @BeforeEach
    void setUp() {
      objectMapper = JsonMapper.builder().disable(MapperFeature.USE_ANNOTATIONS).build();
    }

    @Test
    void testRegisterAccountWhenNotFound() throws Exception {
        when(accountService.register(any(AccountDto.class))).thenReturn(accountDto);
        mockMvc.perform(post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(jsonPath("$.username").value(accountDto.getUsername()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(accountDto.getEmail()))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.accountUuid").value(accountDto.getAccountUuid().toString()));
    }
    @Test
    void testRegisterAccountWhenAccountUuidFound() throws Exception {
        doThrow(new ConflictException("AccountUuid is already exists!"))
                .when(accountService)
                .register(any(AccountDto.class));
        mockMvc.perform(post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("AccountUuid is already exists!"));
    }
    @Test
    void testRegisterAccountWhenUsernameFound() throws Exception {
        doThrow(new ConflictException("Username is already exists!"))
                .when(accountService)
                .register(any(AccountDto.class));

        mockMvc.perform(post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("Username is already exists!"));
    }
    @Test
    void testRegisterAccountWhenEmailFound() throws Exception {
        doThrow(new ConflictException("Email is already exists!"))
                .when(accountService)
                .register(any(AccountDto.class));
        mockMvc.perform(post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("Email is already exists!"));
    }

    @Test
    void testRegisterAccountWhenMissingField() throws Exception {
        accountDto.setAccountUuid(null);
        mockMvc.perform(post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("accountUuid is required"));
    }
    @Test void testRegisterAccountWhenFieldNotValid() throws Exception {
        accountDto.setEmail("example");
        mockMvc.perform(post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Email is not valid"));
    }

}
