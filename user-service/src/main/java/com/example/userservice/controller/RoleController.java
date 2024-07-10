package com.example.userservice.controller;

import com.example.userservice.dto.model.RoleDto;
import com.example.userservice.repositories.RoleRepository;
import com.example.userservice.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping(produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("#{hasRole('ROLE_ADMIN')}")
    public RoleDto createRole(@RequestBody @Valid RoleDto newRoleDto){
        return roleService.createRole(newRoleDto);
    }

    @GetMapping(produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<RoleDto> getAllRoles(){
        return roleService.getAllRoles();
    }
    @GetMapping(value = "/{roleId}", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RoleDto getRole(@PathVariable String roleId){
        return roleService.getRole(roleId);
    }

    @DeleteMapping(value = "/{roleID}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable Long roleID){
        roleService.deleteRole(roleID);
    }
}
