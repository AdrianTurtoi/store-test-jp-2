package com.msys.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "P")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DELIVERY_DATE")
	private Date deliveryDate;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "orders", cascade = CascadeType.ALL)
	private Set<OrderItem> orderItems;

	@Column(name = "VALID_FROM")
	private Date validFrom;

	@Column(name = "VALID_TO")
	private Date validTo;

	@Column(name = "AVAILABLE_AT")
	private int avalabileAt;

	public Order() {
		orderItems = new HashSet<OrderItem>();
	}

	public Order(Date deliveryDate, Set<OrderItem> orderItems, Date validFrom, Date validTo) {
		super();
		this.deliveryDate = deliveryDate;
		this.orderItems = orderItems;
		this.validFrom = validFrom;
		this.validTo = validTo;
	}

	public Order(Date deliveryDate, Date validFrom, Date validTo) {
		super();
		this.deliveryDate = deliveryDate;
		this.validFrom = validFrom;
		this.validTo = validTo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean availableAt(Date inputDate) {
		return true;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public int getAvalabileAt() {
		return avalabileAt;
	}

	public void setAvalabileAt(int avalabileAt) {
		this.avalabileAt = avalabileAt;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", deliveryDate=" + deliveryDate + ", ordersItems=" + orderItems + ", validFrom="
				+ validFrom + ", validTo=" + validTo + "]";
	}
}
