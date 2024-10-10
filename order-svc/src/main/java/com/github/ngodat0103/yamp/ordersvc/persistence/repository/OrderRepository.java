package com.github.ngodat0103.yamp.ordersvc.persistence.repository;

import com.github.ngodat0103.yamp.ordersvc.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {}
