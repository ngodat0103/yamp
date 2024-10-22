package com.github.ngodat0103.yamp.authsvc.persistence.entity.role;

import com.github.ngodat0103.yamp.authsvc.persistence.converter.ToUppercaseConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ROLES")
@Getter
@Setter
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, name = "role_id")
  private Long id;

  @Column(nullable = false, updatable = false)
  @Convert(converter = ToUppercaseConverter.class)
  private String name;
}
