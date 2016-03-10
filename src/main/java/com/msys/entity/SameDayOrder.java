package com.msys.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
@DiscriminatorValue("H")
public class SameDayOrder extends Order { 
	 

	public SameDayOrder(Date deliveryDate, Set<OrderItem> orderItems, Date validFrom, Date validTo) {
		super(deliveryDate, orderItems, validFrom, validTo);	
	}

	public SameDayOrder () {
		super();
	}
	
	@Override
	public Date getValidTo() {
		return getDeliveryDate();
	}

	@Override
	public Date getValidFrom() {
		return getDeliveryDate();
	}
}
