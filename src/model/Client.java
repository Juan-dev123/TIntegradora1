package model;

public class Client {
	
	public static final int IDENTITY_CARD=1;
	public static final int CEDULA=2;
	public static final int PASSPORT=3;
	public static final int FOREIGNER_ID=4;
	
	int typeId;
	String id;
	String name;
	String lastName;
	String phoneNumber;
	String address;
	
	private Order order;
	
	public Client(int typeId, String id, String name, String lastName, String phoneNumber, String address) {
		this.typeId = typeId;
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
	
	
	
	
}
