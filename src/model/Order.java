package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.security.SecureRandom;
import java.math.BigInteger;

public class Order {
	
	enum Status{
		REQUESTED,
		PROCESSING,
		SENT,
		DELIVERED
	}
	
	String id;
	Date date;;
	String clientId;
	String nit;
	ArrayList<String[]> products;
	Status state;

	/**
	 * It creates a new object type Order
	 * @param clientId The number of the client id
	 * @param nit The NIT of the restaurant who received the order
	 * @param products A list of the products ordered
	 */
	public Order(String clientId, String nit, ArrayList<String[]> products) {
		Calendar cal= Calendar.getInstance();
		date = cal.getTime();
		id = generateId();
		this.clientId = clientId;
		this.nit = nit;
		this.products = products;
		state = Status.REQUESTED;
	}
	
	/**
	 * It creates a random id
	 * @return The id
	 */
	public String generateId() {
		SecureRandom random = new SecureRandom();
		String id = new BigInteger(130, random).toString(32);
		return id;
	}
	
	
}
