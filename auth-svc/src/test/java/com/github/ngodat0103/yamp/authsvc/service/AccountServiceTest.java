package com.github.ngodat0103.yamp.authsvc.service;

import static com.github.ngodat0103.yamp.authsvc.Util.*;
import static org.mockito.BDDMockito.*;

import com.github.ngodat0103.yamp.authsvc.dto.account.AccountRegisterDto;
import com.github.ngodat0103.yamp.authsvc.dto.account.AccountResponseDto;
import com.github.ngodat0103.yamp.authsvc.dto.kafka.AccountTopicContent;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.AccountMapper;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.AccountMapperImpl;
import com.github.ngodat0103.yamp.authsvc.exception.ConflictException;
import com.github.ngodat0103.yamp.authsvc.exception.NotFoundException;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Role;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.AccountRepository;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.RoleRepository;
import com.github.ngodat0103.yamp.authsvc.service.impl.AccountServiceImpl;
import java.time.LocalDateTime;
import java.util.UUID;
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

@ExtendWith(MockitoExtension.class)
@Profile("uni-test")
class AccountServiceTest {

  private AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private RoleRepository roleRepository;

  private AccountServiceImpl accountService;

  private AccountRegisterDto accountRegisterDtoMock;
  private AccountResponseDto accountResponseDtoMock;
  private Account account;
  private Role role;

  private static final String uuidMock = UUID.randomUUID().toString();
  private static final String defaultRoleName = "CUSTOMER";

    @BeforeEach
  void setUp() {
    this.roleRepository = Mockito.mock(RoleRepository.class);
        KafkaTemplate<UUID, AccountTopicContent> kafkaTemplate = Mockito.mock(KafkaTemplate.class);
    this.accountRepository = Mockito.mock(AccountRepository.class);
      AccountMapper accountMapper = new AccountMapperImpl();
    this.accountService =
        new AccountServiceImpl(
            accountRepository, accountMapper, passwordEncoder, roleRepository, kafkaTemplate);
    this.accountRegisterDtoMock =
        AccountRegisterDto.builder()
            .username("test")
            .password("password")
            .email("example@gmail.com")
            .build();
    this.accountResponseDtoMock =
        AccountResponseDto.builder()
            .uuid(uuidMock)
            .username(accountRegisterDtoMock.getUsername())
            .email(accountRegisterDtoMock.getEmail())
            .roleName(defaultRoleName)
            .build();
    this.account = accountMapper.mapToEntity(accountRegisterDtoMock);
    this.role = new Role();
    role.setRoleName(defaultRoleName);
    role.setUuid(UUID.randomUUID());
    role.setCreateAt(LocalDateTime.now());
    role.setLastModifiedAt(LocalDateTime.now());
  }

  @Test
  @DisplayName(
      "Given already existing account with email, when register, then throw ConflictException")
  void giveAccountUsernameExists_whenRegister_thenThrowConflict() {
    given(accountRepository.existsByUsername(account.getUsername())).willReturn(true);
    Assertions.assertThatThrownBy(() -> accountService.register(accountRegisterDtoMock))
        .isInstanceOf(ConflictException.class)
        .hasMessageContaining(
            String.format(TEMPLATE_CONFLICT, "Account", "username", account.getUsername()));
  }

  @Test
  @DisplayName(
      "Given already existing account with email, when register, then throw ConflictException")
  void giveAccountEmailExists_whenRegister_thenThrowConflict() {
    given(accountRepository.existsByUsername(account.getUsername())).willReturn(false);
    given(accountRepository.existsByEmail(account.getEmail())).willReturn(true);
    Assertions.assertThatThrownBy(() -> accountService.register(accountRegisterDtoMock))
        .isInstanceOf(ConflictException.class)
        .hasMessageContaining(
            String.format(TEMPLATE_CONFLICT, "Account", "email", account.getEmail()));
  }

  @Test
  @DisplayName(
      "Given not exists account but not exists role when register then throw NotFoundException")
  void givenRoleNotExists_whenRegister_thenThrowNotFoundException() {
    given(accountRepository.existsByUsername(account.getUsername())).willReturn(false);
    given(accountRepository.existsByEmail(account.getEmail())).willReturn(false);
    given(roleRepository.findRoleByRoleName(defaultRoleName))
        .willReturn(java.util.Optional.empty());
    Assertions.assertThatThrownBy(() -> accountService.register(accountRegisterDtoMock))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(
            String.format(TEMPLATE_NOT_FOUND, "Role", "roleName", defaultRoleName));
  }

  @Test
  @DisplayName("Given an exist role when register then saved account")
  void givenAccountDto_whenRegister_thenReturnSavedAccountDto() {
    given(accountRepository.existsByUsername(account.getUsername())).willReturn(false);
    given(accountRepository.existsByEmail(account.getEmail())).willReturn(false);
    given(roleRepository.findRoleByRoleName(any(String.class)))
        .willReturn(java.util.Optional.of(role));
    Account accountResponse = new Account();
    accountResponse.setUuid(UUID.fromString(uuidMock));
    accountResponse.setUsername(account.getUsername());
    accountResponse.setPassword(account.getPassword());
    accountResponse.setEmail(account.getEmail());
    accountResponse.setRole(role);
    accountResponse.setCreateAt(LocalDateTime.now());
    accountResponse.setLastModifiedAt(LocalDateTime.now());

    given(accountRepository.save(any(Account.class))).willReturn(accountResponse);
    AccountResponseDto result = accountService.register(accountRegisterDtoMock);
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isEqualTo(accountResponseDtoMock);
    verify(accountRepository).save(any(Account.class));
    //    verify(kafkaTemplate.send("authsvc-topic",any(UUID.class),
    // any(AccountTopicContent.class)));
  }
}
