package formation.afpa.fr.exception;

public class CommentAlreadyExistsException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommentAlreadyExistsException() {
		super("Comment already exists");
	}
	
}
