package com.github.ngodat0103.yamp.ordersvc.dto.mapper;

import com.github.ngodat0103.yamp.ordersvc.dto.CartDto;
import com.github.ngodat0103.yamp.ordersvc.persistence.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
  Cart toEntity(CartDto cartDto);

  CartDto toDto(Cart cart);
}
