package formation.afpa.fr.exception;

public class PostNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PostNotFoundException() {
		super("Post is not found");
	}
	
	public PostNotFoundException(String message) {
		super(message);
	}

}
