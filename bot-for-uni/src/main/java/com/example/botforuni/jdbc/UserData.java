
package com.example.botforuni.jdbc;

import com.example.botforuni.domain.BotUser;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserData {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/db_bot";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root12345678";


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

    public void putUserInDataBase(BotUser botUser){
        Connection connection;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

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
    public  List<String> getUserInfoFomDataBasa(Long userId){
        Connection connection;
        List<String> info =new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt= connection.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT *,MAX(numbers)  FROM users  WHERE teleqramId="+userId.toString()+
                    " GROUP BY(numbers);");
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
}
