package model;

import java.util.ArrayList;

import exceptions.DuplicateProductException;
import exceptions.NotFoundRestaurantException;

public class RestaurantsAsociation {

	private ArrayList<Restaurant> restaurants;
	private ArrayList<Client> clients;
	private ArrayList<Product> products;
	
	public RestaurantsAsociation() {
		restaurants = new ArrayList<Restaurant>();
		clients = new ArrayList<Client>();
		products = new ArrayList<Product>(); 
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
	
	public void registerOrder(String clientId, String nit, ArrayList<String[]> products) {
		Restaurant restaurant = findRestaurant(nit);
		Client client = findClient(clientId);
		Order order=new Order(clientId, nit, products);
		restaurant.getOrders().add(order);
		client.setOrder(order);
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
	
	public void updateDataRestaurant(String nit, String name, String newNit, String nameAdmin) {
		Restaurant restaurant=findRestaurant(nit);
		restaurant.setName(name);
		restaurant.setNameAdmin(nameAdmin);
		restaurant.setNit(newNit);
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
