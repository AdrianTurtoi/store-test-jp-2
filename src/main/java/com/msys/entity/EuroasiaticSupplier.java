package com.msys.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SUPPLIER")
@DiscriminatorValue("E")
public class EuroasiaticSupplier extends Supplier {

	@Column(name = "IS_VAT_EXTEMPT")
	private byte isVATExtempt;
	
	@Column(name = "VAT")
	private int getVAT;

	public EuroasiaticSupplier(int supplierNo, String supplierName, byte isVATExtempt, int getVAT) {
		super(supplierNo, supplierName);
		this.isVATExtempt = isVATExtempt;
		this.getVAT = getVAT;
	}

    public EuroasiaticSupplier () {		
    	super();
	}

	public byte getIsVATExtempt() {
		return isVATExtempt;
	}

	public void setIsVATExtempt(byte isVATExtempt) {
		this.isVATExtempt = isVATExtempt;
	}

	public int getGetVAT() {
		return getVAT;
	}

	public void setGetVAT(int getVAT) {
		this.getVAT = getVAT;
	}

	@Override
	public String toString() {
		return "EuroasiaticSupplier [isVATExtempt=" + isVATExtempt + ", getVAT=" + getVAT + "]";
	}

}
