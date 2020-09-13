package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
		id = "";
		this.clientId = clientId;
		this.nit = nit;
		this.products = products;
		state = Order.REQUESTED;
	}
	
	
}
