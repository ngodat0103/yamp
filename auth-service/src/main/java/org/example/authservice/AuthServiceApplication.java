package org.example.authservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.example.authservice.entity.Role;
import org.example.authservice.repository.RoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@OpenAPIDefinition
public class AuthServiceApplication {
    static void init(RoleRepository roleRepository) {
        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_CUSTOMER"));

    }

    public static void main(String[] args) {
       ApplicationContext ctx =  SpringApplication.run(AuthServiceApplication.class, args);
        RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
        init(roleRepository);
    }

}
