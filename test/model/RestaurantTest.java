package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RestaurantTest {
	
	private Restaurant restaurant;

	public void setupScenary1() {
		
	}
	
	public void setupScenary2() {
		restaurant = new Restaurant("La locura", "9087654321", "Juan Pablo Ramos");
	}
	
	@Test
	void testRestaurant() {
		setupScenary1();
		restaurant = new Restaurant ("La locura", "1234567891", "Juan Pablo Ramos");
		assertNotNull(restaurant);
		assertEquals("La locura", restaurant.getName());
		assertEquals("1234567891", restaurant.getNit());
		assertEquals("Juan Pablo Ramos", restaurant.getNameAdmin());
		
		setupScenary2();
		Restaurant restaurant1 = new Restaurant("Las ricuras del Valle", "1234567890", "Micaela Gutierrez");
		Restaurant restaurant2 = new Restaurant("El chuletazo", "1111122222", "Ahsoka Tano");
		int comp=restaurant.compareTo(restaurant1);
		assertTrue(comp<0);
		comp=restaurant.compareTo(restaurant2);
		assertTrue(comp>0);
		
		
		
	}

}
