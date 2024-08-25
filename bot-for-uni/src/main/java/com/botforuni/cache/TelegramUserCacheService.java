package com.botforuni.cache;

import com.botforuni.cache.Cache;
import com.botforuni.domain.TelegramUser;
import com.botforuni.repositories.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelegramUserCacheService implements Cache<TelegramUser> {
    @Autowired
    private static TelegramUserRepository telegramUserRepository;

   @Override
    public void add(TelegramUser telegramUser){
        telegramUserRepository.save(telegramUser);
    }
    @Override
    public void remove(TelegramUser telegramUser){
       telegramUserRepository.deleteById(telegramUser.getTelegramId());
    }

    @Override
    public TelegramUser findBy(Long id) {


        ///має бути перевірка на присутність обєкта
        return telegramUserRepository.findById(id).get();
    }

    @Override
    public List<TelegramUser> getAll() {
        return telegramUserRepository.findAll();
    }
}
