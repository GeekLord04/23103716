package com.cts.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/research_lab";
    private static final String USER = "root";
    private static final String PASSWORD = "Admin@1234";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Include the MySQL JDBC Driver library in the classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to establish a database connection.");
            e.printStackTrace();
        }
        return connection;
    }
}
