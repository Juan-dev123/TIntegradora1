package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.security.SecureRandom;
import java.math.BigInteger;

public class Order {
	
	public static final int REQUESTED=1;
	public static final int PROCESSING=2;
	public static final int SENT=3;
	public static final int DELIVERED=4;
	
	String id;
	Date date;;
	String clientId;
	String nit;
	ArrayList<String[]> products;
	int state;
	
	public Order(String clientId, String nit, ArrayList<String[]> products) {
		Calendar cal= Calendar.getInstance();
		date = cal.getTime();
		//Missing
		id = generateId();
		this.clientId = clientId;
		this.nit = nit;
		this.products = products;
		state = Order.REQUESTED;
	}
	
	public String generateId() {
		SecureRandom random = new SecureRandom();
		String id = new BigInteger(130, random).toString(32);
		return id;
	}
	
	
}
