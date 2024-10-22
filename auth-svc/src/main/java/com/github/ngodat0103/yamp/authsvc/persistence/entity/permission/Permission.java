package com.github.ngodat0103.yamp.authsvc.persistence.entity.permission;

import com.github.ngodat0103.yamp.authsvc.persistence.converter.ToUppercaseConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PERMISSIONS")
@Getter
@Setter
public class Permission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false)
  private Long id;

  @Convert(converter = ToUppercaseConverter.class)
  @Column(nullable = false)
  private String name;
}
