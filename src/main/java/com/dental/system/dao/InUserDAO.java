package com.dental.system.dao;

import com.dental.system.model.User;

public interface InUserDAO {

    User login(String username, String password);

    boolean addUser(User user);

    User getUserById(int userId);

    boolean updateUser(User user);

    boolean deleteUser(int userId);

}
