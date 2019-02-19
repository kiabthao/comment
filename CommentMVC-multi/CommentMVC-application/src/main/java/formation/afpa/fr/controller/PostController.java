package formation.afpa.fr.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import formation.afpa.fr.entity.Comment;
import formation.afpa.fr.entity.Post;
import formation.afpa.fr.exception.CommentNotFoundException;
import formation.afpa.fr.exception.PostAlreadyExistsException;
import formation.afpa.fr.exception.PostNotFoundException;
import formation.afpa.fr.exception.PostNotValidException;
import formation.afpa.fr.service.ServiceComment;
import formation.afpa.fr.service.ServicePost;

@Controller
public class PostController {

	@Autowired
	private ServicePost servicePost;

	@Autowired
	private ServiceComment serviceComm;

	@GetMapping("/")
	public String postList(Model model) throws PostNotFoundException {
		List<Post> listPosts = servicePost.list();

		model.addAttribute("posts", listPosts);

		return "index";
	}

	@GetMapping("/add/post")
	public String addPost(Model model) {
		model.addAttribute("post", new Post());
		return "addpost";
	}

	@PostMapping("/add/post")
	public String addNewPost(Post post) {
		try {
			servicePost.create(post);
		} catch (PostNotValidException e) {
			e.printStackTrace();
		} catch (PostAlreadyExistsException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	
	@GetMapping("/post/{id}/comment")
	public String getComments(@PathVariable(name="id") Long id, Model model) throws PostNotFoundException {
		
		List<Comment> listComm = serviceComm.findCommentsByPostId(id);
		model.addAttribute("comments", listComm);
	
		return "comments";
	}
}