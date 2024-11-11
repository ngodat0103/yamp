package com.github.ngodat0103.yamp.ordersvc.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
    callSuper = true,
    exclude = {"orders"})
@Data
@Builder
public final class Cart extends AbstractMappedEntity implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "cart_id", unique = true, nullable = false, updatable = false)
  private UUID cartId;

  @Column(name = "user_id")
  private UUID userId;

  @JsonIgnore
  @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<Order> orders;
}
