package br.com.guerethes.forum.architecture.repository;

import br.com.guerethes.forum.architecture.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	 User findByEmail(String email);

}