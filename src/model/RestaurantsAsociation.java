package model;

import java.util.ArrayList;

import exceptions.DuplicateProductException;
import exceptions.NotFoundRestaurantException;

public class RestaurantsAsociation {

	private ArrayList<Restaurant> restaurants;
	private ArrayList<Client> clients;
	
	public RestaurantsAsociation() {
		restaurants = new ArrayList<Restaurant>();
		clients = new ArrayList<Client>();
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
		if(findProduct(id, nit)!=null) {
			throw new DuplicateProductException(id);
		}
		Restaurant restaurant=findRestaurant(nit);
		restaurant.addProduct(id, name, description, price, nit);
	}
	
	public String registerClient(int typeId, String id, String name, String lastName, String phoneNumber, String address) {
		String message;
		if(findClient(id)==null) {
			addClient(typeId, id, name, lastName, phoneNumber, address);
			message="The client was added successfully";
		}else {
			message="The client was not added. There is already a client with the id: "+id;
		}
		return message;
	}
	
	public void registerOrder(String clientId, String nit, ArrayList<String[]> products) {
		Restaurant restaurant = findRestaurant(nit);
		Client client = findClient(clientId);
		Order order=new Order(clientId, nit, products);
		restaurant.getOrders().add(order);
		client.setOrder(order);
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
	
	public Product findProduct(String id, String nit) {
		Restaurant restaurant=findRestaurant(nit);
		Product product=null;
		boolean found=false;
		for(int i=0; i<restaurant.getProducts().size() && !found; i++) {
			if(restaurant.getProducts().get(i).getId().equalsIgnoreCase(id)) {
				found=true;
				product=restaurant.getProducts().get(i);
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
	}
	/**
	public String showProducts(String nit) {
		Restaurant restaurant=findRestaurant(nit);
		String products="";
		for(int i=0; i<restaurant.getProducts().size();i++) {
			products+=(i+1)+restaurant.getProducts().get(i).toString();
		}
		
	} **/
}
