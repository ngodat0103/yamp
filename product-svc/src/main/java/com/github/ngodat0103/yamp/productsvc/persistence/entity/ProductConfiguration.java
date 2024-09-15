package com.github.ngodat0103.yamp.productsvc.persistence.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class ProductConfiguration {
    @Embeddable
    @EqualsAndHashCode
    public static class CompositePK implements Serializable {
       private UUID productItemUuid;
       private UUID productVariationOptionUUid;
    }





}
