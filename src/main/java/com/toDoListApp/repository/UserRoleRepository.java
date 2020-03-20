package com.toDoListApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toDoListApp.model.User;
import com.toDoListApp.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	List<UserRole> findByUser(User user);
	
	void delete(UserRole userRole);
	
	
}
