package br.com.guerethes.forum.architecture.service;

import br.com.guerethes.forum.architecture.model.User;
import br.com.guerethes.forum.architecture.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends CrudServiceImpl<User, Long> implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
