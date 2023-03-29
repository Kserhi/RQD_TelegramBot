package com.example.botforuni;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.*;
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
        ReplyKeyboardMarkup replyKeyboardMarkup = getKeyboard();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        if (update.getMessage().getText().equals("/start")){
            sendMessage.setText("\uD83D\uDC4BПривіт! За допомогою цього чат-бота ви зможете зробити запит до деканату!");
            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    private ReplyKeyboardMarkup getKeyboard(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("❗Потрібна послуга деканату");
        keyboardRows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;

    }
}
