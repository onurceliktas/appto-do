package com.toDoListApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toDoListApp.model.User;
import com.toDoListApp.model.UserRole;
import com.toDoListApp.repository.UserRoleRepository;


@Service
public class UserRoleServiceImp implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;
    

	@Override
	public void save(UserRole userRole) {
		this.userRoleRepository.save(userRole);
		
	}


	@Override
	public void delete(UserRole userRole) {
		this.userRoleRepository.delete(userRole);
		
	}


	@Override
	public List<UserRole> findByUser(User user) {
		return this.userRoleRepository.findByUser(user);
	}



   
}
