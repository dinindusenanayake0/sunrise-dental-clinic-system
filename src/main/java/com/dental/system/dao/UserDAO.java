package com.dental.system.dao;

import com.dental.system.model.User;
import com.dental.system.util.DBCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements InUserDAO {

    @Override
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(int userId) {
        return false;
    }
}
