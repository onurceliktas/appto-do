package com.toDoListApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toDoListApp.model.Role;
import com.toDoListApp.repository.RoleRepository;


@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    
	@Override
	public Optional<Role> findById(Long id) {
		return this.roleRepository.findById(id);
	}

	@Override
	public List<Role> findAll() {
		return this.roleRepository.findAll();
	}
   
}
