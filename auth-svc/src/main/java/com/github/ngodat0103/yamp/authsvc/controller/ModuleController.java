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
 import java.util.Set;

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

  @GetMapping(produces = "application/json")
  public Set<ModuleDto> getAllModules() {
    return  modulesService.findAll();
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public ModuleDto createModule(@RequestBody ModuleDto module) {
    log.debug("Controller createModule method called");
    return modulesService.create(module);
  }

  @GetMapping(path = "/{id}", produces = "application/json")
  public ModuleDto getModuleById(
      @Parameter(description = "ID of the module to be retrieved") @PathVariable Long id) {
     return  modulesService.readById(id);
  }

  @PutMapping(path = "/{id}")
  public ModuleDto updateModule(
      @Parameter(description = "ID of the module to be updated") @PathVariable Long id,
      @RequestBody ModuleDto module) {
    return modulesService.update(id, module);
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
