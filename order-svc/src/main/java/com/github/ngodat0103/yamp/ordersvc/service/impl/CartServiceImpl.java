package com.github.ngodat0103.yamp.ordersvc.service.impl;

import com.github.ngodat0103.yamp.ordersvc.constant.AppConstant;
import com.github.ngodat0103.yamp.ordersvc.dto.CartDto;
import com.github.ngodat0103.yamp.ordersvc.dto.UserDto;
import com.github.ngodat0103.yamp.ordersvc.dto.mapper.CartMapper;
import com.github.ngodat0103.yamp.ordersvc.exception.wrapper.CartNotFoundException;
import com.github.ngodat0103.yamp.ordersvc.persistence.entity.Cart;
import com.github.ngodat0103.yamp.ordersvc.persistence.repository.CartRepository;
import com.github.ngodat0103.yamp.ordersvc.service.CartService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final WebClient webClient;
  private final CartMapper cartMapper;

  @Override
  public List<CartDto> findAll() {
    log.info("*** CartDto List, service; fetch all carts *");
    return this.cartRepository.findAll().stream()
        .map(cartMapper::toDto)
        .peek(c -> {
          Mono<UserDto> userDtoMono = this.webClient.get()
              .uri(AppConstant.DiscoveredDomainsApi.USER_SERVICE_API_URL + "/" + c.getUserDto().getUserId())
              .retrieve()
              .bodyToMono(UserDto.class);
          c.setUserDto(userDtoMono.block());
        })
        .toList();
  }

  @Override
  public CartDto findById(final Integer cartId) {
    log.info("*** CartDto, service; fetch cart by id *");
    return this.cartRepository
        .findById(cartId)
        .map(cartMapper::toDto)
        .map(c -> {
          Mono<UserDto> userDtoMono = this.webClient.get()
              .uri(AppConstant.DiscoveredDomainsApi.USER_SERVICE_API_URL + "/" + c.getUserDto().getUserId())
              .retrieve()
              .bodyToMono(UserDto.class);
          c.setUserDto(userDtoMono.block());
          return c;
        })
        .orElseThrow(() -> new CartNotFoundException(String.format("Cart with id: %d not found", cartId)));
  }

  @Override
  public CartDto save(final CartDto cartDto) {
    log.info("*** CartDto, service; save cart *");
    Cart cart = this.cartMapper.toEntity(cartDto);
    return this.cartMapper.toDto(this.cartRepository.save(cart));
  }

  @Override
  public CartDto update(final CartDto cartDto) {
    log.info("*** CartDto, service; update cart *");
    return cartMapper.toDto(this.cartRepository.save(cartMapper.toEntity(cartDto)));
  }

  @Override
  public CartDto update(final Integer cartId, final CartDto cartDto) {
    log.info("*** CartDto, service; update cart with cartId *");
    return cartMapper.toDto(this.cartRepository.save(cartMapper.toEntity(this.findById(cartId))));
  }

  @Override
  public void deleteById(final Integer cartId) {
    log.info("*** Void, service; delete cart by id *");
    this.cartRepository.deleteById(cartId);
  }
}
