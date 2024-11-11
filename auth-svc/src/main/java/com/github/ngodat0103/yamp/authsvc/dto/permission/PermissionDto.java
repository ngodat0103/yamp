package com.github.ngodat0103.yamp.authsvc.dto.permission;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PermissionDto {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  private String name;
}
