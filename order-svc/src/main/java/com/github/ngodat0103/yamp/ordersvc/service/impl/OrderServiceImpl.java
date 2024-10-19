package com.github.ngodat0103.yamp.ordersvc.service.impl;

import com.github.ngodat0103.yamp.ordersvc.dto.OrderDto;
import com.github.ngodat0103.yamp.ordersvc.dto.mapper.OrderMapper;
import com.github.ngodat0103.yamp.ordersvc.exception.wrapper.OrderNotFoundException;
import com.github.ngodat0103.yamp.ordersvc.persistence.repository.OrderRepository;
import com.github.ngodat0103.yamp.ordersvc.service.OrderService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;

  @Override
  public List<OrderDto> findAll() {
    log.info("*** OrderDto List, service; fetch all orders *");
    return this.orderRepository.findAll().stream().map(orderMapper::toDto).distinct().toList();
  }

  @Override
  public OrderDto findById(final Integer orderId) {
    log.info("*** OrderDto, service; fetch order by id *");
    return this.orderRepository
        .findById(orderId)
        .map(orderMapper::toDto)
        .orElseThrow(
            () ->
                new OrderNotFoundException(String.format("Order with id: %d not found", orderId)));
  }

  @Override
  public OrderDto save(final OrderDto orderDto) {
    log.info("*** OrderDto, service; save order *");
    return orderMapper.toDto(this.orderRepository.save(orderMapper.toEntity(orderDto)));
  }

  @Override
  public OrderDto update(final OrderDto orderDto) {
    log.info("*** OrderDto, service; update order *");
    return orderMapper.toDto(this.orderRepository.save(orderMapper.toEntity(orderDto)));
  }

  @Override
  public OrderDto update(final Integer orderId, final OrderDto orderDto) {
    log.info("*** OrderDto, service; update order with orderId *");
    return orderMapper.toDto(
        this.orderRepository.save(orderMapper.toEntity(this.findById(orderId))));
  }

  @Override
  public void deleteById(final Integer orderId) {
    log.info("*** Void, service; delete order by id *");
    this.orderRepository.delete(orderMapper.toEntity(this.findById(orderId)));
  }
}
