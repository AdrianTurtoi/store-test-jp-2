package com.msys.repository;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msys.entity.Order;
import com.msys.entity.OrderItem;

@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {

	/*@Query("select a.articleNo as articleNo, a.articleName as articleName, oi.quantity as quantity, s.supplierNo as supplierNo "
			+ "from Order o join o.orderItems oi join oi.articles a join oi.suppliers s")*/
	//@Query("select oi from Order o join o.orderItems oi join oi.articles a join oi.suppliers s")
	//List<OrderItem> findByOrderItems();

}
