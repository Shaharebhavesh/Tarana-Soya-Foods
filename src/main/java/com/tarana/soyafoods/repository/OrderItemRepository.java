package com.tarana.soyafoods.repository;

import com.tarana.soyafoods.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
