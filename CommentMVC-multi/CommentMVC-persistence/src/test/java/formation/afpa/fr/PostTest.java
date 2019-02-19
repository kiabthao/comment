package formation.afpa.fr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import formation.afpa.fr.entity.Comment;
import formation.afpa.fr.entity.Post;
import formation.afpa.fr.repository.CommentRepository;
import formation.afpa.fr.repository.PostRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = SmallApp.class)
public class PostTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private CommentRepository commRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	public Long idLastComm = 0l;
	public Long idLastPost = 0l;
	
	public Long idPost2 = 0l;
	
	List<Comment> list = new ArrayList<>();
	
	@Before
	public void setUp() {
		initBDD();
	}
	
	
	public void initBDD() {
		Comment com1 = new Comment();
		com1.setMessage("Coucou");
		
		Comment com2 = new Comment();
		com2.setMessage("Tra-la");
		
		Comment com3 = new Comment();
		com3.setMessage("Test");
		
		entityManager.persist(com1);
		entityManager.persist(com2);
		
		idLastComm = (Long) entityManager.persistAndGetId(com3);
		
		
		Post p1 = new Post();
		p1.setSubject("Global warming");
		p1.setText("Modern reality");
			
		list.add(com1);
		list.add(com2);
		list.add(com3);
		
		p1.setComments(list);
		
		idLastPost = (Long) entityManager.persistAndGetId(p1);
		
		Post p2 = new Post();
		p2.setSubject("Test");
		p2.setText("TestTest");
		
		idPost2 = (Long) entityManager.persistAndGetId(p2);
			
	}
	
	
	@Test
	public void List() {
		try {
			List<Post> postList = (List<Post>) postRepo.findAll();
			assertNotNull(postList);
			assertEquals(postList.size(), 2);
		} catch (Exception e) {
			Assert.fail("The exception was not expected");
		}
	}
	
	@Test
	public void addComment() {
		Comment com1 = new Comment();
		com1.setMessage("Test msg");
		commRepo.save(com1);
		
		assertEquals(commRepo.count(), 4);
		
		list.add(com1);
		assertTrue(postRepo.findById(idLastPost).get().getComments().contains(com1));
		
		
	}
	
	@Test
	public void deleteComm() {
		list.remove(commRepo.findById(idLastComm).get());
		
		assertEquals(postRepo.findById(idLastPost).get().getComments().size(), 2);
	}
	
	@Test
	public void commid() {
		Post p1 = postRepo.findById(idLastPost).get();
		List<Long> ids = p1.getCommIds();
		assertNotNull(ids);
		assertTrue(ids.contains(idLastComm));
		
		Post p2 = postRepo.findById(idPost2).get();
		List<Long> ids2 = p2.getCommIds();
		assertFalse(ids2.contains(idLastComm));
	}
	
//	@Test
//	public void findCommentsByPostId() {
//		List<Comment> myList = commRepo.findCommentsByPostId(idLastPost);
//		assertNotNull(myList);
//		assertEquals(myList.size(), 3);
//		
//	}
//	
}
