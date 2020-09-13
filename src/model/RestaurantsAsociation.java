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
}
