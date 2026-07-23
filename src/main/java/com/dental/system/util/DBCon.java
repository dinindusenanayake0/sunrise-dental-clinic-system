package com.dental.system.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {
    //Database Details
    private static final String URL = "jdbc:mysql://localhost:3306/sunrise_dental_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection(){
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("Database Connected");

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver Not Found");
            e.printStackTrace();

        }catch (SQLException e){
            System.out.println("Database Connection Failed");
            e.printStackTrace();

        }
        return connection;
    }
}
