package exceptions;

public class NotFoundRestaurantException extends Exception{
	private String nit;
	public NotFoundRestaurantException(String nit) {
		super("There not exist a restaurant with the NIT: "+nit);
		this.nit=nit;
	}
	
	public String getNIT() {
		return nit;
	}
}
