package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import exceptions.DuplicateProductException;
import exceptions.NotFoundRestaurantException;

public class RestaurantsAsociation {
	
	public static final String DATA_PATH_FILE = "data/";
	
	private ArrayList<Restaurant> restaurants;
	private ArrayList<Client> clients;
	private ArrayList<Product> products;
	private ArrayList<Order> orders;
	
	/**
	 * It creates a new object type RestaurantsAsociation
	 * @throws IOException If there is a problem reading the file
	 * @throws ClassNotFoundException If there is a problem converting the data of the file to the objects.
	 */
	public RestaurantsAsociation() throws IOException, ClassNotFoundException{
		restaurants = new ArrayList<Restaurant>();
		clients = new ArrayList<Client>();
		products = new ArrayList<Product>();
		orders=new ArrayList<Order>(); 
		loadData();
	}
	
	/**
	 * It registers a restaurant
	 * @param name The name
	 * @param nit The NIT
	 * @param nameAdmin The name of the administrator
	 * @return True if the restaurant was registered. False if it was not
	 */
	public boolean registerRestaurant(String name, String nit, String nameAdmin){
		boolean registered;
		if(findRestaurant(nit)==null) {
			restaurants.add(new Restaurant(name,nit,nameAdmin));
			registered=true;
		}else {
			registered=false;
		}
		return registered;
	}
	
	/**
	 * It registers a product
	 * @param id The id
	 * @param name The name
	 * @param description The description
	 * @param price The price
	 * @param nit The NIT of the restaurant that offers this product
	 * @throws NotFoundRestaurantException If there is no restaurant with the NIT entered
	 * @throws DuplicateProductException If there is a product already registered with the id entered
	 */
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

	/**
	 * It registers a client
	 * @param typeId The type of the id
	 * @param id The id
	 * @param name The name
	 * @param lastName The last name
	 * @param phoneNumber The phone number
	 * @param address The address
	 * @return True if the client was registered successfully. False if it was not.
	 */
	public boolean registerClient(int typeId, String id, String name, String lastName,String phoneNumber, String address) {
		boolean registered = false; 
		if(findClient(id)==null) {
			addClient(typeId, id, name, lastName,phoneNumber, address);
			registered = true;
		}
		return registered;
	}
	
	/**
	 * It registers an order
	 * @param clientId The owner of the order
	 * @param nit The NIT of the restaurant who offers the products
	 * @param products The list of products
	 * @return A message informing what happened
	 */
	public String registerOrder(String clientId, String nit, ArrayList<String[]> products) {
		String message;
		if(products.isEmpty()) {
			message="No product was added. So, the order was not registered";
		}else if(checkProducts(nit, products)) {
			message="All the products do not belong to the same restaurant. So, the order was not registered";
		}else {
			Client client = findClient(clientId);
			Order order=new Order(clientId, nit, products);
			orders.add(order);
			client.addOrder(order);
			message="The order was registered successfully";
		}
		return message;
	}
	
	/**
	 * It updates the data of a restaurant
	 * @param nit The nit of the restaurant
	 * @param option The option
	 * @param data The data
	 */
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
	
	/**
	 * It updates the data of a product
	 * @param id The id of the product
	 * @param option The option
	 * @param data The data
	 */
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
	
