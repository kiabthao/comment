package formation.afpa.fr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import formation.afpa.fr.entity.Comment;
import formation.afpa.fr.entity.Post;
import formation.afpa.fr.exception.CommentAlreadyExistsException;
import formation.afpa.fr.exception.CommentNotValidException;
import formation.afpa.fr.exception.PostNotFoundException;
import formation.afpa.fr.service.ServiceComment;
import formation.afpa.fr.service.ServicePost;

@Controller
public class CommentController {

	@Autowired
	private ServiceComment serviceComment;
	
	@Autowired
	private ServicePost servicePost;

	@GetMapping("/add/{id}/comment")
	public String addComment(@PathVariable(name="id") Long id, Model model) throws PostNotFoundException, CommentNotValidException, CommentAlreadyExistsException {
		Post post = servicePost.findById(id);
		model.addAttribute("mycomment", new Comment());
		model.addAttribute("post", post);
		return "addcomment";
	}

	@PostMapping("/add/{id}/comment")
	public String addNewComment (@PathVariable(name="id") Long id, Comment comment) throws Exception {
			Post post = servicePost.findById(id);
			
			
			comment.setPost(post);
			serviceComment.create(comment);
			
		return "redirect:/";
	}

}