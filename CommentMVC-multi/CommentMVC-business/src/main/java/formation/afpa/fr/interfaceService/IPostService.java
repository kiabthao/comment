package formation.afpa.fr.interfaceService;

import java.util.List;

import formation.afpa.fr.entity.Post;
import formation.afpa.fr.exception.PostAlreadyExistsException;
import formation.afpa.fr.exception.PostNotFoundException;
import formation.afpa.fr.exception.PostNotValidException;

public interface IPostService {
	
	public List<Post> list() throws PostNotFoundException;
	
	public Post findById(Long id) throws PostNotFoundException;
	
	public Post create(Post post) throws PostNotValidException, PostAlreadyExistsException;
	
	public Post update(Post post) throws PostNotFoundException, PostNotValidException;
	
	public void deleteById(Long id) throws PostNotFoundException;


}
