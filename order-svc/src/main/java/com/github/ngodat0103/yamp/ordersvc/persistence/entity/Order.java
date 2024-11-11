package com.github.ngodat0103.yamp.ordersvc.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.github.ngodat0103.yamp.ordersvc.constant.AppConstant;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
    callSuper = true,
    exclude = {"cart"})
@Data
@Builder
public final class Order extends AbstractMappedEntity implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "order_id", unique = true, nullable = false, updatable = false)
  private UUID orderId;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(pattern = AppConstant.LOCAL_DATE_TIME_FORMAT, shape = Shape.STRING)
  @DateTimeFormat(pattern = AppConstant.LOCAL_DATE_TIME_FORMAT)
  @Column(name = "order_date")
  private LocalDateTime orderDate;

  @Column(name = "order_desc")
  private String orderDesc;

  @Column(name = "order_fee", columnDefinition = "decimal")
  private Double orderFee;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "cart_id")
  private Cart cart;
}
