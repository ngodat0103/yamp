package com.github.ngodat0103.yamp.authsvc.controller;

import com.github.ngodat0103.yamp.authsvc.dto.ModuleDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Module;
import com.github.ngodat0103.yamp.authsvc.service.impl.ModuleServiceImpl;
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
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/modules")
@AllArgsConstructor
@SecurityRequirement(name = "oauth2")
// @PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Modules", description = "Operations related to modules management")
public class ModuleController {

  private final ModuleServiceImpl modulesService;

  @Operation(summary = "Get all modules", description = "Retrieves a list of all modules.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved list of modules",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Module.class)))
      })
  @GetMapping(produces = "application/json")
  public ResponseEntity<List<ModuleDto>> getAllModules() {
    List<ModuleDto> modules = modulesService.findAll();
    return ResponseEntity.ok(modules);
  }

  @Operation(summary = "Create a new module", description = "Adds a new module to the system.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Module created successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Module.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input")
      })
  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public ModuleDto createModule(@RequestBody ModuleDto module) {
    log.debug("Controller createModule method called");
    return modulesService.create(module);
  }

  @Operation(summary = "Get module by ID", description = "Retrieves a module by its ID.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the module",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Module.class))),
        @ApiResponse(responseCode = "404", description = "Module not found")
      })
  @GetMapping(path = "/{id}", produces = "application/json")
  public ResponseEntity<ModuleDto> getModuleById(
      @Parameter(description = "ID of the module to be retrieved") @PathVariable Long id) {
    ModuleDto module = modulesService.readById(id);
    return ResponseEntity.of(Optional.of(module));
  }

  @Operation(summary = "Update a module", description = "Updates an existing module.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully updated the module",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Module.class))),
        @ApiResponse(responseCode = "404", description = "Module not found")
      })
  @PutMapping(path = "/{id}")
  public ResponseEntity<ModuleDto> updateModule(
      @Parameter(description = "ID of the module to be updated") @PathVariable Long id,
      @RequestBody ModuleDto module) {
    ModuleDto updatedModule = modulesService.update(id, module);
    return ResponseEntity.ok(updatedModule);
  }

  @Operation(
      summary = "Delete a module",
      description = "Removes a module from the system by its ID.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "202", description = "Module deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Module not found")
      })
  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void deleteModule(
      @Parameter(description = "ID of the module to be deleted") @PathVariable Long id) {
    log.debug("Controller deleteModule method called");
    modulesService.deleteById(id);
  }
}
