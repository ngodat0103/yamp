package com.github.ngodat0103.yamp.authsvc.controller.permission;

import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.RolePermissionDetailDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.RolePermissionDto;
import com.github.ngodat0103.yamp.authsvc.service.impl.RolePermissionImpl;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/role-permissions")
@RequiredArgsConstructor
public class RolePermissionController {

  private final RolePermissionImpl rolePermissionService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RolePermissionDto create(@RequestBody RolePermissionDto newDto) {
    return rolePermissionService.create(newDto);
  }

  @GetMapping("/{roleId}/permissions")
  public Set<PermissionDto> readPermissionsByRoleId(@PathVariable Long roleId) {
    return rolePermissionService.readPermissionByRoleId(roleId);
  }

  @GetMapping("/details")
  public ResponseEntity<List<RolePermissionDetailDto>> findDetailAll() {
    List<RolePermissionDetailDto> detailDtos = rolePermissionService.findDetailAll();
    return ResponseEntity.ok(detailDtos);
  }
}
