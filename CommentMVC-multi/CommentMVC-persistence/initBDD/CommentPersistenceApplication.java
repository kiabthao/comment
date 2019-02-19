package formation.afpa.fr;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import formation.afpa.fr.entity.Comment;
import formation.afpa.fr.entity.Post;
import formation.afpa.fr.repository.CommentRepository;
import formation.afpa.fr.repository.PostRepository;

@SpringBootApplication
public class CommentPersistenceApplication implements CommandLineRunner {

	private static Log log = LogFactory.getLog(CommentPersistenceApplication.class);

	@Autowired
	PostRepository postRep;
		
	@Autowired
	CommentRepository comRep;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CommentPersistenceApplication.class, args);
	}

	@Transactional
	public void run(String... args) throws Exception {
		
	//	initBDD();
		
		List<Comment> myList = comRep.findCommentsByPostId(2l);
		log.info(myList);
		
	}
	
	
	 private void initBDD() {
		 
	Post post1 = new Post();
	
	post1.setSubject("Global warming");
	post1.setText("Global warming is one of the most complex and sensitive issues facing us today. "
			+ "It is important to start talking about climate change in our personal and professional"
			+ " circles in order to bring it into mainstream conversations and contribute into actions.");
		 
	
	Comment com1 = new Comment();
	Comment com2 = new Comment();
	com1.setMessage("Completetely agree with you on the question."
			+ " Actually climate change is not a « normal » topic to bring up in conversations");
	com1.setPost(post1);
	comRep.save(com1);
	
	com2.setMessage("This is a modern reality");
	com2.setPost(post1);
	comRep.save(com2);
	
	Set<Comment> comments = new HashSet<>();
	comments.add(com1);
	comments.add(com2);
	
	post1.setComments(comments);
	postRep.save(post1);
	

	
	 }

}
