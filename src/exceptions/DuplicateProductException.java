package exceptions;

public class DuplicateProductException extends Exception{
	
	private static final long serialVersionUID = 1;
	private String id;
	public DuplicateProductException(String id) {
		super("The product was not registered.\nThere is already exists a product with the id: "+id);
		this.id=id;
	}
	public String getId() {
		return id;
	}
}
