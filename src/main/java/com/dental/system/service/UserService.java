package com.dental.system.service;

import com.dental.system.model.User;
import com.dental.system.dao.InUserDAO;

import java.util.Collections;
import java.util.List;

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


    @Override
    public boolean addUser(User user) {

        if (user == null) {
            return false;
        }

        if (user.getUsername() == null ||
                user.getUsername().trim().isEmpty()) {
            return false;
        }

        if (user.getPassword() == null ||
                user.getPassword().trim().isEmpty()) {
            return false;
        }

        if (!isValidRole(user.getRole())) {
            return false;
        }

        User existingUser =
                inUserDAO.getUserByUsername(
                        user.getUsername().trim()
                );

        if (existingUser != null) {
            return false;
        }

        user.setUsername(
                user.getUsername().trim()
        );

        user.setRole(
                "Administrator".equalsIgnoreCase(user.getRole())
                        ? "Administrator"
                        : "User"
        );

        return inUserDAO.addUser(user);
    }

    @Override
    public User getUserById(int userId) {

        if (userId <= 0) {
            return null;
        }

        return inUserDAO.getUserById(userId);
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users =
                inUserDAO.getAllUsers();

        if (users == null) {
            return Collections.emptyList();
        }

        return users;
    }

    @Override
    public boolean updateUser(User user) {

        if (user == null ||
                user.getUserId() <= 0) {
            return false;
        }

        if (user.getUsername() == null ||
                user.getUsername().trim().isEmpty()) {
            return false;
        }

        if (user.getPassword() == null ||
                user.getPassword().trim().isEmpty()) {
            return false;
        }

        if (!isValidRole(user.getRole())) {
            return false;
        }

        User existingUser =
                inUserDAO.getUserByUsername(
                        user.getUsername().trim()
                );

        if (existingUser != null &&
                existingUser.getUserId() != user.getUserId()) {
            return false;
        }

        user.setUsername(
                user.getUsername().trim()
        );

        user.setRole(
                "Administrator".equalsIgnoreCase(user.getRole())
                        ? "Administrator"
                        : "User"
        );

        return inUserDAO.updateUser(user);
    }

    @Override
    public boolean deleteUser(int userId) {

        if (userId <= 0) {
            return false;
        }

        return inUserDAO.deleteUser(userId);
    }

    private boolean isValidRole(String role) {

        if (role == null) {
            return false;
        }

        return "Administrator".equalsIgnoreCase(role) ||
                "USER".equalsIgnoreCase(role);
    }
}
