package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import exceptions.DuplicateProductException;
import exceptions.NotFoundRestaurantException;

public class RestaurantsAsociation {

	public static final String SAVE_PATH_FILE = "data/restaurantsAsociation.mor";
	
	private ArrayList<Restaurant> restaurants;
	private ArrayList<Client> clients;
	private ArrayList<Product> products;
	private ArrayList<Order> orders;
	
	public RestaurantsAsociation() {
		restaurants = new ArrayList<Restaurant>();
		clients = new ArrayList<Client>();
		products = new ArrayList<Product>();
		orders=new ArrayList<Order>(); 
	}
	
	public String registerRestaurant(String name, String nit, String nameAdmin){
		String message;
		if(findRestaurant(nit)==null) {
			restaurants.add(new Restaurant(name,nit,nameAdmin));
			message="The restaurant was registered successfully";
		}else {
			message="The restaurant was not registered.\nThere is already a restaurant registered with the NIT: "+nit;
		}
		return message;
	}
	
	public void registerProduct(String id, String name, String description, double price, String nit) throws NotFoundRestaurantException, DuplicateProductException{
		if(findRestaurant(nit)==null) {
			throw new NotFoundRestaurantException(nit);
		}
		if(findProduct(id)!=null) {
			throw new DuplicateProductException(id);
		}
		Product product=new Product(id, name, description, price, nit);
		products.add(product);
	}
	
	public String registerClient(int typeId, String id, String name, String phoneNumber, String address) {
		String message;
		if(findClient(id)==null) {
			addClient(typeId, id, name, phoneNumber, address);
			message="The client was added successfully";
		}else {
			message="The client was not added. There is already a client with the id: "+id;
		}
		return message;
	}
	
	public String registerOrder(String clientId, String nit, ArrayList<String[]> products) {
		String message;
		if(checkProducts(nit, products)) {
			message="All the products do not belong to the same restaurant. So, the order was not registered";
		}else {
			Client client = findClient(clientId);
			Order order=new Order(clientId, nit, products);
			orders.add(order);
			client.setOrder(order);
			message="The order was registered successfully";
		}
		return message;
	}
	
	public void updateDataRestaurant(String nit, int option, String data) {
		Restaurant restaurant=findRestaurant(nit);
		switch(option) {
		case 1: //Change name of the restaurant
			restaurant.setName(data);
			break;
		case 2: //Change nit
			restaurant.setNit(data);
			break;
		case 3: //Change name of the admin
			restaurant.setNameAdmin(data);
			break;
		}
	}
	
	public void updateDataProduct(String id, int option, String data) {
		Product product=findProduct(id);
		switch(option) {
		case 1:
			product.setId(data);
			break;
		case 2:
			product.setName(data);
			break;
		case 3:
			product.setDescription(data);
			break;
		case 4:
			
			product.setPrice(Double.parseDouble(data));
			break;
		case 5:
			product.setNit(data);
			break;
		}
	}
	
	public void updateDataClient(String id, int option, String data) {
		Client client=findClient(id);
		switch(option) {
		case 1:
			client.setTypeId(Integer.parseInt(data));
			break;
		case 2:
			client.setId(data);
			break;
		case 3:
			client.setName(data);
			break;
		case 4:
			client.setPhoneNumber(data);
			break;
		case 5:
			client.setAddress(data);
			break;
		}
	}
	
	public void updateDataOrder(String id, int option, String data) {
		Order order=findOrder(id);
		switch(option) {
		case 1:
			order.setClientId(data);
			break;
		case 2:
			order.setNit(data);
			break;
		case 4:
			order.setState(data);
			break;
		}
	}
	
	public void updateDataOrder(String id, ArrayList<String[]> products) {
		Order order=findOrder(id);
		order.setProducts(products);
	}
	
	public String removeOrder(String id) {
		String message;
		Order order=findOrder(id);
		if(order==null) {
			message="There is no order with the id "+id;
		}else {
			orders.remove(orders.indexOf(order));
			message="The order was removed successfully";
		}
		return message;
	}
	
