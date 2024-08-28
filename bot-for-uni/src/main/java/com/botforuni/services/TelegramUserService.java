package com.botforuni.services;

import com.botforuni.domain.Position;
import com.botforuni.domain.TelegramUser;
import com.botforuni.repositories.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TelegramUserService {
    private static TelegramUserRepository telegramUserRepository;

    @Autowired
    public TelegramUserService(TelegramUserRepository telegramUserRepository) {
        TelegramUserService.telegramUserRepository = telegramUserRepository;
    }

    public static void save(TelegramUser telegramUser) {
        telegramUserRepository.save(telegramUser);
    }

    public static void remove(TelegramUser telegramUser) {
        telegramUserRepository.deleteById(telegramUser.getTelegramId());
    }




    public static TelegramUser getOrGenerate(Long telegramId){
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
                    Position.NONE,
                    (long)-1 );
            TelegramUserService.save(telegramUser);
        }
        return telegramUser;
    }

    public static TelegramUser findById(Long telegramId){
        return telegramUserRepository.findById(telegramId).get();

    }

}
