package model;

public class Product {
	
	String id;
	String name;
	String description;
	double price;
	String nit;
	
	public Product(String id, String name, String description, double price, String nit) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.nit = nit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}
