package com.toDoListApp.service;

import java.util.List;

import com.toDoListApp.model.User;
import com.toDoListApp.model.UserRole;

public interface UserRoleService {
    void save(UserRole userRole);
    
    void delete(UserRole userRole);
    
    List<UserRole> findByUser(User user);
}
