package com.example.botforuni;

import com.example.botforuni.Keybords.Keyboards;
import com.example.botforuni.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


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
                        sendMessage.setText("Виберіть необхідну послугу");
                        sendMessage.setReplyMarkup(Keyboards.menuKeyboard());
                        sendMessage.setChatId(String.valueOf(message.getChatId()));
                        break;

                    case "Створити довідку з місця навчання":

                        sendMessage.setText("Пройдіть реєстрацію");
                        sendMessage.setReplyMarkup(Keyboards.regKeyboard());
                        sendMessage.setChatId(String.valueOf(message.getChatId()));
                        break;
                    case "Реєстрація":
                        sendMessage.setText("Ведіть ПІБ");
                        sendMessage.setChatId(String.valueOf(message.getChatId()));
                        break;
                }


            }
        }
    }


}

    @Autowired
    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }
}
