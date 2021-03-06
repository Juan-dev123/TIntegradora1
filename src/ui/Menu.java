
package ui;

import java.io.File;
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
		try {
			consortium=new RestaurantsAsociation();
		}catch(ClassNotFoundException cnf) {
			System.out.println(cnf.getMessage());
		}catch(IOException io) {
			System.out.println(io.getMessage());
		}
		
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
			System.out.println("12 Search for a client by his name efficiently");
			System.out.println("13 Import data of restaurants from a file cvs");
			System.out.println("14 Import data of clients from a file cvs");
			System.out.println("15 Import data of products from a file cvs");
			System.out.println("16 Import data of orders from a file cvs");
			System.out.println("17 Exit");
			
			
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
			case 12:
				searchClientByName();
				break;
			case 13:
				importRestaurants();
				break;
			case 14:
				importClients();
				break;
			case 15:
				importProducts();
				break;
			case 16:
				importOrders();
				break;
			case 17:
				stop=true;
				break;
			}
		}while(!stop);
		
	}
	
	/**
	 * It registers a restaurant
	 */
	public void registerRestaurant() {
		System.out.print("Enter the name of the restaurant: ");
		String name=read.nextLine();
		System.out.print("Enter the NIT: ");
		String nit=read.nextLine();
		System.out.print("Enter the name of the administrator: ");
		String nameAdmin=read.nextLine();
		if(consortium.registerRestaurant(name, nit, nameAdmin)){
			System.out.println("The restaurant was registered successfully");
		}else {
			System.out.println("The restaurant was not registered.\nThere is already a restaurant registered with the NIT: "+nit);
		}
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
		System.out.print("Enter the name:");
		String name=read.nextLine();
		System.out.print("Enter the last name: ");
		String lastName=read.nextLine();
		System.out.print("Enter the phoneNumber:");
		String phoneNumber=read.nextLine();
		System.out.print("Enter the address:");
		String address=read.nextLine();
		boolean registered=consortium.registerClient(typeId, id, name, lastName,phoneNumber, address);
		if(registered) {
			System.out.println("The client was added successfully");
			try {
				consortium.saveData("client");
			}catch(FileNotFoundException fnf) {
				System.out.println(fnf.getMessage());
			}catch(IOException io) {
				System.out.println(io.getMessage());
			}
		}else {
			System.out.println("The client was not added. There is already a client with the id: "+id);	
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
				firstTime=false;
			}while(consortium.findRestaurant(nit)==null);
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

	/**
	 * It updates the data of a product
	 */
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
	
	/**
	 * It updates the data of a client
	 */
	public void updateClient() {
		System.out.print("Enter the id of the client: ");
		String id=read.nextLine();
		if(consortium.findClient(id)==null) {
			System.out.println("There is no client with the id: "+id);
		}else {
			System.out.println("Which one do you want to change?");
			System.out.println("1 Type of id");
			System.out.println("2 Number of the id");
			System.out.println("3 The name");
			System.out.println("4 The last name");
			System.out.println("5 The phone number");
			System.out.println("6 The address");
			System.out.println("7 The products list");
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
				System.out.print("Enter the new last name: ");
				String lastName=read.nextLine();
				consortium.updateDataClient(id, option, lastName);
				break;
			case 5:
				System.out.print("Enter the new phone number: ");
				String phoneNumber=read.nextLine();
				consortium.updateDataClient(id, option, phoneNumber);
				break;
			case 6:
				System.out.print("Enter the new address: ");
				String address=read.nextLine();
				consortium.updateDataProduct(id, option, address);
				break;
			case 7:
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
	
	/**
	 * It updates the data of an order
	 */
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
	
	/**
	 * It exports in a file cvs the data of the orders
	 */
	public void exportOrders() {
		System.out.print("Enter the name of the file without \".cvs\":: ");
		String fileName=read.nextLine()+".cvs";
		System.out.print("Enter the separator: ");
		String separator=read.nextLine();
		createOrdersFile(fileName, separator);
	}
	
	/**
	 * It prints on console all the restaurants ordered by ascending name
	 */
	public void showRestaurants() {
		System.out.print(consortium.showRestaurants());
	}
	
	/**
	 * It prints on console all the clients ordered by descending phone number
	 */
	public void showClients() {
		System.out.print(consortium.showClients());
	}
	
	/**
	 * It searches a client by his name
	 */
	public void searchClientByName() {
		System.out.print("Enter the name: ");
		String name = read.nextLine().toUpperCase();
		System.out.print("Enter the last name: ");
		String lastName = read.nextLine().toUpperCase();
		System.out.println(consortium.searchClientByName(name, lastName));
	}
	
	/**
	 * It changes the status of on order
	 * @param orderId The id of the order
	 * @return The status
	 */
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
	
	/**
	 * It adds products to one order
	 * @param products The list of products
	 */
	public void addProducts(ArrayList<String[]> products) {
		String idProduct;
		do {
			System.out.println("When you want to stop enter -1");
			System.out.print("Enter the id of the product: ");
			idProduct=read.nextLine();
			if(consortium.findProduct(idProduct)==null && !idProduct.equals("-1")) {
				System.out.println("The product with the id "+idProduct+" does not exist");
			}else if(!idProduct.equals("-1")){
				int quantity;
				do {
					System.out.print("Enter que quantity: ");
					quantity=Integer.parseInt(read.nextLine());
				}while(quantity<1);
				
				boolean found=false;
				for(int i=0; i<products.size(); i++) {
					if(products.get(i)[0].equals(idProduct)) {
						quantity+=Integer.parseInt(products.get(i)[1]);
						products.get(i)[1]=String.valueOf(quantity);
						found=true;
					}
				}
				if(!found) {
					String[] order = {idProduct, String.valueOf(quantity)};
					products.add(order);
				}
			}
		}while(!(idProduct.equals("-1")));
	}
	
	/**
	 * It updates the data of a list of products 
	 * @param id
	 */
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
	
	/**
	 * It creates a file cvs with all the orders
	 * @param fileName The name of the file
	 * @param separator The separator
	 */
	public void createOrdersFile(String fileName, String separator) {
		File file = new File(RestaurantsAsociation.DATA_PATH_FILE+fileName);
		if(file.exists()) {
			char answer;
			do {
				System.out.println("A file with that name already exist");
				System.out.println("Do you want to overwrite the file? Y/N");
				answer = read.nextLine().toUpperCase().charAt(0);
			}while(answer!='Y' && answer!='N');
			if(answer=='Y') {
				try {
					System.out.println(consortium.exportOrders(file, separator));
				}catch(FileNotFoundException fnf) {
					System.out.println(fnf.getMessage());
				}
			}else {
				System.out.println("Enter another name: ");
				String anotherName = read.nextLine();
				createOrdersFile(anotherName, separator);
			}	
		}else {
			try {
				System.out.println(consortium.exportOrders(file, separator));
			}catch(FileNotFoundException fnf) {
				System.out.println(fnf.getMessage());
			}
		}
		
	}
	
	/**
	 * It imports data of restaurants from a file cvs
	 */
	public void importRestaurants() {
		System.out.print("Enter the name of the file without \".cvs\": ");
		String fileName=read.nextLine();
		File file = new File(RestaurantsAsociation.DATA_PATH_FILE+fileName+".cvs");
		if(!file.exists()) {
			System.out.println("There is no file with the name "+fileName);
		}else {
			try {
				String[] message=consortium.importRestaurants(file);
				System.out.println(message[0]);
				char answer;
				if(message[1]!="") {
					do {
						System.out.println("Do you want to see the restaurants that were not imported Y/N");
						answer=read.nextLine().toUpperCase().charAt(0);
					}while(answer!='Y' && answer!='N');
					if(answer=='Y') {
						System.out.print(message[1]);
					}
				}
				consortium.saveData("restaurant");
			}catch(IOException io) {
				System.out.println(io.getMessage());
			}
			
		}
	}
	
	/**
	 * It imports data of products from a file cvs
	 */
	public void importProducts() {
		System.out.print("Enter the name of the file without \".cvs\": ");
		String fileName=read.nextLine();
		File file = new File(RestaurantsAsociation.DATA_PATH_FILE+fileName+".cvs");
		if(!file.exists()) {
			System.out.println("There is no file with the name "+fileName);
		}else {
			try {
				String[] message=consortium.importProducts(file);
				System.out.println(message[0]);
				char answer;
				if(message[1]!="") {
					do {
						System.out.println("Do you want to see the products that were not imported Y/N");
						answer=read.nextLine().toUpperCase().charAt(0);
					}while(answer!='Y' && answer!='N');
					if(answer=='Y') {
						System.out.print(message[1]);
					}
				}
				consortium.saveData("product");
			}catch(IOException io) {
				System.out.println(io.getMessage());
			}
		}
	}
	
	/**
	 * It imports data of orders from a file cvs
	 */
	public void importOrders() {
		System.out.print("Enter the name of the file without \".cvs\": ");
		String fileName=read.nextLine();
		File file = new File(RestaurantsAsociation.DATA_PATH_FILE+fileName+".cvs");
		if(!file.exists()) {
			System.out.println("There is no file with the name "+fileName);
		}else {
			try {
				String[] message=consortium.importOrders(file);
				System.out.println(message[0]);
				char answer;
				if(message[1]!="") {
					do {
						System.out.println("Do you want to see the orders that were not imported? Y/N");
						answer=read.nextLine().toUpperCase().charAt(0);
					}while(answer!='Y' && answer!='N');
					if(answer=='Y') {
						System.out.print(message[1]);
					}
				}
				consortium.saveData("order");
			}catch(IOException io) {
				System.out.println(io.getMessage());
			}
		}
	}
	
	/**
	 * It imports data of clients from a file cvs
	 */
	public void importClients() {
		System.out.print("Enter the name of the file without \".cvs\": ");
		String fileName=read.nextLine();
		File file = new File(RestaurantsAsociation.DATA_PATH_FILE+fileName+".cvs");
		if(!file.exists()) {
			System.out.println("There is no file with the name "+fileName);
		}else {
			try {
				String[] message=consortium.importClients(file);
				System.out.println(message[0]);
				char answer;
				if(message[1]!="") {
					do {
						System.out.println("Do you want to see the clients that were not imported? Y/N");
						answer=read.nextLine().toUpperCase().charAt(0);
					}while(answer!='Y' && answer!='N');
					if(answer=='Y') {
						System.out.print(message[1]);
					}
				}
				consortium.saveData("client");
			}catch(IOException io) {
				System.out.println(io.getMessage());
			}
		}
	}
}
