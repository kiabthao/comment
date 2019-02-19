package formation.afpa.fr.repository;

import org.springframework.data.repository.CrudRepository;

import formation.afpa.fr.entity.Post;

public interface PostRepository extends CrudRepository<Post, Long> {


}
