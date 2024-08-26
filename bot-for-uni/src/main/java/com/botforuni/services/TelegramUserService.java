package com.botforuni.services;

import com.botforuni.domain.TelegramUser;
import com.botforuni.repositories.TelegramUserRepository;
import com.botforuni.utils.PositionInTelegramChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class TelegramUserService {
    private static TelegramUserRepository telegramUserRepository;

    @Autowired
    public TelegramUserService(TelegramUserRepository telegramUserRepository) {
        TelegramUserService.telegramUserRepository = telegramUserRepository;
    }

    public static void add(TelegramUser telegramUser) {
        telegramUserRepository.save(telegramUser);
    }

    public static void remove(TelegramUser telegramUser) {
        telegramUserRepository.deleteById(telegramUser.getTelegramId());
    }


    public static TelegramUser get(Long telegramId){
//        витягуємо телеграм користувача з бази даних
//        якшо запису неіснує генеруємо його

        TelegramUser telegramUser;

        Optional<TelegramUser> telegramUserOptional = telegramUserRepository
                .findById(telegramId);

        if (telegramUserOptional.isPresent()){

            telegramUser =telegramUserOptional.get();
        }else {
            telegramUser= new TelegramUser(
                    telegramId,
                    PositionInTelegramChat.NONE);
            TelegramUserService.add(telegramUser);
        }
        return telegramUser;
    }

}
