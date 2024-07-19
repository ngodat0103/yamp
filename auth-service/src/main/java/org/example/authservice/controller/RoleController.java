package org.example.authservice.controller;

import jakarta.validation.Valid;
import org.example.authservice.entity.Role;
import org.example.authservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping("/create")
    public Role createRole(@RequestBody @Valid Role role){
        return roleService.createRole(role);
    }

}
