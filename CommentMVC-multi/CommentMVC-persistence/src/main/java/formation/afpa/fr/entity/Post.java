package formation.afpa.fr.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@Column(name = "subject", nullable = false, length = 25)
	private String subject;

	@Column(name = "text", nullable = false)
	private String text;

	@OneToMany(mappedBy="post")
	private List<Comment> comments;

	public Post() {

	}

	public Post(String subject, String text, List<Comment> comments) {
		super();
		this.subject = subject;
		this.text = text;
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Long> getCommIds() {

		List<Comment> commSet = getComments();
		List<Long> ids = new ArrayList<>();

		if (commSet != null) {
			List<Comment> list = new ArrayList<Comment>(commSet);

			ids = list.stream().map(Comment::getId).collect(Collectors.toList());
		}

		return ids;

	}

	@Override
	public String toString() {
		return subject + text + comments;
	}

}
