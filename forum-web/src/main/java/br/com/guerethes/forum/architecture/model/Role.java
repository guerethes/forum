package br.com.guerethes.forum.architecture.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "role", schema = "forum")
public class Role implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGenerator")
	@SequenceGenerator(name = "seqGenerator", sequenceName = "forum.role_seq")
	@Column(name = "role_id")
	private Long id;

	@Column(name="role")
	private String role;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public boolean isNew() {
		return getId() == null;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}