package com.github.ngodat0103.yamp.authsvc.dto.permission;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.ngodat0103.yamp.authsvc.dto.OperationDto;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PermissionOperationsDto {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  private String name;
  private Set<OperationDto> operationDtos;
}
