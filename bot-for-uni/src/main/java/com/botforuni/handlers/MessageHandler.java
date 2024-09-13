package com.botforuni.handlers;

import com.botforuni.keybords.Keyboards;
import com.botforuni.domain.Position;
import com.botforuni.domain.StatementCache;
import com.botforuni.domain.TelegramUserCache;
import com.botforuni.services.SendMessageService;
import com.botforuni.services.TelegramUserService;
import com.botforuni.utils.Constants;
import com.botforuni.utils.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Slf4j
@Component
public class MessageHandler implements Handler<Message> {

    @Autowired
    private TelegramUserService telegramUserService;

    @Autowired
    private SendMessageService sendMessageService;

    @Override
    public void choose(Message message) {
        Long chatId = message.getChatId();
        log.info("Отримано повідомлення від користувача з ID: {}", chatId);

        TelegramUserCache userCache = telegramUserService.getOrGenerate(chatId);
        Position currentPosition = userCache.getPosition();
        log.debug("Поточна позиція користувача: {}", currentPosition);



        if (currentPosition != Position.NONE) {
            handleUserInputByPosition(currentPosition, message, userCache);
        } else if (message.hasText()) {
            handleCommands(message.getText(), chatId);
        }
    }



    private void handleUserInputByPosition(Position position, Message message, TelegramUserCache userCache) {
        Long telegramId = userCache.getTelegramId();
        StatementCache statementCache = userCache.getStatementCache();
        String messageText = message.getText();


        switch (position) {
            case INPUT_USER_NAME -> handleNameInput(messageText, telegramId, userCache, statementCache);
            case INPUT_USER_GROUP -> handleGroupInput(messageText, telegramId, userCache, statementCache);
            case INPUT_USER_YEAR -> handleYearInput(messageText, telegramId, userCache, statementCache);
            case INPUT_USER_FACULTY -> handleFacultyInput(messageText, telegramId, userCache, statementCache);
            case INPUT_USER_PHONE -> handlePhoneInput(message, telegramId, userCache, statementCache);
            default -> log.warn("Некоректна позиція користувача: {}", position);
        }
    }

    private void handleNameInput(String name, Long telegramId, TelegramUserCache userCache, StatementCache statementCache) {
        log.info("Користувач з ID: {} вводить своє ім'я: {}", telegramId, name);
        if (Validator.validateName(name)) {
            statementCache.setFullName(name);
            userCache.setPosition(Position.INPUT_USER_GROUP);
            userCache.setStatementCache(statementCache);
            telegramUserService.save(userCache);
            sendMessageService.sendMessage(telegramId, "Введіть вашу групу (Наприклад: КН23c)⤵");
            log.debug("Оновлено позицію користувача на INPUT_USER_GROUP");

        } else {
            sendValidationError(telegramId, "Ім'я некоректне. Будь ласка, введіть ім'я без чисел і символів.");
        }
    }

    private void handleGroupInput(String group, Long telegramId, TelegramUserCache userCache, StatementCache statementCache) {
        log.info("Користувач з ID: {} вводить групу: {}", telegramId, group);
        if (Validator.validateGroup(group)) {
            statementCache.setGroupe(group);
            userCache.setPosition(Position.INPUT_USER_YEAR);
            userCache.setStatementCache(statementCache);
            telegramUserService.save(userCache);
            sendMessageService.sendMessage(telegramId, "Введіть ваш рік набору (Наприклад: 2021)⤵");

            log.debug("Оновлено позицію користувача на INPUT_USER_YEAR");
        } else {
            sendValidationError(telegramId, "Група некоректна. Приклад правильного формату: КН23c.");
        }
    }

