package org.example.authservice.config;

import jakarta.annotation.PostConstruct;
import org.example.authservice.entity.Account;
import org.example.authservice.entity.AccountRole;
import org.example.authservice.entity.Role;
import org.example.authservice.repository.AccountRepository;
import org.example.authservice.repository.AccountRoleRepository;
import org.example.authservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class StartUp {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AccountRoleRepository accountRoleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @PostConstruct void init(){
       Account admin = accountRepository.save(new Account(Constants.adminUsername,passwordEncoder.encode(Constants.adminPassword)));
       Role roleAdmin =  roleRepository.save(new Role("ROLE_ADMIN"));
       Role  roleUser =  roleRepository.save(new Role("ROLE_USER"));
        AccountRole accountRole = new AccountRole(admin,roleAdmin);
        accountRoleRepository.save(accountRole);
    }
}
