package com.toDoListApp.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toDoListApp.model.Task;
import com.toDoListApp.model.User;
import com.toDoListApp.repository.TaskRepository;


@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

	@Override
	public void save(Task task) {
		this.taskRepository.save(task);
	}

	@Override
	public List<Task> findAll() {
		return this.taskRepository.findAll();
	}

	@Override
	public Optional<Task> findById(Long id) {
		return this.taskRepository.findById(id);
	}

	@Override
	public void delete(Task task) {
		this.taskRepository.delete(task);
		
	}

	@Override
	public List<Task> findByOwner(User user) {
		return this.taskRepository.findByOwner(user);
	}

	@Override
	public List<Task> findAllByStartdateGreaterThanEqualAndEnddateLessThanEqual(Date startDate, Date endDate) {
		return this.taskRepository.findAllByStartdateGreaterThanEqualAndEnddateLessThanEqual(startDate, endDate);
	}

	@Override
	public List<Task> findTasksBetween(Date startDate, Date endDate) {
		return this.taskRepository.findTasksBetween(startDate, endDate);
	}

	@Override
	public List<Task> findTasksByDateAndOwner(Date startDate, Date endDate, User owner) {
		return this.taskRepository.findTasksByDateAndOwner(startDate, endDate, owner);
	}


    
}
