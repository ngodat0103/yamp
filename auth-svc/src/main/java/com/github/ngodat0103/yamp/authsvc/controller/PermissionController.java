package com.github.ngodat0103.yamp.authsvc.controller;

import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionOperationsDto;
import com.github.ngodat0103.yamp.authsvc.service.impl.PermissionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/permissions")
@Tag(name = "Permissions", description = "Operations related to permissions management")
@RequiredArgsConstructor
public class PermissionController {

  private final PermissionServiceImpl permissionService;

  @PostMapping
  @Operation(summary = "Create a new permission")
  @ResponseStatus(HttpStatus.CREATED)
  public PermissionDto createPermission(@Valid @RequestBody PermissionDto permissionDto) {
    return permissionService.create(permissionDto);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get a permission by ID")
  public PermissionDto getPermissionById(@PathVariable Long id) {
    return permissionService.readById(id);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update an existing permission")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public PermissionDto updatePermission(
      @PathVariable Long id, @Valid @RequestBody PermissionDto permissionDto) {
    return permissionService.update(id, permissionDto);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a permission by ID")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePermission(@PathVariable Long id) {
    permissionService.deleteById(id);
  }

  @GetMapping
  @Operation(summary = "Get all permissions")
  public Set<PermissionDto> getAllPermissions() {
    return permissionService.findAll();
  }

  @GetMapping("/list-operations/{permission_id}")
  public PermissionOperationsDto getOperationsByPermissionId(
      @PathVariable(name = "permission_id") Long permissionId) {
    return permissionService.getOperations(permissionId);
  }

  @PostMapping("/add-operations/{permission_id}")
  @ResponseStatus(HttpStatus.CREATED)
  public PermissionOperationsDto addOperations(
      @PathVariable(name = "permission_id") Long permissionId,
      @RequestBody Set<Long> operationIds) {
    return permissionService.addOperations(permissionId, operationIds);
  }

  @DeleteMapping("/delete-operations/{permissionId}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public PermissionOperationsDto deleteOperations(
      @PathVariable Long permissionId, Set<Long> operationIds) {
    return permissionService.deleteOperations(permissionId, operationIds);
  }
}
