package com.github.ngodat0103.yamp.authsvc.persistence.entity;

import com.github.ngodat0103.yamp.authsvc.persistence.converter.ToUppercaseConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "OPERATIONS")
@Getter
@Setter
public class Operation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false)
  private Long id;

  @Column(nullable = false, unique = true)
  @Convert(converter = ToUppercaseConverter.class)
  private String name;
}
