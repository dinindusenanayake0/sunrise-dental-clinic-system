package com.dental.system.dao;

import com.dental.system.model.User;
import java.util.List;

public interface InUserDAO {

    User login(String username, String password);

    boolean addUser(User user);

    User getUserById(int userId);

    boolean updateUser(User user);

    boolean deleteUser(int userId);

    List<User> getAllUsers();

    User getUserByUsername(String username);
}
