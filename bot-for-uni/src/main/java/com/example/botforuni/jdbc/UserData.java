
package com.example.botforuni.jdbc;

import com.example.botforuni.domain.BotUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class UserData {

    public static final String STATEMENTFORMILITARI = "Довідка для військомату";
    public static final String STATEMENTFORSTUDY = "Довідка з місця навчання";

//    private static String url;
//    private static String username;
//    private static String password;
//    private static String jdbc_driver;


    private static Connection getConnectionToDataBasa() throws ClassNotFoundException, SQLException {
        Properties properties = new Properties();
//        try (FileInputStream input = new FileInputStream("application.properties")) {
//            properties.load(input);
//            url = properties.getProperty("telegram.bot.url");
//            username = properties.getProperty("telegram.bot.userName");
//            password = properties.getProperty("telegram.bot.password");
//            jdbc_driver = properties.getProperty("telegram.bot.jdbcDriver");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Connection connection;
        Class.forName(properties.getProperty("telegram.bot.jdbcDriver"));
        connection = DriverManager.getConnection(properties.getProperty("telegram.bot.url"),properties.getProperty("telegram.bot.userName"), properties.getProperty("telegram.bot.password"));

        return connection;
    }


    public static void putUserInDataBase(BotUser botUser) {

        try {
            Connection connection = getConnectionToDataBasa();
            Statement stmt = connection.createStatement();
            if (botUser.getStatement().equals(STATEMENTFORSTUDY)) {
                stmt.executeUpdate(" INSERT INTO users (teleqramId,fullName,yearEntry,statement,phoneNumber," +
                        "groupe,mail) VALUES(" + botUser.getId().toString() + ",'" + botUser.getFullName() + "','" +
                        botUser.getYearEntry() + "','" + botUser.getStatement() + "','" + botUser.getPhoneNumber() + "','" +
                        botUser.getGroupe() + "','" + botUser.getMail() + "');");
            } else if (botUser.getStatement().equals(STATEMENTFORMILITARI)) {
                stmt.executeUpdate(" INSERT INTO militari (teleqramId,fullName,yearEntry,statement,phoneNumber," +
                        "groupe,mail) VALUES(" + botUser.getId().toString() + ",'" + botUser.getFullName() + "','" +
                        botUser.getYearEntry() + "','" + botUser.getStatement() + "','" + botUser.getPhoneNumber() + "','" +
                        botUser.getGroupe() + "','" + botUser.getMail() + "');");
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getMilitariStatment(Long userId) {

        List<String> info = new ArrayList<>();

        try {
            Connection connection = getConnectionToDataBasa();
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM militari WHERE numbers = (SELECT " +
                    "MAX(numbers) FROM militari WHERE teleqramId ="+userId.toString()+")");


            if(rs.next()){
                    for (int i = 0; i < 8; i++) {
                        info.add(i, rs.getString(i + 1));
                    }

                }else {
                    return info;
                }



            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return info;
    }

    public static List<String> getStudiStatment(Long userId) {
        List<String> info = new ArrayList<>();
        try {
            Connection connection = getConnectionToDataBasa();
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE numbers = (SELECT " +
                    "MAX(numbers) FROM users WHERE teleqramId ="+userId.toString()+")");



            if(rs.next()){
                for (int i = 0; i < 8; i++) {
                    info.add(i, rs.getString(i + 1));
                }

            }else {
                return info;
            }


            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return info;
    }


//    public static void deleteUserFromDb(Long userId) {
//        try {
//            Connection connection = getConnectionToDataBasa();
//            Statement stmt = connection.createStatement();
//            stmt.execute("DELETE FROM users WHERE teleqramId = " + userId.toString() + ";");
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }
}
