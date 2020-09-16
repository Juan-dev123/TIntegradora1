package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RestaurantsAsociationTest {
	
	public void setupScenary1(){
		
	}

	@Test
	void testRestaurantsAsociation() {
		setupScenary1();
		RestaurantsAsociation consortium=new RestaurantsAsociation();
		assertNotNull(consortium);
		assertTrue(consortium.getClients().isEmpty());
		assertTrue(consortium.getRestaurants().isEmpty());
		
	}

}
