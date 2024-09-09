package com.botforuni.services;

import com.botforuni.domain.TelegramUserCache;
import com.botforuni.keybords.Keyboards;
import com.botforuni.domain.Statement;
import com.botforuni.messageSender.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.Optional;

@Service
public class SendMessageService {
    private final MessageSender messageSender;
    @Autowired
    private TelegramUserService telegramUserService;

    public SendMessageService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }


    /**
     * Надсилає просте текстове повідомлення до чату з вказаним текстом.
     *
     * @param chatId Телеграм id для надсиланняи повідомлення до користувача .
     * @param text   Текст повідомлення для надсилання.
     */
    public void sendMessage(Long chatId, String text) {
        // Створення об'єкту SendMessage для надсилання текстового повідомлення
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(chatId))
                .build();
        messageSender.sendMessage(message);
    }






    /**
     * Надсилає текстове повідомлення до чату з можливістю додати інлайн клавіатуру.
     *
     * @param chatId         Телеграм id для надсиланняи повідомлення до користувача .
     * @param text           Текст повідомлення для надсилання.
     * @param inlineKeyboard Об'єкт з інлайн клавіатурою (кнопки для взаємодії).
     */
    public void sendMessage(Long chatId, String text, InlineKeyboardMarkup inlineKeyboard) {
        // Спочатку видаляємо попередню клавіатуру
        removePreviousKeyboard(chatId);

        // Створюємо та відправляємо нове повідомлення
        Integer messageId = sendTextMessage(chatId, text, inlineKeyboard);

        // Зберігаємо новий messageId для подальшого видалення клавіатури
        telegramUserService.saveMassageId(chatId,messageId);
    }

    private Integer sendTextMessage(Long chatId, String text, InlineKeyboardMarkup inlineKeyboard) {
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(chatId))
                .replyMarkup(inlineKeyboard)
                .build();

        // Відправляємо повідомлення і повертаємо його ID
        return messageSender.sendMessage(message);
    }

    private void removePreviousKeyboard(Long chatId) {
        Optional<TelegramUserCache> telegramUserCacheOptional = telegramUserService.findById(chatId);

        if (telegramUserCacheOptional.isPresent()) {
            TelegramUserCache telegramUserCache = telegramUserCacheOptional.get();
            if (telegramUserCache.getMassageId() != null) {
                deleteInlineKeyboard(chatId, telegramUserCache.getMassageId());
            }
        }
    }

    private void deleteInlineKeyboard(Long chatId, Integer messageId) {
        EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
                .chatId(String.valueOf(chatId))
                .messageId(messageId)  // Вказуємо ID повідомлення, яке редагуємо
                .replyMarkup(null)     // Видаляємо клавіатуру, встановивши replyMarkup як null
                .build();

        // Надсилаємо запит на редагування повідомлення для видалення клавіатури
        messageSender.sendMessage(editMessageReplyMarkup);
    }

    public void sendMessage(Long tgId, String text, ReplyKeyboardMarkup replyKeyboard) {

        // Створення об'єкту SendMessage для надсилання текстового повідомлення з клавіатурою відповіді
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(tgId))
                .replyMarkup(replyKeyboard)
                .build();
        // Надсилання повідомлення за допомогою messageSender.sendMessage()
        messageSender.sendMessage(message);
    }


    public void sendInfoAboutReadyStatement(Statement statement) {
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

    public void sendMessage(Long chatId,String text, ReplyKeyboardRemove replyKeyboardRemove){
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(chatId))
                .replyMarkup(replyKeyboardRemove)
                .build();
        messageSender.sendMessage(message);
    }







}