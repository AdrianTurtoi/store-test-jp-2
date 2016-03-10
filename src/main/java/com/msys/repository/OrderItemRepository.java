package com.msys.repository;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.msys.entity.Order;
import com.msys.entity.OrderItem;

@Transactional
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> { 
	List<OrderItem> findByOrders(Order order); 
}
