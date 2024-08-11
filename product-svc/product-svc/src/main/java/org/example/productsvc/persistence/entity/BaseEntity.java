package org.example.productsvc.persistence.entity;


import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public class BaseEntity {
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime lastModifiedAt; ;
    private String lastModifiedBy;


    public BaseEntity(String createdBy) {
        this.createdBy = Objects.requireNonNull(createdBy,"test_user");
        lastModifiedBy = createdBy;
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = createdAt;

    }
}
