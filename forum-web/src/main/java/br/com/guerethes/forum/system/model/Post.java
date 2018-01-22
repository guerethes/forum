package br.com.guerethes.forum.system.model;

import br.com.guerethes.forum.architecture.model.User;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "forum", name = "post")
public class Post implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGenerator")
	@SequenceGenerator(name = "seqGenerator", sequenceName = "forum.post_seq")
	@Column(name = "post_id")
	private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "post", columnDefinition = "text")
	@NotEmpty(message = "*Please provide your post")
	private String post;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="post_thread_id")
	private Post thread;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

    @Transient
    private List<Post> postThreadList;

    @Transient
    private Long view;

    @Transient
    private Long answer;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getThread() {
        return thread;
    }

    public void setThread(Post thread) {
        this.thread = thread;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Post> getPostThreadList() {
        return postThreadList;
    }

    public void setPostThreadList(List<Post> postThreadList) {
        this.postThreadList = postThreadList;
    }

    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

    public Long getAnswer() {
        return answer;
    }

    public void setAnswer(Long answer) {
        this.answer = answer;
    }
}
