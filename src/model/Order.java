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
	
	public Order(String id, Date date, String clientId, String nit, ArrayList<String[]> products) {
		this.id=id;
		this.date=date;
		this.clientId = clientId;
		this.nit = nit;
		this.products = products;
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
		int nit1 = Integer.parseInt(getNit());
		int nit2 = Integer.parseInt(order.getNit());
		comp=nit1-nit2;
		if(comp==0) {
			comp=organizeByClientId(order);
		}
		return comp;
	}
	
	public int organizeByClientId(Order order) {
		int comp;
		int clientId1 = Integer.parseInt(getClientId());
		int clientId2 = Integer.parseInt(order.getClientId());
		comp=clientId2-clientId1;
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
