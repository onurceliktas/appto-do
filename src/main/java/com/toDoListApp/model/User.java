package com.toDoListApp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import com.toDoListApp.etc.Status;

/**
 * Created by onurceliktas on 17.03.2020
 */

@Entity
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Integer status;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Collection<UserRole> userRoleCollection;
    
    @Transient
    private String passwordConfirm;
    
    @Transient
    private String viewStatus;
    
    @Transient
    private Role role;
    
    public static String adminRoleName = "admin";
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Collection<UserRole> getUserRoleCollection() {
		return userRoleCollection;
	}

	public void setUserRoleCollection(Collection<UserRole> userRoleCollection) {
		this.userRoleCollection = userRoleCollection;
	}
    
    public List<Role> getRoles(){
    	List<Role> roles = new ArrayList<Role>();
    	if (this.userRoleCollection != null) {
			for(UserRole userRole : new ArrayList<UserRole>(this.userRoleCollection)) {
				roles.add(userRole.getRole());
			}
		}
    	return roles;
    }

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getViewStatus() {
		if (this.status != null) {
			this.viewStatus = Status.getNameFromVal(this.status);
		}
		return viewStatus;
	}

	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}

    public boolean isNew() {
    	if (this.id != null) {
			return false;
		}else {
			return true;
		}
    }
    
    public boolean isAdmin() {
    	if (this.getRole() != null) {
    		if (this.getRole().getName().contentEquals(adminRoleName)) {
    			return true;
    		}
		}
    	return false;
    }

	public Role getRole() {
		if (this.getRoles() != null && !this.getRoles().isEmpty()) {
			role = this.getRoles().get(0);
		}
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
    
    

}