    private void handleYearInput(String year, Long telegramId, TelegramUserCache userCache, StatementCache statementCache) {
        log.info("Користувач з ID: {} вводить рік набору: {}", telegramId, year);

        if (Validator.validateYear(year)) {
            statementCache.setYearEntry(year);
            userCache.setPosition(Position.INPUT_USER_FACULTY);
            userCache.setStatementCache(statementCache);
            telegramUserService.save(userCache);
            sendMessageService.sendMessage(telegramId, "Виберіть ваш факультет", Keyboards.chooseFaculty());
            log.debug("Оновлено позицію користувача на INPUT_USER_FACULTY");

        } else {
            sendValidationError(telegramId, "Рік некоректний. Введіть 4 цифри (Наприклад: 2021).");
        }
    }

    private void handleFacultyInput(String faculty, Long telegramId, TelegramUserCache userCache, StatementCache statementCache) {
        log.info("Користувач з ID: {} вибирає факультет: {}", telegramId, faculty);
        statementCache.setFaculty(faculty);
        userCache.setPosition(Position.INPUT_USER_PHONE);
        userCache.setStatementCache(statementCache);
        telegramUserService.save(userCache);
        sendMessageService.sendMessage(telegramId, "Введіть ваш номер телефону⤵", Keyboards.keyboardRemove());

        sendMessageService.sendMessage(telegramId, "Нажміть, щоб поділитися контактом", Keyboards.phoneKeyboard());
        log.debug("Оновлено позицію користувача на INPUT_USER_PHONE");


        statementCache.setFaculty(faculty);
        updateUserPosition(userCache, statementCache, Position.INPUT_USER_PHONE, telegramId, "Введіть ваш номер телефону⤵", Keyboards.phoneKeyboard());
    }

    private void handlePhoneInput(Message message, Long telegramId, TelegramUserCache userCache, StatementCache statementCache) {
        if (message.hasContact() || Validator.validatePhoneNumber(message.getText())) {
            String phoneNumber = message.hasContact() ? message.getContact().getPhoneNumber() : message.getText();
            statementCache.setPhoneNumber(phoneNumber);
            updateUserPosition(userCache, statementCache, Position.CONFIRMATION, telegramId, statementCache.toString(), Keyboards.confirmationKeyboard());
        } else {
            sendValidationError(telegramId, "Некоректний номер телефону. Введіть правильний номер у форматі +380XXXXXXXXX.");
        }
    }

    private void handleCommands(String text, Long telegramId) {
        switch (text) {
            case "/start" -> sendMessageService.sendMessage(telegramId, Constants.START, Keyboards.starKeyboard());
            case "/help" -> sendMessageService.sendMessage(telegramId, Constants.HELP, Keyboards.helpMenu());
            default -> log.warn("Користувач з ID: {} надіслав невідому команду: {}", telegramId, text);
        }
    }


    private void updateUserPosition(TelegramUserCache userCache, StatementCache statementCache, Position newPosition, Long telegramId, String message) {
        userCache.setPosition(newPosition);
        userCache.setStatementCache(statementCache);
        sendMessageService.sendMessage(telegramId, message, Keyboards.keyboardRemove());

    }

    private void updateUserPosition(TelegramUserCache userCache, StatementCache statementCache, Position newPosition, Long telegramId, String message, ReplyKeyboardMarkup keyboard) {
        userCache.setPosition(newPosition);
        userCache.setStatementCache(statementCache);
        telegramUserService.save(userCache);
        sendMessageService.sendMessage(telegramId, message, keyboard);


    }

    private void updateUserPosition(TelegramUserCache userCache, StatementCache statementCache, Position newPosition, Long telegramId, String message, InlineKeyboardMarkup keyboard) {
        userCache.setPosition(newPosition);
        userCache.setStatementCache(statementCache);
        telegramUserService.save(userCache);
        sendMessageService.sendMessage(telegramId, message, keyboard);
    }





    private void sendValidationError(Long telegramId, String errorMessage) {
        log.warn("Помилка валідації для користувача з ID: {}", telegramId);
        sendMessageService.sendMessage(telegramId, errorMessage);
    }
}
