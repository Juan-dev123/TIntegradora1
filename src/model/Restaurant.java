package model;

import java.io.Serializable;

public class Restaurant implements Serializable, Comparable<Restaurant>{
	
	private static final long serialVersionUID = 1;
	
	String name;
	String nit;
	String nameAdmin;
	
	/**
	 * It creates a new object type Restaurant
	 * @param name The name of the restaurant
	 * @param nit The nit
	 * @param nameAdmin The name of the administrator
	 */
	public Restaurant(String name, String nit, String nameAdmin) {
		this.name = name;
		this.nit = nit;
		this.nameAdmin = nameAdmin;
	}

	/**
	 * @return The nit
	 */
	public String getNit() {
		return nit;
	}

	/**
	 * @param nit The nit
	 */
	public void setNit(String nit) {
		this.nit = nit;
	}
	
	/**
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The name of the administrator
	 */
	public String getNameAdmin() {
		return nameAdmin;
	}

	/**
	 * @param nameAdmin The name of the administrator
	 */
	public void setNameAdmin(String nameAdmin) {
		this.nameAdmin = nameAdmin;
	}

	
	@Override
	/**
	 * It returns the data of the restaurant in a message
	 * @return The message
	 */
	public String toString() {
		String message="Name: "+name+"\nNIT: "+nit+"\nName of the Administrator: "+nameAdmin;
		return message;
	}

	@Override
	/**
	 * It compares two restaurants
	 * @param restaurant The other restaurant
	 * @return A number major than 0 if the restaurant is major than the entered. A number minor than 0 if the restaurant is minor. 0 if they are equals
	 */
	public int compareTo(Restaurant restaurant) {
		return getName().compareTo(restaurant.getName());
	}





	
	
}
