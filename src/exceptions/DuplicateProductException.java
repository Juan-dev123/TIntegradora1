package exceptions;

public class DuplicateProductException extends Exception{
	private String id;
	public DuplicateProductException(String id) {
		super("There is already exists a product with the id: "+id);
		this.id=id;
	}
	public String getId() {
		return id;
	}
}
