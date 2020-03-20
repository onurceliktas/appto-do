package com.toDoListApp.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.toDoListApp.model.Task;
import com.toDoListApp.model.User;

public interface TaskService {
    void save(Task task);

    
    List<Task> findAll();
    
    Optional<Task> findById(Long id);
    
    void delete(Task task);
    
    @Query("SELECT t FROM Task t WHERE t.owner = ?1")
    List<Task> findByOwner(User user);
    
//    @Query("select t from Task t where t.enddate <= :endDate and t.startdate => :startDate")
//    List<Task> findTasksBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    List<Task> findAllByStartdateGreaterThanEqualAndEnddateLessThanEqual(Date startDate, Date endDate);
    
    @Query("select t from Task t where t.startdate >= :startDate and t.enddate <= :endDate")
    List<Task> findTasksBetween(@Param("startDate") Date startDate, 
    						 	@Param("endDate") Date endDate);
    
    @Query("select t from Task t where t.startdate >= :startDate and t.enddate <= :endDate and t.owner = :owner")
    List<Task> findTasksByDateAndOwner(@Param("startDate") Date startDate, 	
									   @Param("endDate") Date endDate,
									   @Param("owner") User owner);
}