	/**
	 * It updates the data of a client
	 * @param id The id of the client
	 * @param option The option
	 * @param data The data
	 */
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
			client.setName(data.toUpperCase());
			Collections.sort(clients);
			break;
		case 4:
			client.setLastName(data.toUpperCase());
			Collections.sort(clients);
			break;
		case 5:
			client.setPhoneNumber(data);
			break;
		case 6:
			client.setAddress(data);
			break;
		
		}
	}
	
	/**
	 * It updates the data of an order
	 * @param id The id of the order
	 * @param option The option
	 * @param data The data
	 */
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
	
	/**
	 * It updates the list of products of an order
	 * @param id The id of the order
	 * @param products The list of porducts
	 */
	public void updateDataOrder(String id, ArrayList<String[]> products) {
		Order order=findOrder(id);
		order.setProducts(products);
	}

	/**
	 * It removes an order
	 * @param id
	 * @return
	 */
	public String removeOrder(String id) {
		String message;
		Order order=findOrder(id);
		if(order==null) {
			message="There is no order with the id "+id;
		}else {
			Client client = findClient(order.getClientId());
			client.removeOrder(order.getId());
			orders.remove(orders.indexOf(order));
			
			message="The order was removed successfully";
		}
		return message;
	}
	
	/**
	 * It updates the quantity of a product in an order
	 * @param id The id of the product
	 * @param position The position of the product
	 * @param quantity The quantity
	 */
	public void updateQuantityProduct(String id, int position, String quantity) {
		Order order=findOrder(id);
		order.setQuantityProduct(position, quantity);
	}
	
	/**
	 * It exports all the orders in a file cvs
	 * @param file The file
	 * @param separator The separator
	 * @return A message informing what happened
	 * @throws FileNotFoundException If the file is not found
	 */
	public String exportOrders(File file, String separator) throws FileNotFoundException{
		String message;
			Collections.sort(orders);
			PrintWriter pw = new PrintWriter(file);
			pw.println("NIT"+separator+"Client id"+separator+"Date"+separator+"Product id");
			for(Order order:orders) {
				pw.println(order.getNit()+separator+order.getClientId()+separator+order.getDate().toString()+separator+order.getId());
			}
			pw.close();
			message="The orders was exported successfully";
		return message;
	}
	
	/**
	 * It searches a client by his name
	 * @param name The name
	 * @param lastName The last name
	 * @return If the client was found, so the client is returned. If it was not, so a message informing what happened is returned. In both cases the time spent in the search is returned
	 */
	public String searchClientByName(String name, String lastName){
		String fullName = lastName+" "+name;
		String message="There is no client with the name "+name+" "+lastName;
		int b = 0; //begin
		int f = clients.size()-1; //final
		boolean found = false;
		long begin = System.currentTimeMillis();
		while(b<=f && !found) {
			int m = (b+f)/2; //middle
			int comp = fullName.compareTo(clients.get(m).getFullName());
			if(comp==0) {
				found = true;
				message=clients.get(m).toString();
				found=true;
			}else if(comp>0) {
				f = m-1;
			}else {
				b = m+1;
			}
		}
		long end = System.currentTimeMillis();
		message += "\nThe time spent searching were "+String.valueOf(end-begin)+" milliseconds";
		return message;
	}
	
	/**
	 * It imports the data of restaurants from a file cvs
	 * @param file The file
	 * @return A message informing what happened
	 * @throws IOException If the file was not found
	 */
	public String[] importRestaurants(File file) throws IOException {
		String[] message = new String[2];
		String report="";
		BufferedReader br = new BufferedReader(new FileReader(file));
		br.readLine();
		String line = br.readLine();
		int i=0;
		while(line!=null) {
			String[] dataRestaurant=line.split(";");
			if(!registerRestaurant(dataRestaurant[0],dataRestaurant[1],dataRestaurant[2])) {
				i++;
				report+=i+(new Restaurant(dataRestaurant[0],dataRestaurant[1],dataRestaurant[2]).toString())+"\n";
			}
			line=br.readLine();
		}
		if(i==0) {
			message[0]="All the restaurants were imported successfully";
			message[1]=report;
		}else {
			message[0]="Some restaurants were not imported because they are already registered";
			message[1]=report;
		}
		br.close();
		return message;
	}
	
	/**
	 * It imports the data of products from a file cvs
	 * @param file The file
	 * @return A message informing what happened
	 * @throws IOException If the file was not found
	 */
	public String[] importProducts(File file) throws IOException{
		String[] message = new String[2];
		String report="";
		BufferedReader br = new BufferedReader(new FileReader(file));
		br.readLine();
		String line = br.readLine();
		int i=0;
		while(line!=null) {
			String[] dataProduct=line.split(";");
			if(!importProduct(dataProduct[0],dataProduct[1],dataProduct[2],dataProduct[3],dataProduct[4])) {
				i++;
				report+=i+(new Product(dataProduct[0],dataProduct[1],dataProduct[2],Double.parseDouble(dataProduct[3]),dataProduct[4]).toString())+"\n";
			}
			line=br.readLine();
		}
		if(i==0) {
			message[0]="All the products were imported successfully";
			message[1]=report;
		}else {
			message[0]="Some products were not imported because one or more of these reasons:\n1. The product was already registered \n2.There is no restaurant with the NIT that has the product";
			message[1]=report;
		}
		br.close();
		return message;
	}
	
	/**
	 * It creates a new product
	 * @param id The id
	 * @param name The name
	 * @param description The description of the product
	 * @param price The price
	 * @param nit The NIT that offers the product
	 * @return True if the product was created. False if it was not
	 */
	public boolean importProduct(String id, String name, String description, String price, String nit) {
		boolean registered=true;
		if(findRestaurant(nit)==null) {
			registered=false;
		}else if(findProduct(id)!=null) {
			registered=false;
		}else {
			Product product=new Product(id, name, description, Double.parseDouble(price), nit);
			products.add(product);
		}
		return registered;
		
	}
	
	/**
	 * It imports the data of orders from a file cvs
	 * @param file The file
	 * @return A message informing what happened
	 * @throws IOException If the file was not found
	 */
	public String[] importOrders(File file) throws IOException {
		String[] message = new String[2];
		String report="";
		BufferedReader br = new BufferedReader(new FileReader(file));
		br.readLine();
		String line = br.readLine();
		boolean error=false;
		while(line!=null) {
			String[] dataOrder=line.split(";");
			String[] d = dataOrder[1].split("/");
			int month = Integer.parseInt(d[0])-1;
			int day = Integer.parseInt(d[1]);
			int year = Integer.parseInt(d[2]);
			Calendar cal = Calendar.getInstance();
			cal.set(year, month, day);
			Date date = cal.getTime();
			int state=Integer.valueOf(dataOrder[2]);
			ArrayList<String[]> products=new ArrayList<String[]>();
			if(dataOrder.length>=6) {
				for(int j=5; j<dataOrder.length-2; j+=2) {
					String[] product = new String[2];
					product[0]=dataOrder[j];
					product[1]=dataOrder[j+1];
					products.add(product);
				}
			}
			if(!importOrder(dataOrder[0], date, state, dataOrder[3], dataOrder[4], products)) {
				error=true;
				report+=(new Order(dataOrder[0], date, state, dataOrder[3], dataOrder[4], products).toString())+"\n";
			}
			line=br.readLine();
		}
		if(!error) {
			message[0]="All the orders were imported successfully";
			message[1]=report;
		}else {
			message[0]="Some orders were not imported beacuse one or more of these reasons: \n1. The order was already registered \n2. There is no restaurant with the NIT that has the order \n3. The product list is empty \n4. All the products do not belong to the same restaurant \n5. There is a product in the list that it is not registered \n6. The client, owner of the order, do not exist";
			message[1]=report;
		}
		br.close();
		return message;
	}
	
	/**
	 * It creates an order
	 * @param id The id
	 * @param date The date
	 * @param status The status
	 * @param clientId The id of the client, owner of the order
	 * @param nit The NIT of the restaurant that offers this product
	 * @param products The list of products
	 * @return True if the order was registered. False if it was not
	 */
	public boolean importOrder(String id, Date date, int status, String clientId, String nit, ArrayList<String[]> products) {
		boolean inserted=true;;
		if(findRestaurant(nit)==null) {
			inserted=false;
		}else if(products.isEmpty()) {
			inserted=false;
		}else if(checkProducts(nit, products)) {
			inserted=false;
		}else if(findClient(clientId)==null){
			inserted=false;
		}else if(findOrder(id)!=null){
			inserted=false;
		}else {
			Client client = findClient(clientId);
			Order order=new Order(id, date, status,clientId, nit, products);
			orders.add(order);
			client.addOrder(order);	
		}
		return inserted;
	}
	
	/**
	 * It imports the data of clients from a file cvs
	 * @param file The file
	 * @return A message informing what happened
	 * @throws IOException If the file was not found
	 */
	public String[] importClients(File file) throws IOException {
		String[] message = new String[2];
		String report="";
		BufferedReader br = new BufferedReader(new FileReader(file));
		br.readLine();
		String line = br.readLine();
		
		int i=0;
		while(line!=null) {
			String[] dataClient=line.split(";");
			int typeId = Integer.parseInt(dataClient[0])-1;
			if(!registerClient(typeId,dataClient[1],dataClient[2],dataClient[3],dataClient[4],dataClient[5])) {
				i++;
				report+=i+(new Client(typeId,dataClient[1],dataClient[2],dataClient[3],dataClient[4],dataClient[5]).toString())+"\n";
			}
			line=br.readLine();
		}
		if(i==0) {
			message[0]="All the clients were imported successfully";
			message[1]=report;
		}else {
			message[0]="Some clients were not imported because they was already registered";
			message[1]=report;
		}
		br.close();
		return message;
	}
	
	/**
	 * @return The products
	 */
	public ArrayList<Product> getProducts() {
		return products;
	}

	/**
	 * @return The orders
	 */
	public ArrayList<Order> getOrders() {
		return orders;
	}

	/**
	 * It returns the restaurants ordered by the name ascending
	 * @return The restaurants
	 */
	public String showRestaurants() {
		String message="";
		Collections.sort(restaurants);
		int i=0;
		for(Restaurant restaurant:restaurants) {
			i++;
			message+=i+" "+restaurant.toString()+"\n";
		}
		return message;
	}
	
	/**
	 * It returns the clients ordered by the phone number descending
	 * @return The clients
	 */
	public String showClients() {
		String message="";
		PhoneNumberComparator pnc = new PhoneNumberComparator();
		ArrayList<Client> clientsOrdererByPhone = new ArrayList<Client>(clients);
		Collections.sort(clientsOrdererByPhone, pnc);
		int i=0;
		for(Client client:clientsOrdererByPhone) {
			i++;
			message+=i+" "+client.toString()+"\n";
		}
		return message;
	}
	
	/**
	 * It looks for a restaurant
	 * @param nit The NIT of the restaurant
	 * @return If it finds the restaurant, so return the restaurant. If it does not, so return null
	 */
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
	
	/**
	 * It looks for a product
	 * @param id The id of the product
	 * @return If it finds the product, so return the product. If it does not, so return null
	 */
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
	
	/**
	 * It looks for a client
	 * @param id The id of the client
	 * @return If it finds the client, so return the client. If it does not, so return null
	 */
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
	
	/**
	 * It looks for an order
	 * @param id The id of the order
	 * @return If it finds the order, so return the order. If it does not, so return null
	 */
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
	
	/**
	 * It adds a client in the correct position
	 * @param typeId The type of id
	 * @param id The id
	 * @param name The name
	 * @param lastName The last name
	 * @param phoneNumber The pohone number
	 * @param address The addres
	 */
	public void addClient(int typeId, String id, String name, String lastName, String phoneNumber, String address) {
		Client client = new Client(typeId, id, name, lastName,phoneNumber, address);
		if(clients.isEmpty()) {
			clients.add(client);
		}else {
			boolean inserted=false;
			for(int i=0; i<clients.size() && !inserted; i++) {
				if(clients.get(i).compareTo(client)>0) {
					clients.add(i, client);
					inserted=true;
				}
			}
			if(!inserted) {
				clients.add(client);
			}
		}
		
	}
	
	/**
	 * It checks that all the prodcuts of an order belong to the same restaurant
	 * @param nit The NIT of the restaurant
	 * @param products The list of products
	 * @return True if all the products belong to the same restaurant. False if they do not
	 */
	public boolean checkProducts(String nit, ArrayList<String[]> products) {
		boolean allProductsBelong=true;;
		for(String[] product : products) {
			if(!(product[0].equals(nit))) {
				allProductsBelong=false;
			}
		}
		return allProductsBelong;
	}
	
	/**
	 * It saves the data of the restaurants, clients, products and order in serializable files
	 * @param typeObject The type of object: restaurant, client, product, order
	 * @throws FileNotFoundException If the file was not found
	 * @throws IOException If there was a problem creating the file
	 */
	public void saveData(String typeObject) throws FileNotFoundException, IOException{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_PATH_FILE+typeObject+".mor"));
		switch(typeObject) {
		case "restaurant":
			oos.writeObject(restaurants);
			break;
		case "client":
			oos.writeObject(clients);
			break;
		case "product":
			oos.writeObject(products);
			break;
		case "order":
			oos.writeObject(orders);
			ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(DATA_PATH_FILE+"client"+".mor"));
			oos1.writeObject(clients);
			oos1.close();
			break;
		}
		oos.close();
	}
	
	/**
	 * It loads data of restaurants, clients, products and orders from serializable files
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void loadData() throws IOException, ClassNotFoundException{
		File fileRestaurant = new File(DATA_PATH_FILE+"restaurant.mor");
		if(fileRestaurant.exists()) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileRestaurant));
			restaurants=(ArrayList<Restaurant>)ois.readObject();
			ois.close();
		}
		File fileClient = new File(DATA_PATH_FILE+"client.mor");
		if(fileClient.exists()) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileClient));
			clients=(ArrayList<Client>)ois.readObject();
			ois.close();
		}
		File fileProduct = new File(DATA_PATH_FILE+"product.mor");
		if(fileProduct.exists()) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileProduct));
			products=(ArrayList<Product>)ois.readObject();
			ois.close();
		}
		File fileOrder = new File(DATA_PATH_FILE+"order.mor");
		if(fileOrder.exists()) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileOrder));
			orders=(ArrayList<Order>)ois.readObject();
			ois.close();
		}
		
	}
	
	/**
	 * It sorts the list of restaurants by the NIT
	 */
	public void sortRestaurantByNit() {
		//Bubble sort
		for(int i=0; i<restaurants.size()-i; i++) {
			for(int j=0; j<restaurants.size()-1-i; j++) {
				String nit1 = restaurants.get(j).getNit();
				String nit2 = restaurants.get(j+1).getNit();
				if(nit1.compareTo(nit2)>0) {
					String temp = nit2;
					nit2 = nit1;
					nit1 = temp;
				}
			}
		}
	}
	
	/**
	 * It sorts the list of products by the id
	 */
	public void sortProductsById() {
		//Insertion sort
		for(int i=1; i<products.size(); i++) {
			
			for(int j=i; j<0 && products.get(j-1).getId().compareTo(products.get(j).getId())>0; j--) {
				String temp = products.get(j).getId();
				products.get(j).setNit(products.get(j-1).getId());
				products.get(j-1).setNit(temp);
			}
		}
	}
	
	/**
	 * @return The list of restaurants
	 */
	public ArrayList<Restaurant> getRestaurants() {
		return restaurants;
	}

	/**
	 * @return The list of clients
	 */
	public ArrayList<Client> getClients() {
		return clients;
	}
}
