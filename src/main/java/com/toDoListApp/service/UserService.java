package com.toDoListApp.service;

import java.util.List;
import java.util.Optional;

import com.toDoListApp.model.User;

public interface UserService {
    void save(User user);

    User findByUserName(String username);
    
    List<User> findAll();
    
    Optional<User> findById(Long id);
    
    void delete(User user);
    
}
