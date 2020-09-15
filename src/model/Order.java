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
	
	public Order(String clientId, String nit, ArrayList<String[]> products) {
		Calendar cal= Calendar.getInstance();
		date = cal.getTime();
		id = generateId();
		this.clientId = clientId;
		this.nit = nit;
		this.products = products;
		state = Status.REQUESTED;
	}
	
	public String generateId() {
		SecureRandom random = new SecureRandom();
		String id = new BigInteger(130, random).toString(32);
		return id;
	}
	
	
}
