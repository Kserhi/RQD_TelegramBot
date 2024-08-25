package com.botforuni.services;

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

    public static void add(TelegramUser telegramUser) {
        telegramUserRepository.save(telegramUser);
    }

    public static void remove(TelegramUser telegramUser) {
        telegramUserRepository.deleteById(telegramUser.getTelegramId());
    }

    public static Optional<TelegramUser> findBy(Long id) {
        return telegramUserRepository.findById(id);
    }

}
