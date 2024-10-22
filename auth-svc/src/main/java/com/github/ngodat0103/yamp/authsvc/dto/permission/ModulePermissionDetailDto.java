package com.github.ngodat0103.yamp.authsvc.dto.permission;

import com.github.ngodat0103.yamp.authsvc.dto.ModuleDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModulePermissionDetailDto {
    private Long id;
    private ModuleDto moduleDto ;
    private PermissionDto permissionDto;
}
