package exceptions;

public class NotFoundRestaurantException extends Exception{

	private static final long serialVersionUID = 1;
	private String nit;
	public NotFoundRestaurantException(String nit) {
		super("The product was not registered.\nThere not exist a restaurant with the NIT: "+nit);
		this.nit=nit;
	}
	
	public String getNIT() {
		return nit;
	}
}
