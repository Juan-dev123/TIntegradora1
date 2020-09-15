package model;

public class Client {
	
	enum Card{
		IDENTITY_CARD,
		CEDULA,
		PASSPORT,
		FOREIGNER_ID
	}
	
	private String id;
	private String name;
	private String lastName;
	private String phoneNumber;
	private String address;
	
	private Order order;
	private Card typeId;
	
	public Client(int typeId, String id, String name, String lastName, String phoneNumber, String address) {
		this.typeId = Card.values()[typeId];
		this.id = id;
		this.name = name;
		this.lastName=lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		String message="Name: "+name+" "+lastName+"\nType of Id: "+typeId+"\nNumber of identification: "+id+"\nPhone Number: "+phoneNumber+"\nAddress:"+address;
		return message;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
	
	
}
