package com.github.ngodat0103.yamp.authsvc.persistence.entity.permission;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.Operation;
import jakarta.persistence.*;

@Entity
@Table(name = "PERMISSIONS_OPERATIONS")
public class PermissionsOperations {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "permission_id", nullable = false)
  private Permission permission;

  @ManyToOne
  @JoinColumn(name = "operations_id", nullable = false)
  private Operation operation;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Permission getPermission() {
    return permission;
  }

  public void setPermission(Permission permission) {
    this.permission = permission;
  }

  public Operation getOperations() {
    return operation;
  }

  public void setOperations(Operation operation) {
    this.operation = operation;
  }
}
