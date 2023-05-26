
package com.example.botforuni.jdbc;

import com.example.botforuni.domain.BotUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserData extends Config{

    private static Connection getConnectionToDataBasa() throws ClassNotFoundException,SQLException{
        Connection connection;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return connection;
    }

    public static void putUserInDataBase(BotUser botUser){

        try {
           Connection connection=getConnectionToDataBasa();
            Statement stmt= connection.createStatement();
            stmt.executeUpdate(" INSERT INTO users (teleqramId,fullName,yearEntry,statement,phoneNumber," +
                    "groupe,mail) VALUES("+botUser.getId().toString()+",'"+botUser.getFullName()+"','"+
                    botUser.getYearEntry()+"','"+botUser.getStatement()+"','"+botUser.getPhoneNumber()+"','"+
                    botUser.getGroupe()+"','"+botUser.getMail()+"');");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static List<String> getUserInfoFomDataBasa(Long userId){

        List<String> info =new ArrayList<>();

        try {
           Connection connection=getConnectionToDataBasa();
            Statement stmt= connection.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT MAX(numbers) FROM users WHERE teleqramId="+userId.toString()+";");
            rs.next();
            rs=stmt.executeQuery("SELECT * FROM users WHERE numbers="+rs.getString(1)+";");
            rs.next();

            for (int i = 0; i < 5; i++) {
                info.add(i,rs.getString(i+3));
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return info;
    }

    public static void deleteUserFromDb(Long userId){
        try {
            Connection connection = getConnectionToDataBasa();
            Statement stmt = connection.createStatement();
            stmt.execute("DELETE FROM users WHERE teleqramId = " + userId.toString() + ";");
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

    }
}
