package com.github.ngodat0103.yamp.authsvc.controller.module;

import com.github.ngodat0103.yamp.authsvc.dto.permission.ModulesPermissionsDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.ModulesPermissions;
import com.github.ngodat0103.yamp.authsvc.service.impl.ModulesPermissionsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/modules-permissions")
@AllArgsConstructor
@SecurityRequirement(name = "oauth2")
@PreAuthorize("hasRole('ADMIN')")
@Tag(
    name = "Modules Permissions",
    description = "Operations related to modules permissions management")
public class ModulesPermissionsController {

  private final ModulesPermissionsServiceImpl modulesPermissionsService;

  @Operation(
      summary = "Get all modules permissions",
      description = "Retrieves a list of all modules permissions.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved list of modules permissions",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ModulesPermissions.class)))
      })
  @GetMapping(produces = "application/json")
  public ResponseEntity<List<ModulesPermissionsDto>> getAllModulesPermissions() {
    List<ModulesPermissionsDto> modulesPermissions = modulesPermissionsService.findAll();
    return ResponseEntity.ok(modulesPermissions);
  }

  @Operation(
      summary = "Create a new module permission",
      description = "Adds a new module permission to the system.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Module permission created successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ModulesPermissions.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input")
      })
  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public void createModulePermission(@RequestBody ModulesPermissionsDto modulePermission) {
    log.debug("Controller createModulePermission method called");
    modulesPermissionsService.create(modulePermission);
  }

  @Operation(
      summary = "Get module permission by ID",
      description = "Retrieves a module permission by its ID.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the module permission",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ModulesPermissions.class))),
        @ApiResponse(responseCode = "404", description = "Module permission not found")
      })
  @GetMapping(path = "/{id}", produces = "application/json")
  public ResponseEntity<ModulesPermissionsDto> getModulePermissionById(
      @Parameter(description = "ID of the module permission to be retrieved") @PathVariable
          Long id) {

      return ResponseEntity.ok( modulesPermissionsService.readById(id));
  }

  @Operation(
      summary = "Update a module permission",
      description = "Updates an existing module permission.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully updated the module permission",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ModulesPermissions.class))),
        @ApiResponse(responseCode = "404", description = "Module permission not found")
      })
  @PutMapping(path = "/{id}")
  public ResponseEntity<ModulesPermissionsDto> updateModulePermission(
      @Parameter(description = "ID of the module permission to be updated") @PathVariable Long id,
      @RequestBody ModulesPermissionsDto modulePermission) {
    ModulesPermissionsDto updatedModulePermission = modulesPermissionsService.update(id, modulePermission);
    return ResponseEntity.ok(updatedModulePermission);
  }

  @Operation(
      summary = "Delete a module permission",
      description = "Removes a module permission from the system by its ID.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "202", description = "Module permission deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Module permission not found")
      })
  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void deleteModulePermission(
      @Parameter(description = "ID of the module permission to be deleted") @PathVariable Long id) {
    log.debug("Controller deleteModulePermission method called");
    modulesPermissionsService.deleteById(id);
  }
}
