package com.dental.system.service;

import com.dental.system.model.User;
import com.dental.system.dao.InUserDAO;

import java.util.Collections;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class UserService implements InUserService {

    private final InUserDAO inUserDAO;

    public UserService(InUserDAO inUserDAO) {
        this.inUserDAO = inUserDAO;
    }

    // Validate user login
    @Override
    public User login(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            return null;
        }
        User user = inUserDAO.login(username.trim(), password);

        if (user == null) {
            return null;
        }

        if (!BCrypt.checkpw(
                password,
                user.getPassword()
        )) {
            return null;
        }

        return user;
    }

    // Add user
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
        user.setPassword(
                BCrypt.hashpw(
                        user.getPassword(),
                        BCrypt.gensalt()
                )
        );

        return inUserDAO.addUser(user);
    }


    // Get user by ID
    @Override
    public User getUserById(int userId) {

        if (userId <= 0) {
            return null;
        }

        return inUserDAO.getUserById(userId);
    }


    // Get all users
    @Override
    public List<User> getAllUsers() {

        List<User> users =
                inUserDAO.getAllUsers();

        if (users == null) {
            return Collections.emptyList();
        }

        return users;
    }


    // Update user
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

        user.setPassword(
                BCrypt.hashpw(
                        user.getPassword(),
                        BCrypt.gensalt()
                )
        );

        return inUserDAO.updateUser(user);
    }


    // Delete user
    @Override
    public boolean deleteUser(int userId) {

        if (userId <= 0) {
            return false;
        }

        return inUserDAO.deleteUser(userId);
    }


    // Validate user role
    private boolean isValidRole(String role) {

        if (role == null) {
            return false;
        }

        return "Administrator".equalsIgnoreCase(role) ||
                "USER".equalsIgnoreCase(role);
    }
}
