package com.dental.system.dao;

import com.dental.system.model.User;
import com.dental.system.util.DBCon;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements InUserDAO {

    // login
    @Override
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
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


    // Add a new user
    @Override
    public boolean addUser(User user) {

        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?) ";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Failed to add user: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }


    // Get user by ID
    @Override
    public User getUserById(int userId) {

        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    User user = new User();

                    user.setUserId(resultSet.getInt("user_id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));

                    return user;
                }
            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve user: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


    // Get all users
    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users ORDER BY user_id DESC";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                User user = new User();

                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));

                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve users: " + e.getMessage());
            e.printStackTrace();
        }

        return users;
    }

    // Get user by username
    @Override
    public User getUserByUsername(String username) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    User user = new User();

                    user.setUserId(resultSet.getInt("user_id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));

                    return user;
                }
            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve user by username: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


    // Update user details
    @Override
    public boolean updateUser(User user) {

        String sql = "UPDATE users SET username = ?, password = ?, role = ? WHERE user_id = ? ";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setInt(4, user.getUserId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Failed to update user: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }


    // Delete a user
    @Override
    public boolean deleteUser(int userId) {

        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Failed to delete user: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
