package model;

import java.io.Serializable;
import java.math.BigInteger;

public class Restaurant implements Serializable, Comparable<Restaurant>{
	
	private static final long serialVersionUID = 1;
	
	String name;
	BigInteger nit;
	String nameAdmin;
	
	public Restaurant(String name, BigInteger nit, String nameAdmin) {
		this.name = name;
		this.nit = nit;
		this.nameAdmin = nameAdmin;
	}

	public BigInteger getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = new BigInteger(nit);
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
