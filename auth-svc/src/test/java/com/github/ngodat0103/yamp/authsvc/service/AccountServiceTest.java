package com.github.ngodat0103.yamp.authsvc.service;

import com.github.ngodat0103.yamp.authsvc.dto.account.AccountRequestDto;
import com.github.ngodat0103.yamp.authsvc.dto.account.AccountResponseDto;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.AccountMapper;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.AccountMapperImpl;
import com.github.ngodat0103.yamp.authsvc.exception.ConflictException;
import com.github.ngodat0103.yamp.authsvc.exception.NotFoundException;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Role;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.AccountRepository;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.RoleRepository;
import com.github.ngodat0103.yamp.authsvc.service.impl.AccountServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static com.github.ngodat0103.yamp.authsvc.Util.*;

@ExtendWith(MockitoExtension.class)
@Profile("uni-test")
class AccountServiceTest {

    private AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private RoleRepository roleRepository;

    private AccountServiceImpl accountService;

    private AccountRequestDto accountRequestDtoMock;
    private AccountResponseDto accountResponseDtoMock;
    private Account account;
    private Role role;

    @BeforeEach
    void setUp() {
        this.roleRepository = Mockito.mock(RoleRepository.class);
        KafkaTemplate kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        this.accountRepository = Mockito.mock(AccountRepository.class);
        AccountMapper accountMapper = new AccountMapperImpl();
        this.accountService = new AccountServiceImpl(accountRepository, accountMapper, passwordEncoder, roleRepository, kafkaTemplate);
        this.accountRequestDtoMock = AccountRequestDto.builder()
                .uuid(UUID.randomUUID().toString())
                .username("test")
                .password("password")
                .email("example@gmail.com")
                .roleName("CUSTOMER")
                .build();
        this.accountResponseDtoMock = AccountResponseDto.builder()
                .uuid(accountRequestDtoMock.getUuid())
                .username(accountRequestDtoMock.getUsername())
                .email(accountRequestDtoMock.getEmail())
                .roleName(accountRequestDtoMock.getRoleName())
                .build();
        this.account = accountMapper.mapToEntity(accountRequestDtoMock);
        this.role = new Role();
        role.setRoleName(accountRequestDtoMock.getRoleName());
        role.setUuid(UUID.randomUUID());
        role.setCreateAt(LocalDateTime.now());
        role.setLastModifiedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("Given already existing account with UUID, when register, then throw ConflictException")
    void givenUuidAlreadyExist_whenRegister_thenThrowConflictException() {
        given(accountRepository.existsById(account.getUuid())).willReturn(true);
        Assertions.assertThatThrownBy(() -> accountService.register(accountRequestDtoMock))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining(String.format(TEMPLATE_CONFLICT, "Account", "uuid", account.getUuid()));
    }

    @Test
    @DisplayName("Given already existing account with email, when register, then throw ConflictException")
    void giveAccountUsernameExists_whenRegister_thenThrowConflict() {
        given(accountRepository.existsById(account.getUuid())).willReturn(false);
        given(accountRepository.existsByUsername(account.getUsername())).willReturn(true);
        Assertions.assertThatThrownBy(() -> accountService.register(accountRequestDtoMock))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining(String.format(TEMPLATE_CONFLICT, "Account", "username", account.getUsername()));
    }
    @Test
    @DisplayName("Given already existing account with email, when register, then throw ConflictException")
    void giveAccountEmailExists_whenRegister_thenThrowConflict() {
        given(accountRepository.existsById(account.getUuid())).willReturn(false);
        given(accountRepository.existsByUsername(account.getUsername())).willReturn(false);
        given(accountRepository.existsByEmail(account.getEmail())).willReturn(true);
        Assertions.assertThatThrownBy(() -> accountService.register(accountRequestDtoMock))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining(String.format(TEMPLATE_CONFLICT, "Account", "email", account.getEmail()));
    }
    @Test
    @DisplayName("Given not exists account but not exists role when register then throw NotFoundException")
    void givenRoleNotExists_whenRegister_thenThrowNotFoundException() {
        given(accountRepository.existsById(account.getUuid())).willReturn(false);
        given(accountRepository.existsByUsername(account.getUsername())).willReturn(false);
        given(accountRepository.existsByEmail(account.getEmail())).willReturn(false);
        given(roleRepository.findRoleByRoleName(accountRequestDtoMock.getRoleName())).willReturn(java.util.Optional.empty());
        Assertions.assertThatThrownBy(() -> accountService.register(accountRequestDtoMock))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format(TEMPLATE_NOT_FOUND, "Role", "roleName", accountRequestDtoMock.getRoleName()));
    }

