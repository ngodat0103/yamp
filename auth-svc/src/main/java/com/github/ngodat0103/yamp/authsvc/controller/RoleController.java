package com.github.ngodat0103.yamp.authsvc.controller;

import com.github.ngodat0103.yamp.authsvc.dto.role.RoleDto;
import com.github.ngodat0103.yamp.authsvc.service.impl.RoleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/roles")
public class RoleController {

  private final RoleServiceImpl roleService;

  public RoleController(RoleServiceImpl roleService) {
    this.roleService = roleService;
  }

  @Operation(summary = "Create a new role")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Role created successfully"),
        @ApiResponse(responseCode = "409", description = "Role already exists")
      })
  @PostMapping
  public RoleDto createRole(@RequestBody @Valid RoleDto roleDto) {
    return roleService.create(roleDto);
  }

  @Operation(summary = "Get all roles")
  @ApiResponse(responseCode = "200", description = "Found roles")
  @GetMapping
  public List<RoleDto> getAllRoles() {
    return roleService.findAll();
  }

  @Operation(summary = "Get a role by ID")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Role found"),
        @ApiResponse(responseCode = "404", description = "Role not found")
      })
  @GetMapping("/{id}")
  public RoleDto getRoleById(
      @Parameter(description = "ID of the role to be obtained") @PathVariable Long id) {
    return roleService.readById(id);
  }

  @Operation(summary = "Update a role")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "202", description = "Role updated successfully"),
        @ApiResponse(responseCode = "404", description = "Role not found"),
        @ApiResponse(responseCode = "409", description = "Role name conflict")
      })
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public RoleDto updateRole(
      @Parameter(description = "ID of the role to be updated") @PathVariable Long id,
      @RequestBody @Valid RoleDto roleDto) {
    return roleService.update(id, roleDto);
  }

  @Operation(summary = "Delete a role")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Role deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Role not found")
      })
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteRole(
      @Parameter(description = "ID of the role to be deleted") @PathVariable Long id) {
    roleService.deleteById(id);
  }
}
