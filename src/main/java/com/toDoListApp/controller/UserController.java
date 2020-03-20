package com.toDoListApp.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.toDoListApp.etc.Status;
import com.toDoListApp.etc.TaskStatus;
import com.toDoListApp.model.Role;
import com.toDoListApp.model.Task;
import com.toDoListApp.model.User;
import com.toDoListApp.model.UserRole;
import com.toDoListApp.service.RoleService;
import com.toDoListApp.security.SecurityService;
import com.toDoListApp.service.TaskService;
import com.toDoListApp.service.UserRoleService;
import com.toDoListApp.service.UserService;
import com.toDoListApp.util.ToDoUtil;
import com.toDoListApp.validator.UserValidator;

@Controller
public class UserController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final String FORMAT_DATE_TR_TIME = "dd.MM.yyyy HH:mm";
    
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userForm", new User());

        return "register";
    }
    
    @PostMapping("/register")
    public String register(@ModelAttribute("userForm") User userForm, 
    					 	   BindingResult bindingResult) {
    	
    	userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userForm.setFirstname(userForm.getUsername());
        userForm.setLastname(userForm.getUsername());
        userForm.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        userService.save(userForm);
        
        /**
		 * save userRole;
		 */
		UserRole userRole = new UserRole();
		userRole.setUser(userForm);
		Optional<Role> r = roleService.findById(ToDoUtil.USER_ROLE_ID);
		userRole.setRole(r.get());
		userRole.setStatus(Status.ACTIVE.intVal());
		userRoleService.save(userRole);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/user/user";
    }

    @GetMapping(value = "/login")
    public String login(Model model, 
    					String error, 
    					String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
    
    @GetMapping(value = "/user/user")
	public String showAllUsers(Model model) {
    	List<User> users = new ArrayList<User>();
    	if (securityService != null && securityService.getLoggedInUser() != null) {
    		if (securityService.getLoggedInUser().isAdmin()) {
    			users = this.userService.findAll();
    		}else {
    			users.add(this.userService.findById(securityService.getLoggedInUser().getId()).get());
    		}
    		model.addAttribute("adminControl", securityService.getLoggedInUser().isAdmin());
		}
		model.addAttribute("users", users);
		
		return "/user/user";

	}
    
    @GetMapping(value = "/task/task")
	public String showAllTask(Model model) {
		List<Task> tasks = new ArrayList<Task>();
		if (this.securityService != null && this.securityService.getLoggedInUser() != null) {
			if (securityService.getLoggedInUser().isAdmin()) {
				tasks = this.taskService.findAll();
			}else {
				tasks = this.taskService.findByOwner(securityService.getLoggedInUser());
			}
			model.addAttribute("adminControl", securityService.getLoggedInUser().isAdmin());
		}
		
//		tasks.forEach(t -> t.setEditable(this.securityService.getLoggedInUser().equals(t.getOwner())));
		tasks = ToDoUtil.setTaskListEditableField(tasks, this.securityService.getLoggedInUser());
		model.addAttribute("editable",true);
		model.addAttribute("tasks", tasks);
		model.addAttribute("filterTaskAtt", new Task());
		return "task/task";

	}
    
    @PostMapping(value = "/user/user")
	public String saveOrUpdateUser(@ModelAttribute("userForm") @Validated User user,
								   BindingResult result, 
								   Model model, 
								   final RedirectAttributes redirectAttributes) {
		try {
			if (!userIsValid(user, redirectAttributes)) {
				if (user.getId() != null) {
					return "redirect:/user/"+user.getId()+"/update";
				}
				return "redirect:/user/addUser";
			}
			if (user.isNew()) {
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				userService.save(user);
				
				UserRole userRole = new UserRole();
				userRole.setUser(user);
				userRole.setRole(user.getRole());
				userRole.setStatus(Status.ACTIVE.intVal());
				this.userRoleService.save(userRole);
				redirectAttributes.addFlashAttribute("css", "success");
				redirectAttributes.addFlashAttribute("msg", "User is created successfully!");
			}else {
				User persistedUser = this.userService.findById(user.getId()).get();
				persistedUser.setFirstname(user.getFirstname());
				persistedUser.setLastname(user.getLastname());
				persistedUser.setStatus(user.getStatus());
				persistedUser.setUsername(user.getUsername());
				if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
					persistedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				}
				userService.save(persistedUser);
				if (user.getRole() != null) {
					UserRole userRole = this.userRoleService.findByUser(persistedUser).get(0);
					userRole.setRole(user.getRole());
					this.userRoleService.save(userRole);
				}
				redirectAttributes.addFlashAttribute("css", "success");
				redirectAttributes.addFlashAttribute("msg", "User is updated successfully!");
			}
			
			return "redirect:/user/"+user.getId()+"/update";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Server Error!");
		}
		return  "redirect:/user/user";
	}
    
    @PostMapping(value = "/task/task")
	public String saveOrUpdateTask(@RequestParam("startdate")  @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
								   @RequestParam("enddate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
								   @ModelAttribute("taskForm") @Validated Task task,
								   BindingResult result, 
								   Model model, 
								   final RedirectAttributes redirectAttributes) throws ParseException {
    	try {
    		model.addAttribute("filterTaskAtt", new Task());
    		boolean isNew = task.isNew();
    		task.setStartdate(startDate);
    		task.setEnddate(endDate);
    		task.setOwner(securityService.getLoggedInUser());
    		task.setStatus(Status.ACTIVE.intVal());
    		if (!taskIsValid(task, redirectAttributes)) {
    			if (task.getId() != null) {
    				return "redirect:/task/"+task.getId()+"/update";
				}
				return "redirect:/task/addTask";
			}
    		this.taskService.save(task);
    		if (isNew) {
    			redirectAttributes.addFlashAttribute("css", "success");
    			redirectAttributes.addFlashAttribute("msg", "Task is created successfully!");
			}else {
				redirectAttributes.addFlashAttribute("css", "success");
				redirectAttributes.addFlashAttribute("msg", "Task is updated successfully!");
			}
    		return "redirect:/task/"+task.getId()+"/update";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Server Error!");
		}
		
		return "redirect:/task/task";
	}
    

 	@RequestMapping(value = "/user/{id}/update", method = RequestMethod.GET)
 	public String updateUserForm(@PathVariable("id") long id, Model model) {
 		User user = (userService.findById(id)).get();
 		model.addAttribute("userForm", user);
 		model.addAttribute("statusList", Status.values() );
 		model.addAttribute("roleList", this.roleService.findAll());
 		model.addAttribute("adminControl", securityService.getLoggedInUser().isAdmin());
 		
 		return "/user/userForm";
 	}
 	
 	@RequestMapping(value = "/task/{id}/update", method = RequestMethod.GET)
 	public String updateTaskForm(@PathVariable("id") long id, 
 								 Model model) {
 		
 		Task task = this.taskService.findById(id).get();
 		if (!this.securityService.getLoggedInUser().equals(task.getOwner())) {
 			return "redirect:/task/task";
		}
 		model.addAttribute("taskForm", task);
		model.addAttribute("adminControl", securityService.getLoggedInUser().isAdmin());
		model.addAttribute("statusList", Status.values());
		model.addAttribute("taskStatusList", TaskStatus.values());
		
		return "/task/taskForm";

 	}
 	
 	@RequestMapping(value = "/user/{id}/delete", method = RequestMethod.GET)
 	public String deleteUser(@PathVariable("id") long id, 
							 final RedirectAttributes redirectAttributes) {
 		try {
 			User user = this.userService.findById(id).get();
 	 		userService.delete(user);
 			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "User is deleted successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Server Error");
		}
 		
 		return "redirect:/user/user";
 	}
 	
 	
 	@RequestMapping(value = "/task/{id}/delete", method = RequestMethod.GET)
 	public String deleteTask(@PathVariable("id") long id, 
 							 final RedirectAttributes redirectAttributes) {
 		try {
 			Task task = this.taskService.findById(id).get();
 			if (!this.securityService.getLoggedInUser().equals(task.getOwner())) {
 	 			return "redirect:/task/task";
 			}
 	 		this.taskService.delete(task);
 	 		redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Task is deleted successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Server Error!");
		}
 		
 		return "redirect:/task/task";
 	}
    
    @RequestMapping(value = "/user/addUser", method = RequestMethod.GET)
	public String newUserForm(Model model) {
		User user = new User();
		model.addAttribute("userForm", user);
		model.addAttribute("roleList", this.roleService.findAll());
		model.addAttribute("adminControl", securityService.getLoggedInUser().isAdmin());
		model.addAttribute("statusList", Status.values());
		
		return "/user/userForm";
	}
    
    @RequestMapping(value = "/task/addTask", method = RequestMethod.GET)
	public String newTaskForm(Model model) {
		Task task = new Task();
		model.addAttribute("taskForm", task);
		model.addAttribute("adminControl", securityService.getLoggedInUser().isAdmin());
		model.addAttribute("statusList", Status.values());
		model.addAttribute("taskStatusList", TaskStatus.values());
		
		return "/task/taskForm";
	}
    
    @RequestMapping(value = "/task/filter", method = RequestMethod.GET)
   	public String filterTask(Model model,
					 	 	 @RequestParam("startdate")  @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
   			                 @RequestParam("enddate")  @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
   			                 RedirectAttributes redirectAttributes) {
    	
    	List<Task> tasks = new ArrayList<Task>();
    	if (!filterIsValid(startDate, endDate, redirectAttributes)) {
    		return "redirect:/task/task";
		}
    	if (this.securityService.getLoggedInUser().isAdmin())
			tasks = this.taskService.findTasksBetween(startDate, endDate);
		else
			tasks = this.taskService.findTasksByDateAndOwner(startDate, 
															 endDate, 
															 this.securityService.getLoggedInUser());
    	tasks = ToDoUtil.setTaskListEditableField(tasks, this.securityService.getLoggedInUser());
   		model.addAttribute("tasks", tasks);
   		model.addAttribute("adminControl", securityService.getLoggedInUser().isAdmin());
   		Task task = new Task();
   		task.setStartdate(startDate);
   		task.setEnddate(endDate);
   		model.addAttribute("filterTaskAtt", task);
   		
   		return "task/task";
   	}
    
    public boolean userIsValid(User user, RedirectAttributes redirectAttributes) {
    	User persistedUser = this.userService.findByUserName(user.getUsername());
    	if (user.isNew()) {
    		if (persistedUser != null) {
        		redirectAttributes.addFlashAttribute("css", "danger");
    			redirectAttributes.addFlashAttribute("msg", "UserName error!");
        		return false;
        	}
		}
    	if (user.getPassword() != null && !user.getPassword().isEmpty()) {
			// do nothing;
		}else {
			if (user.isNew()) {
				redirectAttributes.addFlashAttribute("css", "danger");
				redirectAttributes.addFlashAttribute("msg", "Password can not be empty!");
				return false;
			}
		}
    	return true;
    }
    
    public boolean taskIsValid(Task task, RedirectAttributes redirectAttributes) {
    	if (task.getStartdate() != null && task.getEnddate() != null
    			&& task.getStartdate().before(task.getEnddate())) {
			// do nothing;
		}else {
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Task date is empty or start date is before end date");
			return false;
		}
    	return true;
    }
    
    public boolean filterIsValid(Date startDate, Date endDate, RedirectAttributes redirectAttributes) {
    	if (startDate != null) {
			// do nothing;
		}else {
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Start date can not be empty!");
			return false;
		}
    	
    	if (endDate != null) {
			// do nothing;
		}else {
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "End date can not be empty!");
			return false;
		}
    	
    	if (startDate.after(endDate)) {
    		redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Start date can not be after end date!");
			return false;
		}
    	return true;
    }
    
    @GetMapping(value = "/")
    public String homePage(Model model) {
        return "redirect:/user/user";
    }
    
    @GetMapping(value = "/user")
    public String userPath(Model model) {
        return "redirect:/user/user";
    }
    
    @GetMapping(value = "/task")
    public String taskPage(Model model) {
        return "redirect:/task/task";
    }
    
}
