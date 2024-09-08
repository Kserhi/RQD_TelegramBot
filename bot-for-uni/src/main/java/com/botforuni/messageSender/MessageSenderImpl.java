package com.botforuni.messageSender;

import com.botforuni.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class MessageSenderImpl implements MessageSender {
    private TelegramBot telegramBot;



    @Override
    public Integer sendMessage(SendMessage sendMessage) {
        try {

          return telegramBot.execute(sendMessage).getMessageId();

        } catch (TelegramApiException e) {
            //// може бути помилка інтернету і повідомлення не надіслеться
            throw new RuntimeException(e);

        }
    }

    public void sendMessage(EditMessageReplyMarkup editMessageReplyMarkup){
        try {
            telegramBot.execute(editMessageReplyMarkup);

        } catch (TelegramApiException e) {
            //// може бути помилка інтернету і повідомлення не надіслеться
            throw new RuntimeException(e);

        }
    }




    @Autowired
    public void setTelegramBot(@Lazy TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }
}
