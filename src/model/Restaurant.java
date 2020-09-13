package model;

import java.util.ArrayList;

public class Restaurant {
	
	String name;
	String nit;
	String nameAdmin;
	
	private ArrayList<Product> products;
	private ArrayList<Order> orders;
	
	public Restaurant(String name, String nit, String nameAdmin) {
		this.name = name;
		this.nit = nit;
		this.nameAdmin = nameAdmin;
	}
	
	public void addProduct(String id, String name, String description, double price, String nit) {
		products.add(new Product(id, name, description, price, nit));
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}




	
	
}
