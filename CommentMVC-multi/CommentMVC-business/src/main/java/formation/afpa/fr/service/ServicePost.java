package formation.afpa.fr.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import formation.afpa.fr.entity.Post;
import formation.afpa.fr.exception.PostAlreadyExistsException;
import formation.afpa.fr.exception.PostNotFoundException;
import formation.afpa.fr.exception.PostNotValidException;
import formation.afpa.fr.interfaceService.IPostService;
import formation.afpa.fr.repository.PostRepository;

@Service
public class ServicePost implements IPostService {

	@Autowired
	PostRepository repo;

	
	@Override
	public List<Post> list() throws PostNotFoundException {
		List<Post> findAll = (List<Post>) repo.findAll();

		if ((findAll == null) || (findAll.size() == 0)) {
			throw new PostNotFoundException();
		}

		return findAll;
	}

	@Override
	public Post findById(Long id) throws PostNotFoundException {
		if (id == null) {
			throw new PostNotFoundException();
		}

		Post post = repo.findById(id).get();

		if (post == null) {
			throw new PostNotFoundException();
		}

		return post;
	}

	@Override
	public Post create(Post post) throws PostNotValidException, PostAlreadyExistsException {
		if (post == null) {
			throw new PostNotValidException();
		}

		if (post.getId() != null) {
			Optional<Post> postOptional = repo.findById(post.getId());

			if (postOptional.isPresent()) {
				throw new PostAlreadyExistsException();
			} else {
				throw new PostNotValidException();
			}
		}

		return repo.save(post);
		
	}

	@Override
	public Post update(Post post) throws PostNotFoundException, PostNotValidException {
		if ((post == null) || (post.getId() == null)) {
			throw new PostNotValidException();
		}

		Long idAChercher = post.getId();
		Optional<Post> PostOptional = repo.findById(idAChercher);

		if (!PostOptional.isPresent()) {
			throw new PostNotFoundException();
		}
		return repo.save(post);
	}

	@Override
	public void deleteById(Long id) throws PostNotFoundException {
		if (id == null) {
			throw new PostNotFoundException();
		}

		Optional<Post> PostOptional = repo.findById(id);
		if (!PostOptional.isPresent()) {
			throw new PostNotFoundException("Post with requested id does not exist");
		}

		repo.deleteById(id);
	}
	
	
	
}
