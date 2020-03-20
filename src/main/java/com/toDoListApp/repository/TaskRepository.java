package com.toDoListApp.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.toDoListApp.model.Task;
import com.toDoListApp.model.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findAll();
    
    Optional<Task> findById(Long id);
    
    void delete(Task task);
    
    @Query("SELECT t FROM Task t WHERE t.owner = ?1")
    List<Task> findByOwner(User user);
    
    @Query("select t from Task t where t.startdate >= ?1 and t.enddate <= ?2 ")
    List<Task> findTasksBetweenDate(Date endDate,Date startDate);
    
    @Query("select t from Task t where t.startdate >= :startDate and t.enddate <= :endDate")
    List<Task> findTasksBetween(@Param("startDate") Date startDate, 
    							@Param("endDate") Date endDate);
    
    @Query("select t from Task t where t.startdate >= :startDate and t.enddate <= :endDate and t.owner = :owner")
    List<Task> findTasksByDateAndOwner(@Param("startDate") Date startDate, 	
									   @Param("endDate") Date endDate,
									   @Param("owner") User owner);
    
    List<Task> findAllByStartdateGreaterThanEqualAndEnddateLessThanEqual(Date startDate, Date endDate);
    
}
