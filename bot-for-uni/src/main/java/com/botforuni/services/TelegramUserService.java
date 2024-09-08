package com.botforuni.services;

import com.botforuni.domain.Position;
import com.botforuni.domain.TelegramUserCache;
import com.botforuni.repositories.TelegramUserCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class TelegramUserService {

    private final TelegramUserCacheRepository telegramUserCacheRepository;

    @Autowired
    public TelegramUserService(TelegramUserCacheRepository telegramUserCacheRepository) {
        this.telegramUserCacheRepository = telegramUserCacheRepository;
    }

    public void save(TelegramUserCache telegramUserCache) {
        telegramUserCacheRepository.save(telegramUserCache);
    }

//    public void remove(TelegramUserCache telegramUserCache) {
//        telegramUserCacheRepository.deleteById(telegramUserCache.getTelegramId());
//    }

    public TelegramUserCache getOrGenerate(Long telegramId) {

        return findById(telegramId)
                .orElseGet(() -> {
                    TelegramUserCache telegramUserCache = generateNewTelegramUser(telegramId);
                    save(telegramUserCache);
                    return telegramUserCache;
                });

    }




    public Optional<TelegramUserCache> findById(Long telegramId) {
        return telegramUserCacheRepository.findById(telegramId);
    }


    private TelegramUserCache generateNewTelegramUser (Long telegramId){
       return  new TelegramUserCache(telegramId,null,Position.NONE,null);
    }

    public void saveMassageId(Long chatId,Integer massageId){
        Optional<TelegramUserCache> telegramUserCacheOptional =findById(chatId);

        if (telegramUserCacheOptional.isPresent()){
            TelegramUserCache telegramUserCache=telegramUserCacheOptional.get();
            telegramUserCache.setMassageId(massageId);
            save(telegramUserCache);
        }

    }
}

