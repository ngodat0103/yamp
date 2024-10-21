package com.github.ngodat0103.yamp.authsvc.persistence.entity.module;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "OPERATIONS")
@Getter
@Setter
public class Operations {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false)
  private Long id;

  @Column(nullable = false)
  private String name;

}
