package com.github.ngodat0103.yamp.ordersvc.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CartDto implements Serializable {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	private Integer cartId;
	private Integer userId;
	
	@JsonInclude(Include.NON_NULL)
	private Set<OrderDto> orderDtos;
	
	@JsonProperty("user")
	@JsonInclude(Include.NON_NULL)
	@Setter
	private UserDto userDto;
	
}










