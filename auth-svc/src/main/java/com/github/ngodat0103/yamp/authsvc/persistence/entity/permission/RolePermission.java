package com.github.ngodat0103.yamp.authsvc.persistence.entity.permission;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.role.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ROLES_PERMISSIONS")
@Getter
@Setter
public class RolePermission {

  @EmbeddedId private RolePermissionId rolePermissionId;

  @ManyToOne
  @JoinColumn(name = "role_id_fk", referencedColumnName = "role_id")
  private Role role;

  @JoinColumn(name = "permission_id_fk", referencedColumnName = "permission_id")
  @ManyToOne
  private Permission permission;
}
