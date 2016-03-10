package com.msys.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
@DiscriminatorValue("F")
public class LowPriorityOrder extends Order {  


	public LowPriorityOrder(Date deliveryDate, Set<OrderItem> orderItems, Date validFrom, Date validTo) {
		super(deliveryDate, orderItems, validFrom, validTo);		
	}
	
	public LowPriorityOrder () {
		super();
	}
	
	@Override
	public Date getDeliveryDate() {
		return null;
	}
}
