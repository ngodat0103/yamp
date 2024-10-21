package com.github.ngodat0103.yamp.authsvc.controller.module;

import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionsOperationsDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.PermissionsOperations;
import com.github.ngodat0103.yamp.authsvc.service.impl.PermissionsOperationsServiceImpl;
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
@RequestMapping(value = "/api/v1/permissions-operations")
@AllArgsConstructor
@SecurityRequirement(name = "oauth2")
@PreAuthorize("hasRole('ADMIN')")
@Tag(
    name = "Permissions Operations",
    description = "Operations related to permissions operations management")
public class PermissionsOperationsController {

  private final PermissionsOperationsServiceImpl permissionsOperationsService;

  @Operation(
      summary = "Get all permissions operations",
      description = "Retrieves a list of all permissions operations.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved list of permissions operations",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PermissionsOperations.class)))
      })
  @GetMapping(produces = "application/json")
  public ResponseEntity<List<PermissionsOperationsDto>> getAllPermissionsOperations() {
    List<PermissionsOperationsDto> permissionsOperations = permissionsOperationsService.findAll();
    return new ResponseEntity<>(permissionsOperations, HttpStatus.OK);
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
                    schema = @Schema(implementation = PermissionsOperations.class))),
        @ApiResponse(responseCode = "404", description = "Permission operation not found.")
      })
  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<PermissionsOperationsDto> getPermissionOperationById(
      @Parameter(description = "ID of the permission operation to be retrieved") @PathVariable
          Long id) {
      PermissionsOperationsDto permissionOperation = permissionsOperationsService.readById(id);
        return new ResponseEntity<>(permissionOperation, HttpStatus.OK);
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
                    schema = @Schema(implementation = PermissionsOperations.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input")
      })
  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<PermissionsOperationsDto> createPermissionOperation(
      @RequestBody PermissionsOperationsDto permissionsOperations) {
      PermissionsOperationsDto createdPermissionOperation =
        permissionsOperationsService.create(permissionsOperations);
    return new ResponseEntity<>(createdPermissionOperation, HttpStatus.CREATED);
  }

  //    @Operation(summary = "Update an existing permission operation", description = "Updates an
  // existing permission operation.")
  //    @ApiResponses(value = {
  //        @ApiResponse(responseCode = "200", description = "Successfully updated the permission
  // operation.",
  //                content = @Content(mediaType = "application/json", schema =
  // @Schema(implementation = PermissionsOperations.class))),
  //        @ApiResponse(responseCode = "404", description = "Permission operation not found.")
  //    })
  //    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
  //    public ResponseEntity<PermissionsOperations>
  // updatePermissionOperation(@Parameter(description = "ID of the permission operation to be
  // updated") @PathVariable Long id, @RequestBody PermissionsOperations permissionsOperations) {
  //        Optional<PermissionsOperations> existingPermissionOperation =
  // permissionsOperationsService.readById(id);
  //        if (existingPermissionOperation.isPresent()) {
  //            PermissionsOperations updatedPermissionOperation =
  // permissionsOperationsService.update(id, permissionsOperations);
  //            return new ResponseEntity<>(updatedPermissionOperation, HttpStatus.OK);
  //        } else {
  //            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  //        }
  //    }

  //    @Operation(summary = "Delete a permission operation", description = "Deletes a permission
  // operation by its ID.")
  //    @ApiResponses(value = {
  //        @ApiResponse(responseCode = "204", description = "Successfully deleted the permission
  // operation."),
  //        @ApiResponse(responseCode = "404", description = "Permission operation not found.")
  //    })
  //    @DeleteMapping(value = "/{id}")
  //    public ResponseEntity<Void> deletePermissionOperation(@Parameter(description = "ID of the
  // permission operation to be deleted") @PathVariable Long id) {
  //        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_IMPLEMENTED);
  //        problemDetail.setTitle("Not implemented");
  //        return ResponseEntity.of(problemDetail);
  //    }
}
