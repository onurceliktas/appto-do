package com.toDoListApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toDoListApp.etc.Status;
import com.toDoListApp.model.User;
import com.toDoListApp.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
    	if (user.getStatus() != null) {
			// do nothing;
		}else {
			user.setStatus(Status.ACTIVE.intVal());
		}
		userRepository.save(user);
	}

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

	@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	@Override
	public Optional<User> findById(Long id) {
		return this.userRepository.findById(id);
	}

	@Override
	public void delete(User user) {
		this.userRepository.delete(user);
		
	}
}
