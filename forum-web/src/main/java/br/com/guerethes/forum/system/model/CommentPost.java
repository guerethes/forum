package br.com.guerethes.forum.system.model;

import br.com.guerethes.forum.architecture.model.User;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(schema = "forum", name = "comment_post")
public class CommentPost implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGenerator")
    @SequenceGenerator(name = "seqGenerator", sequenceName = "forum.comment_post_seq")
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="post_id")
    private Post post;

    @Column(name = "description", columnDefinition = "text")
    @NotEmpty(message = "*Please provide your post")
    private String description;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

    @Transient
    private String createdTemp;

    @Override
    public boolean isNew() {
        return false;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedTemp() {
        return createdTemp;
    }

    public void setCreatedTemp(String createdTemp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        try {
            setCreated(new java.sql.Date(format.parse(createdTemp).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.createdTemp = createdTemp;
    }
}
