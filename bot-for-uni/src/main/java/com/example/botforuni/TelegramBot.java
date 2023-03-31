package com.example.botforuni;

import com.example.botforuni.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class TelegramBot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "ldubgdDekanat_bot";
    }

    @Override
    public String getBotToken() {
        return "6139727723:AAGhYLSHJaIzSF0yDyps1b3d14PLB3oXQnI";
    }

    private SendMessageService sendMessageService;

    @Override
    public void onUpdateReceived(Update update) {


        if (update.hasMessage()) {
            Message message = update.getMessage();//шоб кожен раз через абдейт нетягнути

            if (message.hasText()) {
                String textFromUser = message.getText();

                switch (textFromUser) {//порівнюєм текст від юзера з командами
                    case "/start":
                    case "Тіпа на головну":
                        sendMessageService.sendStartMenu(message);
                        break;
                    case "❗Потрібна послуга деканату":
                    case "Скасувати":
                        sendMessageService.sendMenu(message);
                        break;

                    case "Створити довідку з місця навчання":
                        sendMessageService.sendRegMenu(message);

                        break;
                    case "Реєстрація":
                        sendMessageService.rer(message);
                        break;
                }


            }
        }
    }




    @Autowired
    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }
}
