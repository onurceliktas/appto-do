package com.toDoListApp.security;

import com.toDoListApp.model.User;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
    
    User getLoggedInUser();
}
