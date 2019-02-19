package formation.afpa.fr.interfaceService;

import java.util.List;

import formation.afpa.fr.entity.Comment;
import formation.afpa.fr.exception.CommentAlreadyExistsException;
import formation.afpa.fr.exception.CommentNotFoundException;
import formation.afpa.fr.exception.CommentNotValidException;

public interface ICommentService {
	
	public List<Comment> list() throws CommentNotFoundException;
	
	public Comment findById(Long id) throws CommentNotFoundException;
	
	public Comment create(Comment comm) throws CommentNotValidException, CommentAlreadyExistsException;
	
	public Comment update(Comment comm) throws CommentNotFoundException, CommentNotValidException;
	
	public void deleteById(Long id) throws CommentNotFoundException;

}
