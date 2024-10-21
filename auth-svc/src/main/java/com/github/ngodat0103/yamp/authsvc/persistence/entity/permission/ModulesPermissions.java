package com.github.ngodat0103.yamp.authsvc.persistence.entity.permission;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.module.Modules;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "MODULES_PERMISSIONS")
@Setter
public class ModulesPermissions {

  // Getters and Setters
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "modules_id", nullable = false)
  private Modules modules;

  @ManyToOne
  @JoinColumn(name = "permissions_id", nullable = false)
  private Permissions permissions;

  public void setId(Long id) {
    this.id = id;
  }

  public void setModules(Modules modules) {
    this.modules = modules;
  }

  public void setPermissions(Permissions permissions) {
    this.permissions = permissions;
  }
}
