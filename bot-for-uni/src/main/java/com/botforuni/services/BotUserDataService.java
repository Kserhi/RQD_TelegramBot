
package com.botforuni.services;

import com.botforuni.domain.BotUser;
import com.botforuni.domain.TelegramUser;
import com.botforuni.repositories.BotUserRepository;
import com.botforuni.repositories.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BotUserDataService {

    private static BotUserRepository botUserRepository;

    @Autowired
    public BotUserDataService(BotUserRepository botUserRepository) {
        BotUserDataService.botUserRepository = botUserRepository;
    }

    public static void putUserInDataBase(BotUser botUser) {
        botUserRepository.save(botUser);

    }


    synchronized public static BotUser getAllInfoAboutUser(Long telegramId, String typeOfStatement) {
//        написав метод який витягує список  користувачів із бази даних з необхідними
//                телеграм id і statement


        BotUser botUser = new BotUser();

        List<BotUser> botUsers = botUserRepository.findByTelegramIdAndStatement(
                telegramId,
                typeOfStatement);


        if (!botUsers.isEmpty()) {
            botUser = botUsers.get(botUsers.size() - 1);
        }
        return botUser;

    }

    synchronized public static List<BotUser> getTrueUsers() {
        List<BotUser> botUsers = botUserRepository.findAll();

        List<BotUser> botUserList = new ArrayList<>();

        botUsers.forEach(botUser -> {
            if (botUser.isStatus() && !botUser.isReady()) {
                botUserList.add(botUser);
            }
        });


        return botUserList;

    }

    public static  void updateIsRedy(List<BotUser> botUserList) {

        botUserList.forEach(botUser -> {
            // Знаходимо об'єкт за його id
            BotUser entityToUpdate = botUserRepository
                    .findById(botUser.getId())
                    .orElse(null);

            if (entityToUpdate != null) {
                // Оновлюємо поле
                entityToUpdate.setReady(true);
                // Виконуємо збереження змін
                botUserRepository.save(entityToUpdate);
            }
        });


    }
}










