package br.com.guerethes.forum.architecture.repository;

import br.com.guerethes.forum.architecture.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByRole(String role);

}