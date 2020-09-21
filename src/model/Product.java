package model;

import java.io.Serializable;

public class Product implements Serializable{
	
	private static final long serialVersionUID = 1;
	String id;
	String name;
	String description;
	double price;
	String nit;
	
	/**
	 * It creates a new object type Product
	 * @param id The id
	 * @param name The name
	 * @param description A description
	 * @param price The price
	 * @param nit The NIT of the restaurant who offers this product
	 */
	public Product(String id, String name, String description, double price, String nit) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.nit = nit;
	}
	/**
	 * It returns the id
	 * @return The id
	 */
	public String getId() {
		return id;
	}

	/**
	 * It sets the id
	 * @param id The id
	 */
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
	}
	
	
	
	/**
	@Override
	public String toString() {
		String message="Name: "+name+"\nDescription:";
		return message;
	} **/
	
	
}
