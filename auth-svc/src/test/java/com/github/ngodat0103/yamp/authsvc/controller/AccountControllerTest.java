package com.github.ngodat0103.yamp.authsvc.controller;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.ngodat0103.yamp.authsvc.dto.AccountDto;
import com.github.ngodat0103.yamp.authsvc.exception.ConflictException;
import com.github.ngodat0103.yamp.authsvc.service.AccountService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
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
@ActiveProfiles("unit-test")
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
            .uuid(UUID.randomUUID().toString())
            .email("example@gmail.com")
            .build();

    @BeforeEach
    void setUp() {
      objectMapper = JsonMapper.builder().disable(MapperFeature.USE_ANNOTATIONS).build();
    }


    @Test
    void testRegisterAccountWhenNotFound() throws Exception {
        when(accountService.register(any(AccountDto.class))).thenReturn(accountDto);
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(jsonPath("$.username").value(accountDto.getUsername()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(accountDto.getEmail()))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.accountUuid").value(accountDto.getUuid()));
    }
    @Test
    void testRegisterAccountWhenAccountUuidFound() throws Exception {
        doThrow(new ConflictException("AccountUuid is already exists!"))
                .when(accountService)
                .register(any(AccountDto.class));
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Already exists"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.type").value("https://problems-registry.smartbear.com/already-exists"))
                .andExpect(jsonPath("$.detail").value("AccountUuid is already exists!"));
    }
    @Test
    void testRegisterAccountWhenUsernameFound() throws Exception {
        doThrow(new ConflictException("Username is already exists!"))
                .when(accountService)
                .register(any(AccountDto.class));

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Already exists"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.type").value("https://problems-registry.smartbear.com/already-exists"))
                .andExpect(jsonPath("$.detail").value("Username is already exists!"));
    }
    @Test
    void testRegisterAccountWhenEmailFound() throws Exception {
        doThrow(new ConflictException("Email is already exists!"))
                .when(accountService)
                .register(any(AccountDto.class));
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Already exists"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.type").value("https://problems-registry.smartbear.com/already-exists"))
                .andExpect(jsonPath("$.detail").value("Email is already exists!"));
    }

    @Test
    void testRegisterAccountWhenMissingField() throws Exception {
        accountDto.setUuid(null);
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.type").value("https://problems-registry.smartbear.com/missing-body-property"))
                .andExpect(jsonPath("$.title").value("Validation Error."))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.detail").value("The request is not valid."));
    }
    @Test
    void testRegisterAccountWhenFieldNotValid() throws Exception {
        accountDto.setEmail("example");
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())

                .andExpect(jsonPath("$.type").value("https://problems-registry.smartbear.com/missing-body-property"))
                .andExpect(jsonPath("$.title").value("Validation Error."))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.detail").value("The request is not valid."));
    }

}
