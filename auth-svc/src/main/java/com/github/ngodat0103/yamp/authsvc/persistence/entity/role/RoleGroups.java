package com.github.ngodat0103.yamp.authsvc.persistence.entity.role;

import jakarta.persistence.*;

@Entity
@Table(name = "ROLE_GROUPS")
public class RoleGroups {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
