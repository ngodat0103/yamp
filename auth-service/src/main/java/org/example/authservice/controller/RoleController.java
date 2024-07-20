package org.example.authservice.controller;

import jakarta.validation.Valid;
import org.example.authservice.entity.Role;
import org.example.authservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping("/create")
    public Role createRole(@RequestBody @Valid Role role){
        return roleService.createRole(role);
    }


    @GetMapping("/getAccountRoles")
    public String getAccountRoles(JwtAuthenticationToken jwtAuthenticationToken){
        return jwtAuthenticationToken.getName() + " has roles: " + jwtAuthenticationToken.getAuthorities();
    }

}