    @Test
    @DisplayName("Given nothing account and exist role when register then saved account")
    void givenAccountDto_whenRegister_thenReturnSavedAccountDto() {
        given(accountRepository.existsById(account.getUuid())).willReturn(false);
        given(accountRepository.existsByUsername(account.getUsername())).willReturn(false);
        given(accountRepository.existsByEmail(account.getEmail())).willReturn(false);
        given(roleRepository.findRoleByRoleName(any(String.class))).willReturn(java.util.Optional.of(role));
        Account accountResponse = new Account();
        accountResponse.setUuid(account.getUuid());
        accountResponse.setUsername(account.getUsername());
        accountResponse.setPassword(account.getPassword());
        accountResponse.setEmail(account.getEmail());
        accountResponse.setRole(role);
        accountResponse.setCreateAt(LocalDateTime.now());
        accountResponse.setLastModifiedAt(LocalDateTime.now());
        given(accountRepository.save(any(Account.class))).willReturn(accountResponse);
        AccountResponseDto result = accountService.register(accountRequestDtoMock);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(accountResponseDtoMock);
        verify(accountRepository).save(any(Account.class));
    }

//
//    @Test
//    void updateAccount_updatesExistingAccount() {
//        UUID uuid = UUID.randomUUID();
//        UpdateAccountDto updateAccountDto = UpdateAccountDto.builder()
//                .username("newUsername")
//                .email("test@gmail.com")
//                .roleName("CUSTOMER")
//                .build();
//        when(accountRepository.findById(any(UUID.class))).thenReturn(Optional.of(account));
//        when(roleRepository.findRoleByRoleName(any(String.class))).thenReturn(Optional.of(role));
//        when(accountRepository.save(any(Account.class))).thenReturn(account);
//        when(accountMapper.mapToDto(any(Account.class))).thenReturn(accountDto);
//
//        AccountDto result = accountService.updateAccount(updateAccountDto);
//        assertNotNull(result);
//        assertEquals(accountDto, result);
//    }
//
//    @Test
//    void getAccount_returnsAccountByUuid() {
//        UUID uuid = UUID.randomUUID();
//        when(accountRepository.findById(any(UUID.class))).thenReturn(Optional.of(account));
//        when(accountMapper.mapToDto(any(Account.class))).thenReturn(accountDto);
//
//        AccountDto result = accountService.getAccount(uuid);
//
//        assertNotNull(result);
//        assertEquals(accountDto, result);
//    }
//
//    @Test
//    void getAccounts_returnsAllAccounts() {
//        when(accountRepository.findAll()).thenReturn(List.of(account));
//        when(accountMapper.mapToDto(any(Account.class))).thenReturn(accountDto);
//
//        Set<AccountDto> result = accountService.getAccounts();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertTrue(result.contains(accountDto));
//    }
//
//    @Test
//    void getAccountFilter_returnsAccountsByRole() {
//        Set<String> roles = Set.of("USER");
//        when(roleRepository.findRolesByRoleNameIn(anySet())).thenReturn(Set.of(role));
//        when(role.getAccounts()).thenReturn(Set.of(account));
//        when(accountMapper.mapToDto(any(Account.class))).thenReturn(accountDto);
//
//        Set<AccountDto> result = accountService.getAccountFilter(roles, null, null);
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertTrue(result.contains(accountDto));
//    }
}