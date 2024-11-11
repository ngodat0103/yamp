package com.github.ngodat0103.yamp.ordersvc.dto.mapper;

import com.github.ngodat0103.yamp.ordersvc.dto.OrderDto;
import com.github.ngodat0103.yamp.ordersvc.persistence.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
  Order toEntity(OrderDto orderDto);

  OrderDto toDto(Order order);
}
