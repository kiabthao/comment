package formation.afpa.fr.exception;

public class CommentNotValidException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommentNotValidException () {
		super("Comment is invalid");

	}
	
	public CommentNotValidException (String message) {
		super(message);

	}
	
	
}
