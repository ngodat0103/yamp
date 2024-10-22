package com.github.ngodat0103.yamp.authsvc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OperationDto {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  @NotNull(message = "Name is required")
  private String name;
}
