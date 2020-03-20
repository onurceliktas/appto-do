package com.toDoListApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toDoListApp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
    User findByUsername(String username);
    
    User findByUsernameAndStatus(String username, int status);
    
    List<User> findAll();
    
    Optional<User> findById(Long id);
    
    void delete(User user);
    
}
