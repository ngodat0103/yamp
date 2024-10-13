package com.github.ngodat0103.yamp.ordersvc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CartDto implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  private UUID cartId;
  private UUID userId;

  @JsonInclude(Include.NON_NULL)
  private Set<OrderDto> orderDtos;

  @JsonProperty("user")
  @JsonInclude(Include.NON_NULL)
  @Setter
  private UserDto userDto;
}
