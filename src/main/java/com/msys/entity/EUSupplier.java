package com.msys.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SUPPLIER")
@DiscriminatorValue("F")
public class EUSupplier extends Supplier { 
	
	@Column(name = "IS_VAT_EXTEMPT")
	private byte isVATExtempt; 
	
	public EUSupplier(int supplierNo, String supplierName, byte isVATExtempt) {
		super(supplierNo, supplierName);
		this.isVATExtempt = isVATExtempt;
	}
	
	public EUSupplier () {
		super();
	}	

	public byte getIsVATExtempt() {
		return isVATExtempt;
	}

	public void setIsVATExtempt(byte isVATExtempt) {
		this.isVATExtempt = isVATExtempt;
	}

	@Override
	public String toString() {
		return "EUSupplier [isVATExtempt=" + isVATExtempt + "]";
	}

}
