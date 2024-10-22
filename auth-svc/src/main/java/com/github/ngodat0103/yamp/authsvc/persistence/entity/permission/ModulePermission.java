package com.github.ngodat0103.yamp.authsvc.persistence.entity.permission;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.Module;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MODULES_PERMISSIONS")
@Setter
@Getter
public class ModulePermission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "modules_id", nullable = false)
  private Module module;

  @ManyToOne
  @JoinColumn(name = "permissions_id", nullable = false)
  private Permission permission;
}
