package ui;

import java.util.Scanner;
import model.RestaurantsAsociation;

public class Menu {
	
	private Scanner read;
	
	private RestaurantsAsociation consortium; 
	
	public Menu() {
		read=new Scanner(System.in);
		consortium=new RestaurantsAsociation();
	}
	
	public void showMenu() {
		System.out.println("What do you want to do?");
		System.out.println("1 Register a restaurant");
		int option=Integer.parseInt(read.nextLine());
		switch(option) {
		case 1:
			registerRestaurant();
			break;
		}
	}
	
	public void registerRestaurant() {
		System.out.println("Enter the name of the restaurant");
		String name=read.nextLine();
		System.out.println("Enter the NIT");
		String nit=read.nextLine();
		System.out.println("Enter the name of the administrator");
		String nameAdmin=read.nextLine();
		System.out.println(consortium.registerRestaurant(name, nit, nameAdmin));
		
		
	}
}
