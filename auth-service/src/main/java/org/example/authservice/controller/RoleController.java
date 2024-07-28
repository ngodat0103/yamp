package org.example.authservice.controller;

import jakarta.validation.Valid;
import org.example.authservice.entity.Role;
import org.example.authservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping("/create")
    public Role createRole(@RequestBody @Valid Role role){
        return roleService.createRole(role);
    }


    @GetMapping("getAccountRoles")
    public String getRole(@RequestHeader(value = "x-account-uuid") UUID accountUuid){
        return "get access ok with " + accountUuid.toString();
    }

}
