package com.toDoListApp.service;

import java.util.List;
import java.util.Optional;

import com.toDoListApp.model.Role;

public interface RoleService {
   Optional<Role> findById(Long id);
   
   List<Role> findAll();
}
