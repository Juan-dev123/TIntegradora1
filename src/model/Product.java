package model;

public class Product {
	
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
	
	/**
	@Override
	public String toString() {
		String message="Name: "+name+"\nDescription:";
		return message;
	} **/
	
	
}
