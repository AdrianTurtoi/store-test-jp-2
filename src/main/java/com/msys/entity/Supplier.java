package com.msys.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SUPPLIER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "P")
public class Supplier { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id; 
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "suppliers", cascade = CascadeType.ALL)
	Set <OrderItem> orderItem;
	
	@Column(name = "SUPPLIER_NO")
	private int supplierNo;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplierName;
	
	public Supplier(Set<OrderItem> orderItem, int supplierNo, String supplierName) {
		super();		
		this.orderItem = orderItem;
		this.supplierNo = supplierNo;
		this.supplierName = supplierName;
	} 

	public Supplier(int supplierNo) {
		this.supplierNo = supplierNo;
		this.supplierName = "NoNameSupplier";
	}

	public Supplier(int supplierNo, String supplierName) {
		super();
		this.supplierNo = supplierNo;
		this.supplierName = supplierName;
	}

	public Supplier() {	
	}	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<OrderItem> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(Set<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}
	
	public int getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(int supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Override
	public String toString() {
		return "Supplier [id=" + id + ", supplierNo=" + supplierNo + ", supplierName=" + supplierName + "]";
	} 
}
