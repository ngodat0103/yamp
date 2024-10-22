package com.github.ngodat0103.yamp.authsvc.controller.permission;

import com.github.ngodat0103.yamp.authsvc.dto.permission.ModulePermissionDetailDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.ModulePermissionDto;
import com.github.ngodat0103.yamp.authsvc.service.impl.ModulesPermissionsServiceImpl;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/modules-permissions")
@AllArgsConstructor
@SecurityRequirement(name = "oauth2")
// @PreAuthorize("hasRole('ADMIN')")
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
                    schema = @Schema(implementation = ModulePermissionDetailDto.class)))
      })
  @GetMapping(produces = "application/json")
  public List<ModulePermissionDetailDto> getAllModulesPermissions() {
    return modulesPermissionsService.findAllDetail();
  }

  @Operation(
      summary = "Create a new module permission",
      description = "Adds a new module permission to the system.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Module permission created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
      })
  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public ModulePermissionDto createModulePermission(
      @RequestBody @Valid ModulePermissionDto modulePermission) {
    return modulesPermissionsService.create(modulePermission);
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
                    schema = @Schema(implementation = ModulePermissionDetailDto.class))),
        @ApiResponse(responseCode = "404", description = "Module permission not found")
      })
  @GetMapping(path = "/{id}", produces = "application/json")
  public ModulePermissionDetailDto getModulePermissionById(
      @Parameter(description = "ID of the module permission to be retrieved") @PathVariable
          Long id) {
    return modulesPermissionsService.readDetailById(id);
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
                    schema = @Schema(implementation = ModulePermissionDto.class))),
        @ApiResponse(responseCode = "404", description = "Module permission not found")
      })
  @PutMapping(path = "/{id}")
  public ModulePermissionDto updateModulePermission(
      @Parameter(description = "ID of the module permission to be updated") @PathVariable Long id,
      @RequestBody @Valid ModulePermissionDto updateModulePermissionDto) {
    return modulesPermissionsService.update(id, updateModulePermissionDto);
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
