package com.example.botforuni;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



@Component
public class MyFirstBot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "ldubgdDekanat_bot";
    }

    @Override
    public String getBotToken() {
        return "6139727723:AAGhYLSHJaIzSF0yDyps1b3d14PLB3oXQnI";
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("Повідомлення отримано: " + update.getMessage().getText());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Привіт користувач, я отримав твоє повідомлення: " + update.getMessage().getText());
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
