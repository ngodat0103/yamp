package com.github.ngodat0103.yamp.authsvc.persistence.entity.module;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MODULES")
@Getter
@Setter
public class Modules {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;
}
