package com.dental.system.service;

import com.dental.system.model.User;
import com.dental.system.dao.InUserDAO;

public class UserService implements InUserService {

    private final InUserDAO inUserDAO;

    public UserService(InUserDAO inUserDAO) {
        this.inUserDAO = inUserDAO;
    }

    @Override
    public User login(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            return null;
        }
        return inUserDAO.login(username, password);
    }
}
