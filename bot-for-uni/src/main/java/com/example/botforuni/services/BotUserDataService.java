
package com.example.botforuni.services;

import com.example.botforuni.domain.BotUser;
import com.example.botforuni.repositories.BotUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
public class BotUserDataService {
    private static  BotUserRepository botUserRepository;
    @Autowired
    public BotUserDataService(BotUserRepository botUserRepository) {
        this.botUserRepository = botUserRepository;
    }

    public static final String STATEMENTFORMILITARI = "Довідка для військомату";
    public static final String STATEMENTFORSTUDY = "Довідка з місця навчання";


    public static void putUserInDataBase(BotUser botUser) {
        botUserRepository.save(botUser);

    }


    public static void getAllInfoAboutUser(Message message, String typeOfStatement) {
        List<BotUser> botUsers=botUserRepository.findByTelegramIdAndStatement(message.getChatId(),
                STATEMENTFORMILITARI);



        for (int i = 0; i < botUsers.size(); i++) {
           BotUser element = botUsers.get(i);
            // Виконати дії з елементом
            System.out.println(element.getFullName());
        }
    }


}

