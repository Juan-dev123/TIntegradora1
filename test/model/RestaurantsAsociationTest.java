package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class RestaurantsAsociationTest {
	
	private RestaurantsAsociation consortium;
	
	public void setupScenary1() throws IOException, ClassNotFoundException{
		consortium=new RestaurantsAsociation();
		consortium.getRestaurants().add(new Restaurant("Las ricuras del Valle", "1234567890", "Micaela Gutierrez"));
		consortium.getRestaurants().add(new Restaurant("La locura", "9087654321", "Juan Pablo Ramos"));
		consortium.getRestaurants().add(new Restaurant("El chuletazo", "1111122222", "Ahsoka Tano"));
	}

	@Test
	void testRestaurantsAsociation() {
		
		try {
			setupScenary1();
			consortium.updateDataRestaurant("1234567890", 1, "La sopaza");
			Restaurant restaurant = consortium.findRestaurant("1234567890");
			assertEquals("La sopaza", restaurant.getName());
			assertEquals("1234567890", restaurant.getNit());
			assertEquals("Micaela Gutierrez", restaurant.getNameAdmin());
		}catch(IOException io) {
			fail();
		}catch(ClassNotFoundException cnf) {
			fail();
		}
		
		
		try {
			setupScenary1();
			Restaurant restaurant = consortium.findRestaurant("1234567890");
			assertEquals("Las ricuras del Valle", restaurant.getName(), restaurant.getName());
			assertEquals("1234567890", restaurant.getNit());
			assertEquals("Micaela Gutierrez", restaurant.getNameAdmin());
			assertNull(consortium.findRestaurant("1111111111"));
		}catch(IOException io) {
			fail();
		}catch(ClassNotFoundException cnf) {
			fail();
		}
	}

}
