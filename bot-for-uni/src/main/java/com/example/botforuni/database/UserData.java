package com.example.botforuni.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserData {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/databasa";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        Connection connection;


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Зєднання виконано");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

    }
}
