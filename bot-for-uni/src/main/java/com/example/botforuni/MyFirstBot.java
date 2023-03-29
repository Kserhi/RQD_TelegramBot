package com.example.botforuni;

import com.example.botforuni.Keybords.Keyboards;
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

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

//        ReplyKeyboardMarkup replyKeyboardMarkup = Keyboards.getKeyboard();
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);


        if (update.hasMessage()) {//перевірка чи є в апдейту повідомлення так бжола казав
            Message message = update.getMessage();//шоб кожен раз через абдейт нетягнути
            String textFromUser = message.getText();//


            switch (textFromUser) {//порівнюєм текст від юзера з командами
                case "/start":
                    //сетчу повідомлення
                    sendMessage.setText("Привіт! За допомогою цього чат-бота ви зможете зробити запит до деканату!");
                    sendMessage.setReplyMarkup(Keyboards.getKeyboard());//передаю клаву
                    sendMessage.setChatId(String.valueOf(message.getChatId()));
                    break;
                case "❗Потрібна послуга деканату":
                    sendMessage.setText("Вибери те шо треба");
                    sendMessage.setReplyMarkup(Keyboards.menuKeyboard());
                    sendMessage.setChatId(String.valueOf(message.getChatId()));
                    break;
                case "Тіпа на головну"://костиль
                    sendMessage.setText("Привіт! За допомогою цього чат-бота ви зможете зробити запит до деканату!");
                    sendMessage.setReplyMarkup(Keyboards.getKeyboard());
                    sendMessage.setChatId(String.valueOf(message.getChatId()));
                    break;


            }


        }





        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);

        }

    }


}
