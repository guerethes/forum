package br.com.guerethes.forum.system.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(schema = "forum", name = "category")
public class Category implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGenerator")
	@SequenceGenerator(name = "seqGenerator", sequenceName = "forum.category_seq")
	@Column(name = "category_id")
	private Long id;

    @Column(name = "title")
    private String title;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
