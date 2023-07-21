
package com.example.botforuni.jdbc;

import com.example.botforuni.domain.BotUser;
import com.example.botforuni.repositories.BotUserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    private final BotUserRepository botUserRepository;
    
    public UserData(BotUserRepository botUserRepository) {
        this.botUserRepository = botUserRepository;
    }

    public static final String STATEMENTFORMILITARI = "Довідка для військомату";
    public static final String STATEMENTFORSTUDY = "Довідка з місця навчання";



    public static void putUserInDataBase(BotUser botUser) {



            if (botUser.getStatement().equals(STATEMENTFORSTUDY)) {

            } else if (botUser.getStatement().equals(STATEMENTFORMILITARI)) {

            }


    }

    public static List<String> getMilitariStatment(Long userId) {

        List<String> info = new ArrayList<>();
        return info;
    }

    public static List<String> getStudiStatment(Long userId) {
        List<String> info = new ArrayList<>();


        return info;
    }}

