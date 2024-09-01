//package com.github.ngodat0103.yamp.authsvc.service;
//
//
//import com.github.ngodat0103.yamp.authsvc.exception.ConflictException;
//import com.github.ngodat0103.yamp.authsvc.exception.NotFoundException;
//import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
//import com.github.ngodat0103.yamp.authsvc.persistence.repository.AccountRepository;
//import com.github.ngodat0103.yamp.authsvc.service.impl.RoleServiceImpl;
//import lombok.AllArgsConstructor;
//import lombok.Setter;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.context.support.WithSecurityContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.mockito.BDDMockito.*;
//
//
//@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("unit-test")
//public class RoleServiceTest {
//    AccountRepository accountRepository;
//    RoleService roleService;
//
//
//    private final static String accountUuid = "1a35d863-0cd9-4bc1-8cc4-f4cddca97721";
//
//
//    @BeforeEach
//    public void setUp() {
//        accountRepository = mock(AccountRepository.class);
//        roleService = new RoleServiceImpl(accountRepository);
//    }
//
//
//    @Test
//    @DisplayName("Create role when account exists")
//    @WithMockUser(authorities = "ROLE_ADMIN",username = accountUuid)
//    public void givenRole_whenAddRole_thenReturnSuccess() {
//        Account account = new Account();
//        account.setAccountUuid(UUID.fromString(accountUuid));
//        account.setRoles(new HashSet<>());
//        account.setEmail("example@gmail.com");
//        account.setUsername("test");
//
//        given(accountRepository.findById(UUID.fromString(accountUuid))).willReturn(Optional.of(account));
//        roleService.addRole("test");
//        verify(accountRepository).save(account);
//
//    }
//
//    @Test
//    @DisplayName("Create role when account does not exist")
//    @WithMockUser(authorities = "ROLE_ADMIN",username = accountUuid)
//    public void givenRole_whenAddRole_thenReturnFail() {
//        given(accountRepository.findById(UUID.fromString(accountUuid))).willReturn(Optional.empty());
//        Assertions.assertThatThrownBy(() -> roleService.addRole("test"))
//                .isInstanceOf(NotFoundException.class)
//                .hasMessage("AccountUuid not found");
//    }
//}