	public void updateQuantityProduct(String id, int position, String quantity) {
		Order order=findOrder(id);
		order.setQuantityProduct(position, quantity);
	}
	
	public Restaurant findRestaurant(String nit) {
		Restaurant restaurant=null;
		boolean found=false;
		for(int i=0; i<restaurants.size() && !found; i++) {
			if(restaurants.get(i).getNit().equals(nit)) {
				found=true;
				restaurant=restaurants.get(i);
			}
		}
		return restaurant;
	}
	
	public Product findProduct(String id) {
		Product product=null;
		boolean found=false;
		for(int i=0; i<products.size() && !found; i++) {
			if(products.get(i).getId().equalsIgnoreCase(id)) {
				found=true;
				product=products.get(i);
			}
		}
		return product;
	}
	
	public Client findClient(String id) {
		Client client=null;
		boolean found=false;
		for(int i=0; i<clients.size() && !found; i++) {
			if(clients.get(i).getId().equals(id)) {
				found=true;
				client=clients.get(i);
			}
		}
		return client;
	}
	
	public Order findOrder(String id) {
		Order order=null;
		boolean found=false;
		for(int i=0; i<orders.size() && !found; i++) {
			if(orders.get(i).getId().equals(id)) {
				found=true;
				order=orders.get(i);
			}
		}
		return order;
	}
	
	public void addClient(int typeId, String id, String name, String phoneNumber, String address) {
		Client client = new Client(typeId, id, name, phoneNumber, address);
		if(clients.isEmpty()) {
			clients.add(client);
		}else {
			boolean inserted=false;
			for(int i=0; i<clients.size() && !inserted; i++) {
				if(clients.get(i).compareTo(client)<0) {
					clients.add(i, client);
				}
			}
		}
		
	}
	
	public boolean checkProducts(String nit, ArrayList<String[]> products) {
		boolean allProductsBelong=true;;
		for(String[] product : products) {
			if(!(product[0].equals(nit))) {
				allProductsBelong=false;
			}
		}
		return allProductsBelong;
	}
	
	public void saveData() throws FileNotFoundException, IOException{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE));
		oos.writeObject(restaurants);
		oos.writeObject(clients);
		oos.writeObject(products);
		oos.writeObject(orders);
		oos.close();
	}
	
	public ArrayList<Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(ArrayList<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public ArrayList<Client> getClients() {
		return clients;
	}

	public void setClients(ArrayList<Client> clients) {
		this.clients = clients;
	}


	
	/**
	public void addClient(int typeId, String id, String name, String lastName, String phoneNumber, String address) {
		if(clients.size()==0) {
			clients.add(new Client(typeId, id, name, lastName, phoneNumber, address));
		}else {
			Client temp1;
			Client temp2;
			boolean inserted=false;
			for(int i=0; i<clients.size() && !inserted; i++) {
				if(clients.get(i).getLastName().compareToIgnoreCase(lastName)==0) {
					if(clients.get(i).getName().compareToIgnoreCase(name)<=0) {
						temp1=clients.get(i);
						clients.set(i, new Client(typeId, id, name, lastName, phoneNumber, address));
						inserted=true;
						for(int j=i; j<clients.size()-1; j++) {
							temp2=clients.get(j+1);
							clients.set(j+1, temp1);
							temp1=temp2;
						}
						clients.add(temp1);
					}
					
				}else if(clients.get(i).getLastName().compareToIgnoreCase(lastName)<0) {
					temp1=clients.get(i);
					clients.set(i, new Client(typeId, id, name, lastName, phoneNumber, address));
					inserted=true;
					for(int j=i; j<clients.size()-1; j++) {
						temp2=clients.get(j+1);
						clients.set(j+1, temp1);
						temp1=temp2;
					}
					clients.add(temp1);
				}
			}
		}
	} **/
	/**
	public String showProducts(String nit) {
		Restaurant restaurant=findRestaurant(nit);
		String products="";
		for(int i=0; i<restaurant.getProducts().size();i++) {
			products+=(i+1)+restaurant.getProducts().get(i).toString();
		}
		
	} **/
}
