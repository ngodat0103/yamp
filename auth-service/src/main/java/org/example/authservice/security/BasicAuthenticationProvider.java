package org.example.authservice.security;

import org.example.authservice.entity.Account;
import org.example.authservice.entity.AccountRole;
import org.example.authservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class BasicAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Optional<Account> account = accountRepository.findByUsername(username);
        if(account.isPresent() && passwordEncoder.matches(password, account.get().getPassword())){
            List<SimpleGrantedAuthority> authorities = convertToGrantedAuthorities(account.get().getAccountRole());
            return new UsernamePasswordAuthenticationToken(account.get().getAccountUuid().toString(), password, authorities);
        }
        throw new BadCredentialsException("username or password incorrect");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


    public List<SimpleGrantedAuthority> convertToGrantedAuthorities(Set<AccountRole> accountRoles){
        if(accountRoles == null){
            return Collections.emptyList();
        }

        return accountRoles.stream()
                .map(accountRole-> new SimpleGrantedAuthority(accountRole.getRole().getRoleName()))
                .toList();
    }
}
