package com.github.ngodat0103.yamp.authsvc.persistence.entity.permission;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.Operation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PERMISSIONS_OPERATIONS")
@Getter
@Setter
public class PermissionOperation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "permission_id", nullable = false)
  private Permission permission;

  @ManyToOne
  @JoinColumn(name = "operations_id", nullable = false)
  private Operation operation;
}
