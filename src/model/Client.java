package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Client implements Comparable<Client>, Serializable{

	enum Card{
		IDENTITY_CARD,
		CEDULA,
		PASSPORT,
		FOREIGNER_ID
	}
	
	private static final long serialVersionUID = 1;
	
	private String id;
	private String name;
	private String lastName;
	private String phoneNumber;
	private String address;
	
	private ArrayList<Order> orders;
	private Card typeId;
	
	/**
	 * It creates a new object type Client
	 * @param typeId The type of id
	 * @param id The number of the id
	 * @param name The name
	 * @param lastName The last name
	 * @param phoneNumber The phone number
	 * @param address The address
	 */
	public Client(int typeId, String id, String name, String lastName, String phoneNumber, String address) {
		this.typeId = Card.values()[typeId];
		this.id = id;
		this.name = name.toUpperCase();
		this.lastName = lastName.toUpperCase();
		this.phoneNumber = phoneNumber;
		this.address = address;
		orders = new ArrayList<Order>();
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
	 * It returns the name
	 * @return The name
	 */
	public String getName() {
		return name;
	}
	
	public String getFullName() {
		return lastName+" "+name;
	}

	/**
	 * It sets the name
	 * @param name The name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	/**
	 * It returns the data of the client in one string
	 * @return The string
	 */
	public String toString() {
		String message="Name: "+name+"\nLast name: "+lastName+"\nType of Id: "+typeId+"\nNumber of identification: "+id+"\nPhone Number: "+phoneNumber+"\nAddress:"+address;
		return message;
	}

	public ArrayList<Order> getOrder() {
		return orders;
	}

	public void addOrder(Order order) {
		orders.add(order);
	}

	@Override
	public int compareTo(Client client) {
		int comp=client.getLastName().compareTo(lastName);
		if(comp==0) {
			comp=client.getName().compareTo(name);
		}
		return comp;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Card getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = Card.values()[typeId];
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	
	
	
	
	
	
}
