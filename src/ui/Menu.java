package ui;

import java.util.ArrayList;
import java.util.Scanner;

import exceptions.DuplicateProductException;
import exceptions.NotFoundRestaurantException;
import model.RestaurantsAsociation;

public class Menu {
	
	private Scanner read;
	
	private RestaurantsAsociation consortium; 
	
	/**
	 * It creates a new object type Menu
	 */
	public Menu() {
		read=new Scanner(System.in);
		consortium=new RestaurantsAsociation();
	}
	
	/**
	 * It prints on screen the menu
	 */
	public void showMenu() {
		boolean stop=false;
		do {
			System.out.println("What do you want to do?");
			System.out.println("1 Register a restaurant");
			System.out.println("2 Register a product");
			System.out.println("3 Register a client");
			System.out.println("4 Register an order");
			System.out.println("5 Update the data of a restaurant");
			System.out.println("6 Update the data of a product");
			System.out.println("7");
			int option=Integer.parseInt(read.nextLine());
			switch(option) {
			case 1:
				registerRestaurant();
				break;
			case 2:
				registerProduct();
				break;
			case 3:
				registerClient();
				break;
			case 4:
				registerOrder();
				break;
			case 5:
				updateDataRestaurant();
			case -1:
				stop=true;
				break;
			}
		}while(!stop);
		
	}
	
	/**
	 * It registers a restaurant
	 */
	public void registerRestaurant() {
		System.out.println("Enter the name of the restaurant");
		String name=read.nextLine();
		System.out.println("Enter the NIT");
		String nit=read.nextLine();
		System.out.println("Enter the name of the administrator");
		String nameAdmin=read.nextLine();
		System.out.println(consortium.registerRestaurant(name, nit, nameAdmin));
		
			
	}
	
	/**
	 * It registers a product
	 */
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
	
	/**
	 * It registers a client 
	 */
	public void registerClient() {
		System.out.println("Enter the type of id ");
		int typeId;
		do {
			System.out.println("1 Identity card");
			System.out.println("2 Cedula");
			System.out.println("3 Passport");
			System.out.println("4 Foreigner id");
			typeId=Integer.parseInt(read.nextLine());
		}while(typeId>4 || typeId<1);
		System.out.print("Enter the number of the id:");
		String id=read.nextLine();
		System.out.print("Enter the full name:");
		String name=read.nextLine();
		System.out.print("Enter the phoneNumber:");
		String phoneNumber=read.nextLine();
		System.out.print("Enter the address:");
		String address=read.nextLine();
		System.out.println(consortium.registerClient(typeId, id, name, phoneNumber, address));	
	}
	
	/**
	 * It registers an order
	 */
	public void registerOrder() {
		ArrayList<String[]> products=new ArrayList<String[]>();
		System.out.print("Enter the client id: ");
		String clientId=read.nextLine();
		char answer;
		boolean firstTime=true;
		if(consortium.findClient(clientId)==null) {
			System.out.println("The client with the id "+clientId+" does not exist");
		}else {
			do {
				if(!firstTime) {
					System.out.print("Enter the client id: ");
					clientId=read.nextLine();
				}
				System.out.println(consortium.findClient(clientId));
				System.out.println("Is this the client? Y/N");
				answer=read.nextLine().toUpperCase().charAt(0);
				firstTime=false;
			}while(answer!='Y');
			
			firstTime=true;
			String nit="";
			do {
				if(!firstTime) {
					System.out.println("There is not exist a restaurant with the NIT: "+nit);
				}
				System.out.print("Enter the NIT of the restaurant: ");
				nit=read.nextLine();
			}while(consortium.findRestaurant(nit)!=null);
			String idProduct;
			do {
				System.out.println("When you want to stop enter -1");
				System.out.println("Enter the id of the product");
				idProduct=read.nextLine();
				if(consortium.findProduct(idProduct)==null) {
					System.out.println("The product with the id "+idProduct+" does not exist");
				}else {
					System.out.print("Enter que quantity: ");
					int quantity=Integer.parseInt(read.nextLine());
					boolean found=false;
					for(int i=0; i<products.size(); i++) {
						if(products.get(i)[0].equals(consortium.findProduct(idProduct).getId())) {
							quantity+=Integer.parseInt(products.get(i)[1]);
							products.get(i)[1]=String.valueOf(quantity);
							found=true;
						}
					}
					if(!found) {
						String[] order = {consortium.findProduct(idProduct).getId(), String.valueOf(quantity)};
						products.add(order);
					}
				}
			}while(!(idProduct.equals("-1")));
			System.out.println("The order was registered successfully");
		}	
	}
	
