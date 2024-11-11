package com.github.ngodat0103.yamp.ordersvc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto implements Serializable {

  @Serial private static final long serialVersionUID = 1L;
  private UUID userId;
  private String firstName;
  private String lastName;
  private String imageUrl;
  private String email;
  private String phone;

  @JsonProperty("cart")
  @JsonInclude(Include.NON_NULL)
  private CartDto cartDto;
}
