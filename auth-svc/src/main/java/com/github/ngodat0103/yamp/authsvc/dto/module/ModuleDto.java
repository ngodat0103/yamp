package com.github.ngodat0103.yamp.authsvc.dto.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ModuleDto {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  private String name;
}
