package com.github.ngodat0103.yamp.authsvc.controller.module;

import com.github.ngodat0103.yamp.authsvc.dto.permission.OperationsDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.module.Operations;
import com.github.ngodat0103.yamp.authsvc.service.impl.OperationsServiceImpl;
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
@RequestMapping(value = "/api/v1/operations")
@AllArgsConstructor
@SecurityRequirement(name = "oauth2")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Operations", description = "Operations related to operations management")
public class OperationsController {

  private final OperationsServiceImpl operationsService;

  @Operation(summary = "Get all operations", description = "Retrieves a list of all operations.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved list of operations",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Operations.class)))
      })
  @GetMapping(produces = "application/json")
  public ResponseEntity<List<OperationsDto>> getAllOperations() {
    List<OperationsDto> operations = operationsService.findAll();
    return ResponseEntity.ok(operations);
  }

  @Operation(
      summary = "Create a new operation",
      description = "Adds a new operation to the system.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Operation created successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Operations.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input")
      })
  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public void createOperation(@RequestBody OperationsDto operation) {
    log.debug("Controller createOperation method called");
    operationsService.create(operation);
  }

  @Operation(summary = "Get operation by ID", description = "Retrieves an operation by its ID.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the operation",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Operations.class))),
        @ApiResponse(responseCode = "404", description = "Operation not found")
      })
  @GetMapping(path = "/{id}", produces = "application/json")
  public ResponseEntity<OperationsDto> getOperationById(
      @Parameter(description = "ID of the operation to be retrieved") @PathVariable Long  id) {
    OperationsDto operation = operationsService.readById(id);
    return ResponseEntity.ok(operation);
  }

  @Operation(summary = "Update an operation", description = "Updates an existing operation.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully updated the operation",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Operations.class))),
        @ApiResponse(responseCode = "404", description = "Operation not found")
      })
  @PutMapping(path = "/{id}")
  public ResponseEntity<OperationsDto> updateOperation(
      @Parameter(description = "ID of the operation to be updated") @PathVariable Long id,
      @RequestBody OperationsDto operation) {
    operation.setId(id);
      OperationsDto updatedOperation = operationsService.update(id,operation);
    return ResponseEntity.ok(updatedOperation);
  }

  @Operation(
      summary = "Delete an operation",
      description = "Removes an operation from the system by its ID.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "202", description = "Operation deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Operation not found")
      })
  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void deleteOperation(
      @Parameter(description = "ID of the operation to be deleted") @PathVariable Long id) {
    log.debug("Controller deleteOperation method called");
    operationsService.deleteById(id);
  }
}
