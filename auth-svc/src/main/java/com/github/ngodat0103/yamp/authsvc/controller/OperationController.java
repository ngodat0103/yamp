// package com.github.ngodat0103.yamp.authsvc.controller;
//
// import com.github.ngodat0103.yamp.authsvc.dto.OperationDto;
// import com.github.ngodat0103.yamp.authsvc.persistence.entity.Operation;
// import com.github.ngodat0103.yamp.authsvc.service.impl.OperationServiceImpl;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.media.Content;
// import io.swagger.v3.oas.annotations.media.Schema;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.security.SecurityRequirement;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import java.util.List;
// import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
//
// @RestController
// @Slf4j
// @RequestMapping(value = "/api/v1/operations")
// @AllArgsConstructor
// @SecurityRequirement(name = "oauth2")
//// @PreAuthorize("hasRole('ADMIN')")
// @Tag(name = "Operations", description = "Operations related to operations management")
// public class OperationController {
//
//  private final OperationServiceImpl operationsService;
//
//  @io.swagger.v3.oas.annotations.Operation(
//      summary = "Get all operations",
//      description = "Retrieves a list of all operations.")
//  @ApiResponses(
//      value = {
//        @ApiResponse(
//            responseCode = "200",
//            description = "Successfully retrieved list of operations",
//            content =
//                @Content(
//                    mediaType = "application/json",
//                    schema = @Schema(implementation = Operation.class)))
//      })
//  @GetMapping(produces = "application/json")
//  public ResponseEntity<List<OperationDto>> getAllOperations() {
//    List<OperationDto> operations = operationsService.findAll();
//    return ResponseEntity.ok(operations);
//  }
//
//  @io.swagger.v3.oas.annotations.Operation(
//      summary = "Create a new operation",
//      description = "Adds a new operation to the system.")
//  @ApiResponses(
//      value = {
//        @ApiResponse(
//            responseCode = "201",
//            description = "Operation created successfully",
//            content =
//                @Content(
//                    mediaType = "application/json",
//                    schema = @Schema(implementation = Operation.class))),
//        @ApiResponse(responseCode = "400", description = "Invalid input")
//      })
//  @PostMapping()
//  @ResponseStatus(HttpStatus.CREATED)
//  public OperationDto createOperation(@RequestBody OperationDto operation) {
//    return operationsService.create(operation);
//  }
//
//  @io.swagger.v3.oas.annotations.Operation(
//      summary = "Get operation by ID",
//      description = "Retrieves an operation by its ID.")
//  @ApiResponses(
//      value = {
//        @ApiResponse(
//            responseCode = "200",
//            description = "Successfully retrieved the operation",
//            content =
//                @Content(
//                    mediaType = "application/json",
//                    schema = @Schema(implementation = Operation.class))),
//        @ApiResponse(responseCode = "404", description = "Operation not found")
//      })
//  @GetMapping(path = "/{id}", produces = "application/json")
//  public ResponseEntity<OperationDto> getOperationById(
//      @Parameter(description = "ID of the operation to be retrieved") @PathVariable Long id) {
//    OperationDto operation = operationsService.readById(id);
//    return ResponseEntity.ok(operation);
//  }
//
//  @io.swagger.v3.oas.annotations.Operation(
//      summary = "Update an operation",
//      description = "Updates an existing operation.")
//  @ApiResponses(
//      value = {
//        @ApiResponse(
//            responseCode = "200",
//            description = "Successfully updated the operation",
//            content =
//                @Content(
//                    mediaType = "application/json",
//                    schema = @Schema(implementation = Operation.class))),
//        @ApiResponse(responseCode = "404", description = "Operation not found")
//      })
//  @PutMapping(path = "/{id}")
//  public ResponseEntity<OperationDto> updateOperation(
//      @Parameter(description = "ID of the operation to be updated") @PathVariable Long id,
//      @RequestBody OperationDto operation) {
//    OperationDto updatedOperation = operationsService.update(id, operation);
//    return ResponseEntity.ok(updatedOperation);
//  }
//
//  @io.swagger.v3.oas.annotations.Operation(
//      summary = "Delete an operation",
//      description = "Removes an operation from the system by its ID.")
//  @ApiResponses(
//      value = {
//        @ApiResponse(responseCode = "202", description = "Operation deleted successfully"),
//        @ApiResponse(responseCode = "404", description = "Operation not found")
//      })
//  @DeleteMapping(path = "/{id}")
//  @ResponseStatus(HttpStatus.ACCEPTED)
//  public void deleteOperation(
//      @Parameter(description = "ID of the operation to be deleted") @PathVariable Long id) {
//    log.debug("Controller deleteOperation method called");
//    operationsService.deleteById(id);
//  }
// }
