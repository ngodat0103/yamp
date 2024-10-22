package com.github.ngodat0103.yamp.authsvc.dto.permission;

import com.github.ngodat0103.yamp.authsvc.dto.OperationDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PermissionOperationDetailDto {
  private Long id;
  private OperationDto operationDto;
  private PermissionDto permissionDto;
}
