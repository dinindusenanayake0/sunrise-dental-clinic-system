package com.dental.system.service;

import java.util.List;
import com.dental.system.model.User;

public interface InUserService {

    User login(String username, String password);

    boolean addUser(User user);

    User getUserById(int userId);

    List<User> getAllUsers();

    boolean updateUser(User user);

    boolean deleteUser(int userId);

}
