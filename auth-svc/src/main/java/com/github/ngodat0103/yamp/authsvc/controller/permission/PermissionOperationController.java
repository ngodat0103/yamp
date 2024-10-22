package com.github.ngodat0103.yamp.authsvc.controller.permission;

import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionOperationDetailDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionOperationDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.PermissionOperation;
import com.github.ngodat0103.yamp.authsvc.service.impl.PermissionsOperationsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/permissions-operations")
@AllArgsConstructor
@SecurityRequirement(name = "oauth2")
// @PreAuthorize("hasRole('ADMIN')")
@Tag(
    name = "Permissions Operations",
    description = "Operations related to permissions operations management")
public class PermissionOperationController {

  private final PermissionsOperationsServiceImpl permissionsOperationsService;

  @Operation(
      summary = "Get all permissions operations",
      description = "Retrieves a list of all permissions operations.")
  @GetMapping(produces = "application/json")
  public List<PermissionOperationDetailDto> getAllPermissionsOperations() {
    return permissionsOperationsService.readAllDetail();
  }

  @Operation(
      summary = "Get a permission operation by ID",
      description = "Retrieves a permission operation by its ID.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the permission operation",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PermissionOperationDetailDto.class))),
        @ApiResponse(responseCode = "404", description = "Permission operation not found.")
      })
  @GetMapping(value = "/{id}", produces = "application/json")
  public PermissionOperationDetailDto getPermissionOperationById(
      @Parameter(description = "ID of the permission operation to be retrieved") @PathVariable
          Long id) {
    return permissionsOperationsService.readDetailById(id);
  }

  @Operation(
      summary = "Create a new permission operation",
      description = "Creates a new permission operation.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Successfully created the permission operation.",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PermissionOperation.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input")
      })
  @PostMapping(consumes = "application/json", produces = "application/json")
  public PermissionOperationDto createPermissionOperation(
      @RequestBody @Valid PermissionOperationDto permissionOperationDto) {
    return permissionsOperationsService.create(permissionOperationDto);
  }

  @PutMapping(path = "{id}")
  public PermissionOperationDto updatePermissionOperation(
      @PathVariable Long id, @RequestBody @Valid PermissionOperationDto permissionOperationDto) {
    return permissionsOperationsService.update(id, permissionOperationDto);
  }

  @DeleteMapping(path = "{id}")
  public void delete(@PathVariable Long id) {
    permissionsOperationsService.deleteById(id);
  }
}
