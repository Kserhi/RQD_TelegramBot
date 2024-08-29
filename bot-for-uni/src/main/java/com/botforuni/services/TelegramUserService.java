package com.botforuni.services;

import com.botforuni.domain.Position;
import com.botforuni.domain.TelegramUser;
import com.botforuni.repositories.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class TelegramUserService {

    private final TelegramUserRepository telegramUserRepository;

    @Autowired
    public TelegramUserService(TelegramUserRepository telegramUserRepository) {
        this.telegramUserRepository = telegramUserRepository;
    }

    public void save(TelegramUser telegramUser) {
        telegramUserRepository.save(telegramUser);
    }

    public void remove(TelegramUser telegramUser) {
        telegramUserRepository.deleteById(telegramUser.getTelegramId());
    }

    public TelegramUser getOrGenerate(Long telegramId) {
        // Витягуємо телеграм користувача з бази даних
        // Якщо запису не існує, генеруємо його

        Optional<TelegramUser> telegramUserOptional = telegramUserRepository.findById(telegramId);

        if (telegramUserOptional.isPresent()) {
            return telegramUserOptional.get();
        } else {
            TelegramUser telegramUser = new TelegramUser(
                    telegramId,
                    Position.NONE,
                    (long) -1
            );
            save(telegramUser);
            return telegramUser;
        }
    }

    public TelegramUser findById(Long telegramId) {
        return telegramUserRepository.findById(telegramId)
                .orElseThrow(() -> new RuntimeException("Відсутній телеграм користувач id: " + telegramId));
    }
}

