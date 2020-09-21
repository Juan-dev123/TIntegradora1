package model;

import java.io.Serializable;

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
	private String phoneNumber;
	private String address;
	
	private Order order;
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
	public Client(int typeId, String id, String name, String phoneNumber, String address) {
		this.typeId = Card.values()[typeId];
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
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
		String message="Name: "+name+"\nType of Id: "+typeId+"\nNumber of identification: "+id+"\nPhone Number: "+phoneNumber+"\nAddress:"+address;
		return message;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public int compareTo(Client client) {
		return name.compareToIgnoreCase(client.getName());
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
	
	
	
	
	
	
}
