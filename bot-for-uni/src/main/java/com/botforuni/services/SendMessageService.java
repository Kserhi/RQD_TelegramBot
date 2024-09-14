package com.botforuni.services;

import com.botforuni.domain.TelegramUserCache;
import com.botforuni.keybords.Keyboards;
import com.botforuni.domain.Statement;
import com.botforuni.messageSender.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.Optional;

@Slf4j
@Service
public class SendMessageService {
    private final MessageSender messageSender;
    private final TelegramUserService telegramUserService;

    @Autowired
    public SendMessageService(MessageSender messageSender, TelegramUserService telegramUserService) {
        this.messageSender = messageSender;
        this.telegramUserService = telegramUserService;
    }



    public void sendMessage(Long chatId, String text) {
        log.info("Відправка простого повідомлення до чату з ID: {}", chatId);
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(chatId))
                .build();
        messageSender.sendMessage(message);
    }

    public void sendMessage(Long chatId, String text, InlineKeyboardMarkup inlineKeyboard) {
        log.info("Відправка повідомлення з інлайн клавіатурою до чату з ID: {}", chatId);
        removePreviousKeyboard(chatId);
        Integer messageId = sendTextMessage(chatId, text, inlineKeyboard);
        telegramUserService.saveMassageId(chatId, messageId);
    }

    private Integer sendTextMessage(Long chatId, String text, InlineKeyboardMarkup inlineKeyboard) {
        log.info("Створення повідомлення з текстом для чату з ID: {}", chatId);
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(chatId))
                .replyMarkup(inlineKeyboard)
                .build();
        return messageSender.sendMessage(message);
    }

    private void removePreviousKeyboard(Long chatId) {
        Optional<TelegramUserCache> telegramUserCacheOptional = telegramUserService.findById(chatId);
        telegramUserCacheOptional.ifPresent(telegramUserCache -> {
            if (telegramUserCache.getMassageId() != null) {
                log.info("Видалення попередньої клавіатури для повідомлення з ID: {} у чаті з ID: {}", telegramUserCache.getMassageId(), chatId);
                deleteInlineKeyboard(chatId, telegramUserCache.getMassageId());
            }
        });
    }

    private void deleteInlineKeyboard(Long chatId, Integer messageId) {
        log.info("Видалення інлайн клавіатури для повідомлення з ID: {} у чаті з ID: {}", messageId, chatId);
        EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
                .chatId(String.valueOf(chatId))
                .messageId(messageId)
                .replyMarkup(null)
                .build();
        messageSender.sendMessage(editMessageReplyMarkup);
    }

    public void sendMessage(Long tgId, String text, ReplyKeyboardMarkup replyKeyboard) {
        log.info("Відправка повідомлення з клавіатурою відповіді до чату з ID: {}", tgId);
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(tgId))
                .replyMarkup(replyKeyboard)
                .build();
        messageSender.sendMessage(message);
    }

    public void sendInfoAboutReadyStatement(Statement statement) {
        log.info("Відправка інформації про готову довідку для користувача з ID: {}", statement.getTelegramId());
        sendMessage(
                statement.getTelegramId(),
                formatStatement(statement),
                Keyboards.linkToMenuKeyboard());
    }

    private String formatStatement(Statement statement) {
        return new StringBuilder()
                .append("📄 Ваша довідка готова:\n\n")
                .append(statement.toString())
                .toString();
    }

    public void sendMessage(Long chatId, String text, ReplyKeyboardRemove replyKeyboardRemove) {
        log.info("Відправка повідомлення з видаленням клавіатури replyKeyboard до чату з ID: {}", chatId);
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(chatId))
                .replyMarkup(replyKeyboardRemove)
                .build();
        messageSender.sendMessage(message);
    }
}
