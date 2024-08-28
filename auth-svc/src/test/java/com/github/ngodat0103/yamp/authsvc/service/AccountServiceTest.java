package com.github.ngodat0103.yamp.authsvc.service;
import com.github.ngodat0103.yamp.authsvc.dto.AccountDto;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.AccountMapper;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.AccountMapperImpl;
import com.github.ngodat0103.yamp.authsvc.exception.ConflictException;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.AccountRepository;
import com.github.ngodat0103.yamp.authsvc.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    private AccountService accountService;
    private AccountRepository accountRepository;
    private AccountMapper accountMapper;


    private final AccountDto accountDto = AccountDto.builder()
            .username("test")
            .email("example@gmail.com")
            .accountUuid(UUID.randomUUID().toString())
            .password("password")
            .build();

    @BeforeEach
    public void setUp(){
        this.accountRepository = Mockito.mock(AccountRepository.class);
        this.accountMapper = new AccountMapperImpl();
        this.accountService = new AccountServiceImpl(accountRepository,accountMapper);
    }



    @Test
    @DisplayName("Test register new account")
    public void givenAccountDto_whenCreateAccount_thenReturnAccountDto(){
        Account account = accountMapper.mapToEntity(accountDto);

        given(accountRepository.save(any())).willReturn(account);

        AccountDto result = accountService.register(accountDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(accountDto.getUsername(),result.getUsername());
        Assertions.assertEquals(accountDto.getEmail(),result.getEmail());
        Assertions.assertEquals(accountDto.getAccountUuid(),result.getAccountUuid());
        Assertions.assertNull(result.getPassword());
    }

    @Test
    @DisplayName("Test register account but accountUuid is already exists")
    public void givenAccountDto_whenCreateAccount_thenThrowConflictExceptionWithUUidExists() {
        UUID accountUuid = UUID.fromString(accountDto.getAccountUuid());
        given(accountRepository.existsById(accountUuid)).willReturn(true);
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> accountService.register(accountDto))
                .isInstanceOf(ConflictException.class)
                .hasMessage("AccountUuid is already exists!");
    }

    @Test
    @DisplayName("Test register account but username is already exists")
    public void givenAccountDto_whenCreateAccount_thenThrowConflictExceptionWithUsernameExists() {
        given(accountRepository.existsById(UUID.fromString(accountDto.getAccountUuid()))).willReturn(false);
        given(accountRepository.existsByUsername(accountDto.getUsername())).willReturn(true);
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> accountService.register(accountDto))
                .isInstanceOf(ConflictException.class)
                .hasMessage("username is already exists!");
    }

    @Test
    @DisplayName("Test register account but email is already exists")
    public void givenAccountDto_whenCreateAccount_thenThrowConflictExceptionWithEmailExists() {
        given(accountRepository.existsById(UUID.fromString(accountDto.getAccountUuid()))).willReturn(false);
        given(accountRepository.existsByUsername(accountDto.getUsername())).willReturn(false);
        given(accountRepository.existsByEmail(accountDto.getEmail())).willReturn(true);
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> accountService.register(accountDto))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Email is already exists!");
    }


}