	/**
	 * It updates the data of a restaurant
	 */
	public void updateDataRestaurant(){
		System.out.println("Enther the NIT of the restaurant");
		String nit=read.nextLine();
		if(consortium.findRestaurant(nit)==null) {
			System.out.println("There is not exists a restaurant with the nit: "+nit);
		}else {
			int option=printSubMenu();
			switch(option) {
			case 1:
				System.out.println("Which one do you want to change?");
				System.out.println("1 Name of the restaurant");
				System.out.println("2 NIT");
				System.out.println("3 Name of the administrator");
				option=Integer.parseInt(read.nextLine());
				switch(option) {
				case 1:
					System.out.println("Enter the new name of the restaurant: ");
					String name=read.nextLine();
					consortium.updateDataRestaurant(nit, option, name);
					break;
				case 2:
					System.out.println("Enter the new NIT: ");
					String newNit=read.nextLine();
					consortium.updateDataRestaurant(nit, option, newNit);;
					break;
				case 3:
					System.out.println("Enter the new name of the administrator: ");
					String nameAdmin=read.nextLine();
					consortium.updateDataRestaurant(nit, option, nameAdmin);
					break;
				}
				break;
			case 2:
				System.out.println("Enter the new name of the restaurant: ");
				String name=read.nextLine();
				System.out.println("Enter the new NIT: ");
				String newNit=read.nextLine();
				System.out.println("Enter the name of the administrator: ");
				String nameAdmin=read.nextLine();
				consortium.updateDataRestaurant(nit, name, newNit, nameAdmin);
				break;
			default:
				System.out.println("Error");
			}
			
		}
	}
	
	public void updateProduct() {
		System.out.println("Enter the id of the product:");
		String id=read.nextLine();
		if(consortium.findProduct(id)==null) {
			System.out.println("There is not a product with the id: ");
		}else {
			int option=printSubMenu();
			switch(option) {
			case 1:
				System.out.println("Which one do you want to change?");
				System.out.println("1 Number of the id");
				System.out.println("2 Name");
				System.out.println("3 Description");
				System.out.println("4 Price");
				System.out.println("5 NIT of the restaurant that offers this product");
				option=Integer.parseInt(read.nextLine());
				switch(option) {
				case 1:
					System.out.print("Enter the new number of the id: ");
					String newId=read.nextLine();
					consortium.updateDataProduct(id, option, newId);
					break;
				case 2:
					System.out.print("Enter the new name: ");
					String name=read.nextLine();
					consortium.updateDataProduct(id, option, name);
					break;
				case 3:
					System.out.print("Enter the new description: ");
					String description=read.nextLine();
					consortium.updateDataProduct(id, option, description);
					break;
				case 4:
					System.out.print("Enter the new price: ");
					String price=read.nextLine();
					consortium.updateDataProduct(id, option, price);
					break;
				case 5:
					System.out.print("Enter the new NIT: ");
					String nit=read.nextLine();
					consortium.updateDataProduct(id, option, nit);
					break;
				}
				break;
			case 2:
				break;
			}
		}
	}
	
	/**
	 * It prints a submenu with the options to update all the data or only one data
	 * @return The option
	 */
	public int printSubMenu() {
		int option;
		do {
			System.out.println("1 Change only one data");
			System.out.println("2 Change all the data");
			option=Integer.parseInt(read.nextLine());
		}while(option>2 || option<1);
		return option;
		
	}
}
