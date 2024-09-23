package com.github.ngodat0103.yamp.authsvc.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
  @Column(nullable = false, updatable = false)
  private LocalDateTime createAt;

  @Column(nullable = false)
  private LocalDateTime lastModifiedAt;
}
