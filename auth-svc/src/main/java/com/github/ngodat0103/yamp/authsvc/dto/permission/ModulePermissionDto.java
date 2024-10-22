package com.github.ngodat0103.yamp.authsvc.dto.permission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.ngodat0103.yamp.authsvc.dto.ModuleDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ModulePermissionDto {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;
  @NotNull(message = "Module ID is required")
  private Long modulesId;
  @NotNull(message = "Permission ID is required")
  private Long permissionsId;
}
