package com.github.ngodat0103.yamp.ordersvc.persistence.repository;

import com.github.ngodat0103.yamp.ordersvc.persistence.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {}
