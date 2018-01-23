package br.com.guerethes.forum.architecture.service;

import br.com.guerethes.forum.architecture.model.User;

public interface UserService extends CrudService<User, Long>{

	User findUserByEmail(String email);


}