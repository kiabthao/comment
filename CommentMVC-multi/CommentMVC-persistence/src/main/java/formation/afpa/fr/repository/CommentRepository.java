package formation.afpa.fr.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import formation.afpa.fr.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

	@Query("SELECT c FROM Comment c WHERE c.post.id = :myId")
	List<Comment> findCommentsByPostId(@Param("myId") Long id);
	
}
