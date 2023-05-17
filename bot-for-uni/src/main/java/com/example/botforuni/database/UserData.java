package com.example.botforuni.database;

import com.example.botforuni.domain.BotUser;
import com.mysql.cj.jdbc.ConnectionImpl;
import org.springframework.stereotype.Component;

import java.sql.*;
@Component
public class UserData {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/databasa";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    public void getAllUsersFormDataBasa(){
        Connection connection;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);




            Statement stmt= connection.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM users ;");
            while (rs.next()) System.out.println
                    (rs.getString(1)+" "+rs.getString(2)+" "
                            +rs.getString(3)+" "+rs.getString(4)+"  "
                            +rs.getString(5)+" "+rs.getString(6)+"  "
                            +rs.getString(7)+"  "+rs.getString(8));
            connection.close();



        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//    public void get(Long id){
//        Connection connection;
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//
//            Statement stmt= connection.createStatement();
//            ResultSet rs=stmt.executeQuery("SELECT * FROM users WHERE teleqramId="+id.toString()+";");
//            while (rs.next()) System.out.println
//                    (rs.getString(1)+" "+rs.getString(2)+" "
//                            +rs.getString(3)+" "+rs.getString(4)+"  "
//                            +rs.getString(5)+" "+rs.getString(6)+"  "
//                            +rs.getString(7)+"  "+rs.getString(8)+"  "
//                            +rs.getString(9)+"  ");
//            connection.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
    public void putUserInDataBase(BotUser botUser){
        Connection connection;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement stmt= connection.createStatement();
            stmt.executeUpdate(" INSERT INTO users (telegramId,fullName,yearEntry,statement,phoneNumber," +
                    "groupe,mail) VALUES("+botUser.getId().toString()+",'"+botUser.getFullName()+"','"+
                    botUser.getYearEntry()+"','"+botUser.getStatement()+"','"+botUser.getPhoneNumber()+"','"+
                    botUser.getGroupe()+"','"+botUser.getMail()+");");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
