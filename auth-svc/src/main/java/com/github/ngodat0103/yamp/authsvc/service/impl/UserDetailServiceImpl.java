package com.github.ngodat0103.yamp.authsvc.service.impl;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final static String ROLE_PREFIX = "ROLE_";
    public UserDetailServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.withUsername(account.getUuid().toString())
                .password(account.getPassword())
                .disabled(!account.isEnabled())
                .accountExpired(!account.isAccountNonExpired())
                .accountLocked(!account.isAccountNonLocked())
                .credentialsExpired(!account.isCredentialsNonExpired())
                .authorities(ROLE_PREFIX + account.getRole().getRoleName())
                .build();
    }
}
