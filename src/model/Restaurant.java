package model;

import java.io.Serializable;

public class Restaurant implements Serializable, Comparable<Restaurant>{
	
	private static final long serialVersionUID = 1;
	
	String name;
	String nit;
	String nameAdmin;
	
	public Restaurant(String name, String nit, String nameAdmin) {
		this.name = name;
		this.nit = nit;
		this.nameAdmin = nameAdmin;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameAdmin() {
		return nameAdmin;
	}

	public void setNameAdmin(String nameAdmin) {
		this.nameAdmin = nameAdmin;
	}

	@Override
	public String toString() {
		String message="Name: "+name+"\nNIT: "+nit+"\nName of the Administrator: "+nameAdmin;
		return message;
	}

	@Override
	public int compareTo(Restaurant restaurant) {
		return getName().compareTo(restaurant.getName());
	}





	
	
}
