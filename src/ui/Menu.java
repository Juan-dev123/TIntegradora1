package ui;

import java.util.Scanner;

import exceptions.DuplicateProductException;
import exceptions.NotFoundRestaurantException;
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
		System.out.println("2 Register a product");
		int option=Integer.parseInt(read.nextLine());
		switch(option) {
		case 1:
			registerRestaurant();
			break;
		case 2:
			registerProduct();
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
	
	public void registerProduct() {
		System.out.print("Enter the id of the product: ");
		String id=read.nextLine();
		System.out.print("Enter the name:");
		String name=read.nextLine();
		System.out.print("Enter a description:");
		String description=read.nextLine();
		System.out.print("Enter the price:");
		double price=Double.parseDouble(read.nextLine());
		System.out.print("Enter the NIT of the restaurant who offers this product:");
		String nit=read.nextLine();
		try {
			consortium.registerProduct(id, name, description, price, nit);
			System.out.println("The product was registered successfully");
		}catch(NotFoundRestaurantException nfr) {
			System.out.println(nfr.getMessage());
		}catch(DuplicateProductException dp) {
			System.out.println(dp.getMessage());
		}
		
		
	}
}
