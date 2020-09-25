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
	
	public int organizeByDate(Order order) {
		int comp=getDate().compareTo(order.getDate());
		if(comp==0) {
			
		}
		return comp;
	}
	
	public int organizeById(Order order) {
		return getId().compareTo(order.getId());
	}
	
	@Override
	public String toString() {
		String listProducts="\nList of products:\n";
		for(int i=0; i<products.size(); i++) {
			listProducts+="Code of product:"+products.get(i)[0]+"\n";
			listProducts+="Quantity:"+products.get(i)[1];
		}
		String message="Order id: "+id+"\nStatus: "+state.toString()+"\nDate: "+date.toString()+"\nNit of restaurant who offers these products: "+nit+listProducts;
		return message;
	}

	public String getId() {
		return id;
	}

	public String getState() {
		return state.toString();
	}
	
	public String getState(int index) {
		return State.values()[index].toString();
	}

	public void setState(String state) {
		this.state = Status.valueOf(state);
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public ArrayList<String[]> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<String[]> products) {
		this.products = products;
	}
	
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
	
	public void setQuantityProduct(int position, String quantity) {
		products.get(position)[1]=quantity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}
