package formation.afpa.fr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import formation.afpa.fr.entity.Comment;
import formation.afpa.fr.entity.Post;
import formation.afpa.fr.exception.CommentAlreadyExistsException;
import formation.afpa.fr.exception.CommentNotFoundException;
import formation.afpa.fr.exception.CommentNotValidException;
import formation.afpa.fr.interfaceService.ICommentService;
import formation.afpa.fr.repository.CommentRepository;
import formation.afpa.fr.repository.PostRepository;

@Service
public class ServiceComment implements ICommentService {

	@Autowired
	CommentRepository repo;
	
	@Autowired
	PostRepository pRepo;
	
	@Override
	public List<Comment> list() throws CommentNotFoundException {
		List<Comment> findAll = (List<Comment>) repo.findAll();

		if ((findAll == null) || (findAll.size() == 0)) {
			throw new CommentNotFoundException();
		}

		return findAll;
	}

	@Override
	public Comment findById(Long id) throws CommentNotFoundException {
		if (id == null) {
			throw new CommentNotFoundException();
		}

		Comment comm = repo.findById(id).get();

		if (comm == null) {
			throw new CommentNotFoundException();
		}

		return comm;
	}

	@Override
	public Comment create(Comment comm) throws CommentNotValidException, CommentAlreadyExistsException {
		if (comm == null) {
			throw new CommentNotValidException("comment is null");
		}

		if (comm.getId() != null) {
			Optional<Comment> commOptional = repo.findById(comm.getId());

			if (commOptional.isPresent()) {
				throw new CommentAlreadyExistsException();
			} else {
				throw new CommentNotValidException("the id of the comment is not null");
			}
		}

		return repo.save(comm);
		
	}


	@Override
	public Comment update(Comment comm) throws CommentNotFoundException, CommentNotValidException {
		if ((comm == null) || (comm.getId() == null)) {
			throw new CommentNotValidException();
		}

		Long idAChercher = comm.getId();
		Optional<Comment> CommentOptional = repo.findById(idAChercher);

		if (!CommentOptional.isPresent()) {
			throw new CommentNotFoundException();
		}
		return repo.save(comm);
	}


	@Override
	public void deleteById(Long id) throws CommentNotFoundException {
		if (id == null) {
			throw new CommentNotFoundException();
		}

		Optional<Comment> commentOptional = repo.findById(id);
		if (!commentOptional.isPresent()) {
			throw new CommentNotFoundException("Comment with requested id does not exist");
		}

		repo.deleteById(id);
	}
	
	public List<Comment> findCommentsByPostId(Long id){
		List<Comment> commList = repo.findCommentsByPostId(id);
		
		return commList;
	}

	public List<Comment> findCommentsByPostId2(Long id){
		List<Comment> commList = new ArrayList<>();
		Post p = pRepo.findById(id).get();
		
		commList = (List<Comment>) p.getComments();
		
		return commList;
		
	}
}
