package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.security.SecureRandom;
import java.awt.Taskbar.State;
import java.io.Serializable;
import java.math.BigInteger;

public class Order implements Serializable, Comparable<Order>{
	
	private static final long serialVersionUID = 1;

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
	public Order(String clientId, String nit, ArrayList<String[]> products){
		Calendar cal= Calendar.getInstance();
		date = cal.getTime();
		id = generateId();
		this.clientId = clientId;
		this.nit = nit;
		this.products = products;
		state = Status.REQUESTED;
	}
	
	/**
	 * It creates a new object type Order
	 * @param id The id 
	 * @param date The date
	 * @param state The state
	 * @param clientId The client id
	 * @param nit The NIT who offers these products
	 * @param products The list of products
	 */
	public Order(String id, Date date, int state, String clientId, String nit, ArrayList<String[]> products) {
		this.id=id;
		this.date=date;
		this.clientId = clientId;
		this.nit = nit;
		this.products = products;
		this.state = Status.values()[state];
		
	}
	
	/**
	 * It creates a random id
	 * @return The id
	 */
	public String generateId() {
		String id;
		do {
			SecureRandom random = new SecureRandom();
			id = new BigInteger(32, random).toString();
		}while(id.length()!=10);
		return id;
	}
	
	@Override
	/**
	 * It compares two Orders
	 * @return A number major than 0 if the order is major than the entered. A number minor than 0 if the order is minor. 0 if they are equals
	 */
	public int compareTo(Order order) {
		int comp;
		String nit1 = getNit();
		String nit2 = order.getNit();
		comp=nit1.compareTo(nit2);
		if(comp==0) {
			comp=organizeByClientId(order);
		}
		return comp;
	}
	
	/**
	 * It compares two orders by the client id
	 * @param order The order
	 * @return A number major than 0 if the order is major than the entered. A number minor than 0 if the order is minor. 0 if they are equals
	 */
	public int organizeByClientId(Order order) {
		int comp;
		String clientId1 = getClientId();
		String clientId2 = order.getClientId();
		comp=clientId2.compareTo(clientId1);
		if(comp==0) {
			comp=organizeByDate(order);
		}
		return comp;
	}
	
	/**
	 * It compares two orders by the date
	 * @param order The order
	 * @return A number major than 0 if the order is major than the entered. A number minor than 0 if the order is minor. 0 if they are equals
	 */
	public int organizeByDate(Order order) {
		int comp=getDate().compareTo(order.getDate());
		if(comp==0) {
			
		}
		return comp;
	}
	
	/**
	 * It compares two orders by the id
	 * @param order The order
	 * @return A number major than 0 if the order is major than the entered. A number minor than 0 if the order is minor. 0 if they are equals
	 */
	public int organizeById(Order order) {
		return getId().compareTo(order.getId());
	}
	
	@Override
	/**
	 * It returns the data of the restaurant in a message
	 * @return The message
	 */
	public String toString() {
		String listProducts="\nList of products:\n";
		for(int i=0; i<products.size(); i++) {
			listProducts+="Code of product:"+products.get(i)[0]+"\n";
			listProducts+="Quantity:"+products.get(i)[1];
		}
		String message="Order id: "+id+"\nStatus: "+state.toString()+"\nDate: "+date.toString()+"\nNit of restaurant who offers these products: "+nit+listProducts;
		return message;
	}

	/**
	 * @return The id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return The state
	 */
	public String getState() {
		return state.toString();
	}
	
	/**
	 * It returns the value of a position of Status
	 * @param index The index
	 * @return The value
	 */
	public String getState(int index) {
		return State.values()[index].toString();
	}

	/**
	 * @param state The state
	 */
	public void setState(String state) {
		this.state = Status.valueOf(state);
	}

	/**
	 * @return The client id
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId The client id
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return The NIT
	 */
	public String getNit() {
		return nit;
	}

	/**
	 * @param nit The NIT
	 */
	public void setNit(String nit) {
		this.nit = nit;
	}

	/**
	 * @return The list of products
	 */
	public ArrayList<String[]> getProducts() {
		return products;
	}

	/**
	 * @param products The list of products
	 */
	public void setProducts(ArrayList<String[]> products) {
		this.products = products;
	}
	
	/**
	 * It returns the psotion of a product given his id
	 * @param idProduct The id
	 * @return The position
	 */
	public int getPositionProducts(String idProduct) {
		int position=-1;
		boolean found=false;
		for(int i=0; i<products.size() && !found; i++) {
			if(products.get(i)[0].equals(idProduct)) {
				found=true;
				position=i;
			}
		}
		return position;
	}
	
	/**
	 * It sets the quantity of a product
	 * @param position The position
	 * @param quantity The quantity
	 */
	public void setQuantityProduct(int position, String quantity) {
		products.get(position)[1]=quantity;
	}

	/**
	 * @return The date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date The date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @param id The id
	 */
	public void setId(String id) {
		this.id = id;
	}	
}
