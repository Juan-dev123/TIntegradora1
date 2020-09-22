
package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
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
			System.out.println("01 Register a restaurant");
			System.out.println("02 Register a product");
			System.out.println("03 Register a client");
			System.out.println("04 Register an order");
			System.out.println("05 Update the data of a restaurant");
			System.out.println("06 Update the data of a product");
			System.out.println("07 Update the data of a client");
			System.out.println("08 Update the data of an order");
			System.out.println("09 Export the orders");
			System.out.println("10 Show restaurants in ascending alphabetical order");
			System.out.println("11 Show clients in order of their descending phone number");
			
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
				break;
			case 6:
				updateProduct();
				break;
			case 7:
				updateClient();
				break;
			case 8:
				updateOrder();
				break;
			case 9:
				exportOrders();
				break;
			case 10:
				showRestaurants();
				break;
			case 11:
				showClients();
				break;
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
		try {
			consortium.saveData("restaurant");
		}catch(FileNotFoundException fnf) {
			System.out.println(fnf.getMessage());
		}catch(IOException io) {
			System.out.println(io.getMessage());
		}
		
			
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
			consortium.saveData("product");
			System.out.println("The product was registered successfully");
		}catch(NotFoundRestaurantException nfr) {
			System.out.println(nfr.getMessage());
		}catch(DuplicateProductException dp) {
			System.out.println(dp.getMessage());
		}catch(FileNotFoundException fnf) {
			System.out.println(fnf.getMessage());
		}catch(IOException io) {
			System.out.println(io.getMessage());
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
		try {
			consortium.saveData("client");
		}catch(FileNotFoundException fnf) {
			System.out.println(fnf.getMessage());
		}catch(IOException io) {
			System.out.println(io.getMessage());
		}
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
			addProducts(products);
			System.out.println(consortium.registerOrder(clientId, nit, products));
			try {
				consortium.saveData("order");
			}catch(FileNotFoundException fnf) {
				System.out.println(fnf.getMessage());
			}catch(IOException io) {
				System.out.println(io.getMessage());
			}
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
			System.out.println("Which one do you want to change?");
			System.out.println("1 Name of the restaurant");
			System.out.println("2 NIT");
			System.out.println("3 Name of the administrator");
			int option=Integer.parseInt(read.nextLine());
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
			try {
				consortium.saveData("restaurant");
			}catch(FileNotFoundException fnf) {
				System.out.println(fnf.getMessage());
			}catch(IOException io) {
				System.out.println(io.getMessage());
			}
		}
	}
	
	public void updateProduct() {
		System.out.print("Enter the id of the product:");
		String id=read.nextLine();
		if(consortium.findProduct(id)==null) {
			System.out.println("There is not a product with the id: ");
		}else {
			System.out.println("Which one do you want to change?");
			System.out.println("1 Number of the id");
			System.out.println("2 Name");
			System.out.println("3 Description");
			System.out.println("4 Price");
			System.out.println("5 NIT of the restaurant that offers this product");
			int option=Integer.parseInt(read.nextLine());
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
			try {
				consortium.saveData("product");
			}catch(FileNotFoundException fnf) {
				System.out.println(fnf.getMessage());
			}catch(IOException io) {
				System.out.println(io.getMessage());
			}
		}
	}
	
	public void updateClient() {
		System.out.print("Enter the id of the client: ");
		String id=read.nextLine();
		if(consortium.findClient(id)==null) {
			System.out.println("There is no client with the id: "+id);
		}else {
			System.out.println("Which one do you want to change?");
			System.out.println("1 Type of id");
			System.out.println("2 Number of the id");
			System.out.println("3 The full name");
			System.out.println("4 The phone number");
			System.out.println("5 The address");
			System.out.println("6 The products list");
			int option=Integer.parseInt(read.nextLine());
			
			switch(option) {
			case 1:
				int typeId;
				do {
					System.out.println("Select the type of id");
					System.out.println("1 Identity card");
					System.out.println("2 Cedula");
					System.out.println("3 Passport");
					System.out.println("4 Foreigner id");
					typeId=Integer.parseInt(read.nextLine());
				}while(typeId>4 || typeId<1);
				consortium.updateDataClient(id, option, String.valueOf(typeId));
				break;
			case 2:
				System.out.print("Enter the new id: ");
				String newId=read.nextLine();
				consortium.updateDataClient(id, option, newId);
				break;
			case 3:
				System.out.print("Enter the new name: ");
				String name=read.nextLine();
				consortium.updateDataClient(id, option, name);
				break;
			case 4:
				System.out.print("Enter the new phone number: ");
				String phoneNumber=read.nextLine();
				consortium.updateDataClient(id, option, phoneNumber);
				break;
			case 5:
				System.out.print("Enter the new address: ");
				String address=read.nextLine();
				consortium.updateDataProduct(id, option, address);
				break;
			case 6:
				do {
					System.out.println("What do you want to do?");
					System.out.println("1 Update an order");
					System.out.println("2 Remove an order");
					option=Integer.parseInt(read.nextLine());
				}while(option>2 || option<1);
				switch(option) {
				case 1:
					updateOrder();
					break;
				case 2:
					System.out.print("Enter the id of the order: ");
					String orderId=read.nextLine();
					System.out.println(consortium.removeOrder(orderId));
					break;
				}
			}
			try {
				consortium.saveData("client");
			}catch(FileNotFoundException fnf) {
				System.out.println(fnf.getMessage());
			}catch(IOException io) {
				System.out.println(io.getMessage());
			}
		}
	}
	
	public void updateOrder() {
		System.out.print("Enter the id of the order: ");
		String id=read.nextLine();
		if(consortium.findOrder(id)==null) {
			System.out.println("There is no order with the id: "+id);
		}else {
			System.out.println("Which one do you want to change?");
			System.out.println("1 The number of the id of the client");
			System.out.println("2 The NIT of the restaurant");
			System.out.println("3 The list of products");
			System.out.println("4 The status of the order");
			int option=Integer.parseInt(read.nextLine());
			
			switch(option) {
			case 1:
				System.out.print("Enter the new id of the client: ");
				String clientId=read.nextLine();
				consortium.updateDataOrder(id, option, clientId);
				break;
			case 2:
				System.out.print("Enter the new NIT: ");
				String nit=read.nextLine();
				consortium.updateDataOrder(id, option, nit);
				break;
			case 3:
				updateProductsList(id);
				break;
			}
			try {
				consortium.saveData("order");
			}catch(FileNotFoundException fnf) {
				System.out.println(fnf.getMessage());
			}catch(IOException io) {
				System.out.println(io.getMessage());
			}
		}
	}
	
	public void exportOrders() {
		System.out.print("Enter the name of the file: ");
		String fileName=read.nextLine();
		System.out.print("Enter the separator: ");
		String separator=read.nextLine();
		try {
			System.out.println(consortium.exportOrders(fileName, separator));
		}catch(FileNotFoundException fnf) {
			System.out.println(fnf.getMessage());
		}
		
	}
	
	public void showRestaurants() {
		System.out.print(consortium.showRestaurants());
	}
	
	public void showClients() {
		System.out.print(consortium.showClients());
	}
	
	public String changeOrderStatus(String orderId) {
		String status=consortium.findOrder(orderId).getState();
		int option;
		System.out.println("Enter the new state");
		switch(status) {
		case "REQUESTED":
			do {
				System.out.println("1 Processing");
				System.out.println("2 Sent");
				System.out.println("3 Delivered");
				option=Integer.parseInt(read.nextLine());
			}while(option>3 || option<1);
			option++;
			status=consortium.findOrder(orderId).getState(option);
			break;
		case "PROCESSING":
			do {
				System.out.println("1 Sent");
				System.out.println("2 Delivered");
				option=Integer.parseInt(read.nextLine());
			}while(option>2 || option<1);
			option=option+2;
			status=consortium.findOrder(orderId).getState(option);
			break;
		case "SENT":
			do {
				System.out.println("1 Delivered");
				option=Integer.parseInt(read.nextLine());
			}while(option!=1);
			option=option+3;
			status=consortium.findOrder(orderId).getState(option);
			break;
		}
		
		return status;
	}
	
	public void addProducts(ArrayList<String[]> products) {
		String idProduct;
		do {
			System.out.println("When you want to stop enter -1");
			System.out.println("Enter the id of the product");
			idProduct=read.nextLine();
			if(consortium.findProduct(idProduct)==null && !idProduct.equals("-1")) {
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
	}
	
	public void updateProductsList(String id) {
		int option;
		System.out.println("What do you want to do?");
		System.out.println("1 Add a product");
		System.out.println("2 Remove a product");
		System.out.println("3 Change the quantity of a product");
		option=Integer.parseInt(read.nextLine());
		String product;
		int position;
		switch(option) {
		case 1:
			addProducts(consortium.findOrder(id).getProducts());
			break;
		case 2:
			System.out.print("Enter the id of the product that you want to remove: ");
			product=read.nextLine();
			position=consortium.findOrder(id).getPositionProducts(product);
			if(position==-1) {
				System.out.println("There is no product with the id: "+product);
			}else {
				consortium.findOrder(id).getProducts().remove(position);
				System.out.println("The product was removed successfully");
			}
			break;
		case 3:
			System.out.print("Enter the id of the product: ");
			product=read.nextLine();
			position=consortium.findOrder(id).getPositionProducts(product);
			if(position==-1) {
				System.out.println("There is no product with the id: "+product);
			}else {
				System.out.print("Enter the new quantity: ");
				String quantity=read.nextLine();
				consortium.updateQuantityProduct(id, position, quantity);
			}
			break;
		}
	}
}
