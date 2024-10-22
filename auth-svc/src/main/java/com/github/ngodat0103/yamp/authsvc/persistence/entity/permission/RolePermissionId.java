package com.github.ngodat0103.yamp.authsvc.persistence.entity.permission;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class RolePermissionId implements Serializable {
  @Column(name = "role_id_fk", insertable = false, updatable = false)
  private Long roleId;

  @Column(name = "permission_id_fk", insertable = false, updatable = false)
  private Long permissionId;
}
