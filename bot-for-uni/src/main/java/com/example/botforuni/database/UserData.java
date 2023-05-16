package com.example.botforuni.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserData {

    private static final String URL = "jdbc:mysql://localhost:3306/databasebot";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root12345678";

    public static void main(String[] args) throws ClassNotFoundException{
        Connection connection;

        Class.forName("com.mysql.cj.jdbc.Driver");

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Зєднання виконано");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
