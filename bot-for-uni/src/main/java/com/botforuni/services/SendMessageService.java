package com.botforuni.services;

import com.botforuni.keybords.Keyboards;
import com.botforuni.domain.Statement;
import com.botforuni.messageSender.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Service
public class SendMessageService {
    private final MessageSender messageSender;

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
        // Створення об'єкту SendMessage для надсилання текстового повідомлення
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(chatId))
                .replyMarkup(inlineKeyboard)
                .build();

        messageSender.sendMessage(message);
    }

    /**
     * Надсилає просте текстове повідомлення до чату з вказаним текстом.
     *
     * @param messageFromUser Об'єкт, що представляє отримане повідомлення від користувача.
     * @param text    Текст повідомлення для надсилання.
     */
    public void sendMessage(Message messageFromUser, String text) {
        sendMessage(messageFromUser.getChatId(),text);
    }

    /**
     * Надсилає текстове повідомлення до чату з можливістю додати інлайн клавіатуру.
     *
     * @param messageFromUser Об'єкт, що представляє отримане повідомлення від користувача.
     * @param text            Текст повідомлення для надсилання.
     * @param inlineKeyboard  Об'єкт з інлайн клавіатурою (кнопки для взаємодії).
     */
    public void sendMessage(Message messageFromUser, String text, InlineKeyboardMarkup inlineKeyboard) {
        // Створення об'єкту SendMessage для надсилання текстового повідомлення з інлайн клавіатурою
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(messageFromUser.getChatId()))
                .replyMarkup(inlineKeyboard)
                .build();


        // Надсилання повідомлення за допомогою messageSender.sendMessage()
        messageSender.sendMessage(message);
    }

    /**
     * Надсилає текстове повідомлення до чату з клавіатурою відповіді.
     *
     * @param messageFromUser Об'єкт, що представляє отримане повідомлення від користувача.
     * @param text            Текст повідомлення для надсилання.
     * @param replyKeyboard   Об'єкт з клавіатурою відповіді (звичайна клавіатура з кнопками).
     */
    public void sendMessage(Message messageFromUser, String text, ReplyKeyboardMarkup replyKeyboard) {

        // Створення об'єкту SendMessage для надсилання текстового повідомлення з клавіатурою відповіді
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(messageFromUser.getChatId()))
                .replyMarkup(replyKeyboard)
                .build();
        // Надсилання повідомлення за допомогою messageSender.sendMessage()
        messageSender.sendMessage(message);
    }


    public void sendInfoAboutReadyStatement(Statement statement) {
        String formattedMessage = formatStatement(statement);
        sendMessage(statement.getTelegramId(), formattedMessage, Keyboards.linkToMenuKeyboard());
    }

    private String formatStatement(Statement statement) {
        return new StringBuilder()
                .append("📄 Ваша довідка готова:\n\n")
                .append(statement.toString())
                .toString();

    }

    public void sendMessage(Message messageFromUser,String text, ReplyKeyboardRemove replyKeyboardRemove){
        // Створення об'єкту SendMessage для надсилання текстового повідомлення з клавіатурою відповіді
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(messageFromUser.getChatId()))
                .replyMarkup(replyKeyboardRemove)
                .build();
        // Надсилання повідомлення за допомогою messageSender.sendMessage()
        messageSender.sendMessage(message);
    }



}