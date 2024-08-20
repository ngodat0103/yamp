package com.github.ngodat0103.yamp.productsvc.persistence.entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public class BaseEntity {
    @Setter(AccessLevel.PRIVATE)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(nullable = false)
    private UUID createdBy;
    @Column(nullable = false)
    private UUID lastModifiedBy;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime lastModifiedAt;


    public BaseEntity(UUID createdBy) {
        assert createdBy!=null;
        this.createdBy = createdBy;
        this.lastModifiedBy = createdBy;
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = createdAt;
    }
}
