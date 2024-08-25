package com.botforuni.services;

import com.botforuni.domain.TelegramUser;
import com.botforuni.repositories.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramUserService {
    @Autowired
    private static TelegramUserRepository telegramUserRepository;

    public void add(TelegramUser telegramUser){
        telegramUserRepository.save(telegramUser);
    }
    public void remove(TelegramUser telegramUser){
       telegramUserRepository.deleteById(telegramUser.getTelegramId());
    }

    public TelegramUser findBy(Long id) {


        ///має бути перевірка на присутність обєкта
        return telegramUserRepository.findById(id).get();
    }

}
