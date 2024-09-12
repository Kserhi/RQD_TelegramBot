package com.botforuni.handlers;

import com.botforuni.keybords.Keyboards;
import com.botforuni.domain.Position;
import com.botforuni.domain.StatementCache;
import com.botforuni.domain.TelegramUserCache;
import com.botforuni.services.SendMessageService;
import com.botforuni.services.TelegramUserService;
import com.botforuni.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Slf4j
@Component
public class MessageHandler implements Handler<Message> {
    @Autowired
    private TelegramUserService telegramUserService;

    @Autowired
    private SendMessageService sendMessageService;

    @Override
    public void choose(Message message) {
        Long telegramId = message.getChatId();

        log.info("Отримано повідомлення від користувача з ID: {}", telegramId);

        TelegramUserCache telegramUserCache = telegramUserService.getOrGenerate(telegramId);

        log.debug("Поточна позиція користувача: {}", telegramUserCache.getPosition());

        if (telegramUserCache.getPosition() != Position.NONE) {
            StatementCache statementCache = telegramUserCache.getStatementCache();

            switch (telegramUserCache.getPosition()) {
                case INPUT_USER_NAME -> {
                    log.info("Користувач з ID: {} вводить своє ім'я: {}", telegramId, message.getText());
                    statementCache.setFullName(message.getText());
                    telegramUserCache.setPosition(Position.INPUT_USER_GROUP);
                    telegramUserCache.setStatementCache(statementCache);
                    telegramUserService.save(telegramUserCache);
                    sendMessageService.sendMessage(telegramId, "Введіть вашу групу (Наприклад: КН23c)⤵");
                    log.debug("Оновлено позицію користувача на INPUT_USER_GROUP");
                }
                case INPUT_USER_GROUP -> {
                    log.info("Користувач з ID: {} вводить групу: {}", telegramId, message.getText());
                    statementCache.setGroupe(message.getText());
                    telegramUserCache.setPosition(Position.INPUT_USER_YEAR);
                    telegramUserCache.setStatementCache(statementCache);
                    telegramUserService.save(telegramUserCache);
                    sendMessageService.sendMessage(telegramId, "Введіть ваш рік набору (Наприклад: 2021)⤵");
                    log.debug("Оновлено позицію користувача на INPUT_USER_YEAR");
                }
                case INPUT_USER_YEAR -> {
                    log.info("Користувач з ID: {} вводить рік набору: {}", telegramId, message.getText());
                    statementCache.setYearEntry(message.getText());
                    telegramUserCache.setPosition(Position.INPUT_USER_FACULTY);
                    telegramUserCache.setStatementCache(statementCache);
                    telegramUserService.save(telegramUserCache);
                    sendMessageService.sendMessage(telegramId, "Виберіть ваш факультет", Keyboards.chooseFaculty());
                    log.debug("Оновлено позицію користувача на INPUT_USER_FACULTY");
                }
                case INPUT_USER_FACULTY -> {
                    log.info("Користувач з ID: {} вибирає факультет: {}", telegramId, message.getText());
                    statementCache.setFaculty(message.getText());
                    telegramUserCache.setPosition(Position.INPUT_USER_PHONE);
                    telegramUserCache.setStatementCache(statementCache);
                    telegramUserService.save(telegramUserCache);
                    sendMessageService.sendMessage(telegramId, "Введіть ваш номер телефону⤵", Keyboards.keyboardRemove());
                    sendMessageService.sendMessage(telegramId, "Нажміть, щоб поділитися контактом", Keyboards.phoneKeyboard());
                    log.debug("Оновлено позицію користувача на INPUT_USER_PHONE");
                }
                case INPUT_USER_PHONE -> {
                    if (message.hasContact()) {
                        log.info("Користувач з ID: {} поділився номером телефону", telegramId);
                        statementCache.setPhoneNumber(message.getContact().getPhoneNumber());
                        telegramUserCache.setPosition(Position.CONFIRMATION);
                        telegramUserCache.setStatementCache(statementCache);
                        telegramUserService.save(telegramUserCache);
                        sendMessageService.sendMessage(telegramId, statementCache.toString(), Keyboards.keyboardRemove());
                        sendMessageService.sendMessage(telegramId, "Нажміть, щоб підтвердити дані", Keyboards.confirmationKeyboard());
                        log.debug("Оновлено позицію користувача на CONFIRMATION");
                    } else {
                        log.warn("Користувач з ID: {} не надав контакт", telegramId);
                        sendMessageService.sendMessage(telegramId, "Нажміть кнопку, щоб поділитися контактом", Keyboards.phoneKeyboard());
                    }
                }
            }
        } else if (message.hasText()) {
            log.info("Отримано текстове повідомлення: {}", message.getText());
            switch (message.getText()) {
                case "/start" -> {
                    log.info("Користувач з ID: {} почав сесію з командою /start", telegramId);
                    sendMessageService.sendMessage(telegramId, Constants.START, Keyboards.starKeyboard());
                }
                case "/help" -> {
                    log.info("Користувач з ID: {} запросив допомогу з командою /help", telegramId);
                    sendMessageService.sendMessage(telegramId, Constants.HELP, Keyboards.helpMenu());
                }
                default -> {
                    log.warn("Користувач з ID: {} надіслав невідому команду: {}", telegramId, message.getText());
                }
            }
        }
    }
}
