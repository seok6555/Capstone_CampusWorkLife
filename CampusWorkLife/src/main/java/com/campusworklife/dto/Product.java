package com.campusworklife.dto;

public class Product {
	String name;
	int unitCost;

	public Product(String name, int unitCost) {
		super();
		this.name = name;
		this.unitCost = unitCost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(int unitCost) {
		this.unitCost = unitCost;
	}
}
