package com.toDoListApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toDoListApp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Optional<Role> findById(Long id);
}
