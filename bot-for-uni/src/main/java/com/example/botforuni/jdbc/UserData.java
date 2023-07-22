
package com.example.botforuni.jdbc;

import com.example.botforuni.domain.BotUser;
import com.example.botforuni.repositories.BotUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserData {
    private static  BotUserRepository botUserRepository;
    @Autowired
    public UserData(BotUserRepository botUserRepository) {
        this.botUserRepository = botUserRepository;
    }

    public static final String STATEMENTFORMILITARI = "Довідка для військомату";
    public static final String STATEMENTFORSTUDY = "Довідка з місця навчання";


    public static void putUserInDataBase(BotUser botUser) {
        botUserRepository.save(botUser);

    }

    public static List<String> getMilitariStatment(Long userId) {
        List<String> info = new ArrayList<>();
        return info;
    }

    public static List<String> getStudiStatment(Long userId) {
        List<String> info = new ArrayList<>();


        return info;
    }
}

