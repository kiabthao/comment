package formation.afpa.fr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import formation.afpa.fr.entity.Comment;
import formation.afpa.fr.exception.CommentAlreadyExistsException;
import formation.afpa.fr.exception.CommentNotFoundException;
import formation.afpa.fr.exception.CommentNotValidException;
import formation.afpa.fr.repository.CommentRepository;
import formation.afpa.fr.service.ServiceComment;

@RunWith(MockitoJUnitRunner.class) // On indique qu’on utilise Mockito
public class TestServiceComment {

	@Mock // Ici on indique qu’on a un mock qui simule le repository
	private CommentRepository repoMock;
	
	@InjectMocks // Ici, on indique que le mock est injecté/utilisé par le service à tester
	private ServiceComment service;
	
	
	List<Comment> list = new ArrayList<>();
	Long id = 1l;
	int listSize;

	@Before
	public void setUp() {
		list.add(new Comment(0l, "test", null));
		list.add(new Comment(1l, "service", null));
		listSize = list.size();

		// used in several tests
		

		when(repoMock.findAll()).thenReturn(list);

	}

	@Test
	public void findAll() throws CommentNotFoundException {

		// test the list's size

		try {
			assertEquals(listSize, service.list().size());
		} catch (CommentNotFoundException e) {
			Assert.fail("Test failed : CommentNotAviableException was not expected");
		}

		// initialise the list to 0 and test that the exception is well thrown
		list = new ArrayList<Comment>();
		try {
			service.list();
		} catch (CommentNotFoundException e) {
			e.printStackTrace();
			assertTrue(true);
		}

	}

	
	@Test
	public void create() throws CommentNotValidException {
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object[] os = invocation.getArguments();
				if (os != null && os.length != 0 && os[0] != null) {
					Comment it = (Comment) os[0];
					it.setId(12L);
					list.add(it);
				}
				return null;
			}
		}).when(repoMock).save(Mockito.any(Comment.class));
		// refactored=>
		// when(repoMock.findById(id)).thenReturn(Optional.of(getCommentById(id)));

		Comment comm = new Comment();
		comm.setMessage("my Message");
		

		try {
			service.create(comm);
		} catch (CommentNotValidException e) {
			Assert.fail("Test failed : CommentNotValidException was not expected");
		} catch (CommentAlreadyExistsException e) {
			Assert.fail("Test failed : CommentAlreadyExistsException was not expected");
		}

		assertEquals(list.size(), listSize + 1);
		assertEquals(12L, list.get(2).getId().longValue());

		try {
			service.create(null);
		} catch (CommentNotValidException e) {
			assertTrue(true);
		} catch (CommentAlreadyExistsException e) {
			Assert.fail("Test failed : CommentAlreadyExistsException was not expected");
		}
	}

	
}
