package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class RestaurantsAsociationTest {
	
	public void setupScenary1(){
		
	}

	@Test
	void testRestaurantsAsociation() {
		setupScenary1();
		try {
			RestaurantsAsociation consortium=new RestaurantsAsociation();
			assertNotNull(consortium);
			assertTrue(consortium.getClients().isEmpty());
			assertTrue(consortium.getRestaurants().isEmpty());
			assertTrue(consortium.getProducts().isEmpty());
			assertTrue(consortium.getOrders().isEmpty());
		}catch(IOException io) {
			fail();
		}catch(ClassNotFoundException cnf) {
			fail();
		}
		
		
	}

}
