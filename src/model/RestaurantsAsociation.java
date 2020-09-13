package model;

import java.util.ArrayList;

public class RestaurantsAsociation {

	private ArrayList<Restaurant> restaurants;
	private ArrayList<Client> clients;
	
	public RestaurantsAsociation() {
		restaurants = new ArrayList<Restaurant>();
		clients = new ArrayList<Client>();
	}
	
	public void registerRestaurant(String name, String nit, String nameAdmin) {
		restaurants.add(new Restaurant(name,nit,nameAdmin));
	}
}
