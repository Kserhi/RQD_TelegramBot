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

    public void remove(TelegramUserCache telegramUserCache) {
        telegramUserCacheRepository.deleteById(telegramUserCache.getTelegramId());
    }

    public TelegramUserCache getOrGenerate(Long telegramId) {
        // Витягуємо телеграм користувача з бази даних
        // Якщо запису не існує, генеруємо його

        Optional<TelegramUserCache> telegramUserOptional = telegramUserCacheRepository.findById(telegramId);

        if (telegramUserOptional.isPresent()) {
            return telegramUserOptional.get();
        } else {
            TelegramUserCache telegramUserCache = new TelegramUserCache();
            telegramUserCache.setTelegramId(telegramId);
            telegramUserCache.setPosition(Position.NONE);

            save(telegramUserCache);
            return telegramUserCache;
        }
    }

    public TelegramUserCache findById(Long telegramId) {
        return telegramUserCacheRepository.findById(telegramId)
                .orElseThrow(() -> new RuntimeException("Відсутній телеграм користувач id: " + telegramId));
    }
}

