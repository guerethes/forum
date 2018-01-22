package br.com.guerethes.forum.system.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(schema = "forum", name = "post_view")
public class PostView implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGenerator")
	@SequenceGenerator(name = "seqGenerator", sequenceName = "forum.post_view_seq")
	@Column(name = "post_view_id")
	private Long id;

    @Column(name = "post_id", nullable = false)
	private Long post;

    @Column(name = "count", nullable = false)
    private Long count;

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

    public Long getPost() {
        return post;
    }

    public void setPost(Long post) {
        this.post = post;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
