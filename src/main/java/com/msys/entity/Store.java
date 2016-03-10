package com.msys.entity;

import java.util.List;

public class Store {

	private String storeName;
	private List<Stock> stocks; 

	public Store(String storeName, List<Stock> stocks) {
		super();
		this.storeName = storeName;
		this.stocks = stocks;
	}

	private static Store instance = null;

	private Store() {
		// Exists only to defeat instantiation.
	}

	public static Store getInstance() {
		if (instance == null) {
			instance = new Store();
		}
		return instance;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	@Override
	public String toString() {
		return "Store [storeName=" + storeName + ", stocks=" + stocks + "]";
	}
}
