package org.example.authservice.service.impl;

import org.example.authservice.entity.Account;
import org.example.authservice.repository.AccountRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
public class UserDetailServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;
    public UserDetailServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsernameOrEmail(username,username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<SimpleGrantedAuthority> authorities= account.getAccountRole().stream()
                .map(accountRole -> new SimpleGrantedAuthority(accountRole.getRole().getRoleName()))
                .toList();
        return User.withUsername(username)
                .password(account.getPassword())
                .authorities(authorities)
                .build();

    }
}
