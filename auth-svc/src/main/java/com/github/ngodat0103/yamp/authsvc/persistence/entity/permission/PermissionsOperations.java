package com.github.ngodat0103.yamp.authsvc.persistence.entity.permission;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.module.Operations;
import jakarta.persistence.*;

@Entity
@Table(name = "PERMISSIONS_OPERATIONS")
public class PermissionsOperations {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "permission_id", nullable = false)
  private Permissions permission;

  @ManyToOne
  @JoinColumn(name = "operations_id", nullable = false)
  private Operations operations;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Permissions getPermission() {
    return permission;
  }

  public void setPermission(Permissions permission) {
    this.permission = permission;
  }

  public Operations getOperations() {
    return operations;
  }

  public void setOperations(Operations operations) {
    this.operations = operations;
  }
}
