package com.github.ngodat0103.yamp.ordersvc.controller;

import com.github.ngodat0103.yamp.ordersvc.dto.CartDto;
import com.github.ngodat0103.yamp.ordersvc.service.CartService;
import com.ngodat0103.yamp.dto.response.collection.DtoCollectionResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
@Slf4j
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  @GetMapping
  public ResponseEntity<DtoCollectionResponse<CartDto>> findAll() {
    log.info("*** CartDto List, controller; fetch all categories *");
    return ResponseEntity.ok(new DtoCollectionResponse<>(this.cartService.findAll()));
  }

  @GetMapping("/{cartId}")
  public ResponseEntity<CartDto> findById(
      @PathVariable("cartId") @NotBlank(message = "Input must not be blank") @Valid
          final String cartId) {
    log.info("*** CartDto, resource; fetch cart by id *");
    return ResponseEntity.ok(this.cartService.findById(Integer.parseInt(cartId)));
  }

  @PostMapping
  public ResponseEntity<CartDto> save(
      @RequestBody @NotNull(message = "Input must not be NULL!") @Valid final CartDto cartDto) {
    log.info("*** CartDto, resource; save cart *");
    return ResponseEntity.ok(this.cartService.save(cartDto));
  }

  @PutMapping
  public ResponseEntity<CartDto> update(
      @RequestBody @NotNull(message = "Input must not be NULL") @Valid final CartDto cartDto) {
    log.info("*** CartDto, resource; update cart *");
    return ResponseEntity.ok(this.cartService.update(cartDto));
  }

  @PutMapping("/{cartId}")
  public ResponseEntity<CartDto> update(
      @PathVariable("cartId") @NotBlank(message = "Input must not be blank") @Valid
          final String cartId,
      @RequestBody @NotNull(message = "Input must not be NULL") @Valid final CartDto cartDto) {
    log.info("*** CartDto, resource; update cart with cartId *");
    return ResponseEntity.ok(this.cartService.update(Integer.parseInt(cartId), cartDto));
  }

  @DeleteMapping("/{cartId}")
  public ResponseEntity<Boolean> deleteById(@PathVariable("cartId") final String cartId) {
    log.info("*** Boolean, resource; delete cart by id *");
    this.cartService.deleteById(Integer.parseInt(cartId));
    return ResponseEntity.ok(true);
  }
}
