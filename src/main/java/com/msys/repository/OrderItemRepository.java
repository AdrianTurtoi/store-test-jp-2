package com.msys.repository;

import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.msys.entity.Order;
import com.msys.entity.OrderItem;

@Transactional
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	Set<OrderItem> findByOrders(Order order);

	@Modifying
	@Query("delete from OrderItem o where id = ?1")
	void deleteByOrderItemId(Long id);
}
