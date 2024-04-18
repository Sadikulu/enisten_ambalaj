package com.kss.repository;

import com.kss.domains.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository <OrderItem,Long> {
}
