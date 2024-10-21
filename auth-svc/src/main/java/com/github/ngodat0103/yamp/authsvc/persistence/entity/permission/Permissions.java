package com.github.ngodat0103.yamp.authsvc.persistence.entity.permission;

import jakarta.persistence.*;

@Entity
@Table(name = "PERMISSIONS")
public class Permissions {

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
