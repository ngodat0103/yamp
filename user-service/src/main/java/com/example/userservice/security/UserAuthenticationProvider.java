package com.example.userservice.security;

import com.example.userservice.entity.Role;
import com.example.userservice.entity.User;
import com.example.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String pwd = authentication.getCredentials().toString();
        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException("Invalid username or password");
        if(passwordEncoder.matches(pwd,user.getPassword())){
            return new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),getAuthorities(user.getRoles()));
        }
        else
            throw new BadCredentialsException("Invalid username or password");

    }

    private List<GrantedAuthority> getAuthorities(Set<Role> roles){
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (Role role: roles)
            authorityList.add(new SimpleGrantedAuthority(role.getRoleName()));

        return authorityList;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return  UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
