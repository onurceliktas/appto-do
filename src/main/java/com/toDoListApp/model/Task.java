package com.toDoListApp.model;

import java.util.Date;

import javax.persistence.*;

import com.toDoListApp.etc.TaskStatus;

/**
 * Created by onurceliktas on 17.03.2020
 */

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "descr")
    private String descr;
    
    @Column(name = "startdate")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    
    @Column(name = "enddate")
    @Temporal(TemporalType.DATE)
    private Date enddate;
    
    @Column(name = "taskstatus")
    private Integer taskstatus;

    @JoinColumn(name = "owner", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;
    
    @Column(name = "status")
    private Integer status;
    
    @Transient
    private String viewTaskStatus;
    
    @Transient
    private boolean editable;
    
    @Transient
    private String shortDescr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Integer getTaskstatus() {
		return taskstatus;
	}

	public void setTaskstatus(Integer taskstatus) {
		this.taskstatus = taskstatus;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

  public boolean isNew() {
    	if (this.id != null) {
			return false;
		}else {
			return true;
		}
    }

	public String getViewTaskStatus() {
		if (this.taskstatus != null) {
			this.viewTaskStatus = TaskStatus.getNameFromVal(this.taskstatus);
		}
		return viewTaskStatus;
	}
	
	public void setViewTaskStatus(String viewTaskStatus) {
		this.viewTaskStatus = viewTaskStatus;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getShortDescr() {
		if (this.descr != null) {
			if (descr.length()>25) {
				this.shortDescr = this.descr.substring(0,50)+"....";
			}else {
				this.shortDescr = descr;
			}
			
		}
		return shortDescr;
	}

	public void setShortDescr(String shortDescr) {
		this.shortDescr = shortDescr;
	}
  
  
    
    

}