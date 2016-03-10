package com.msys.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
@DiscriminatorValue("G")
public class NextDayOrder extends Order { 
 
	public NextDayOrder(Date deliveryDate, Set<OrderItem> orderItems, Date validFrom, Date validTo) {
		super(deliveryDate, orderItems, validFrom, validTo);		
	}
	
	public NextDayOrder () {
		super();
	}

	@Override
	public Date getValidTo() {
		return getValidFrom();
	}
}
