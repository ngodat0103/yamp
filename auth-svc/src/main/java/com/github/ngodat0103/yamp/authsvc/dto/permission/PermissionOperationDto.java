package com.github.ngodat0103.yamp.authsvc.dto.permission;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PermissionOperationDto {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  @NotNull(message = "Operation ID is required")
  private Long operationId;

  @NotNull(message = "Permission ID is required")
  private Long permissionId;
}
