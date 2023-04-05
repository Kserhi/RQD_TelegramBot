package com.example.botforuni.services;

import com.example.botforuni.Keybords.Keyboards;
import com.example.botforuni.cache.Cache;
import com.example.botforuni.domain.BotUser;
import com.example.botforuni.domain.Position;
import com.example.botforuni.messagesender.MessageSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class SendMessageService {
    private final MessageSender messageSender;

    private final Cache<BotUser> cache;

    public SendMessageService(MessageSender messageSender, Cache<BotUser> cache) {
        this.messageSender = messageSender;
        this.cache = cache;
    }



    public void sendStartMenu(Message message){

       SendMessage ms1= SendMessage.builder()
                .text("\uD83D\uDC4BПривіт! За допомогою цього чат-бота ви зможете зробити запит до деканату!")
                .replyMarkup(Keyboards.getKeyboard())
                .chatId(String.valueOf(message.getChatId()))
                .build();


        messageSender.sendMessage(ms1);

    }

    public void sendStartMenuDemo(Message message){

        SendMessage ms1= SendMessage.builder()
                .text("Обирайте з меню нижче ⤵️")
                .replyMarkup(Keyboards.getKeyboard())
                .chatId(String.valueOf(message.getChatId()))
                .build();


        messageSender.sendMessage(ms1);

    }

    public void sendMenu(Message message){

        SendMessage ms1= SendMessage.builder()
                .text("Виберіть необхідну послугу ⤵ ")
                .replyMarkup(Keyboards.menuKeyboard())
                .chatId(String.valueOf(message.getChatId()))
                .build();


        messageSender.sendMessage(ms1);

    }


    public void sendRegMenu(Message message){

        SendMessage ms1= SendMessage.builder()
                .text("Пройдіть реєстрацію")
                .replyMarkup(Keyboards.regKeyboard())
                .chatId(String.valueOf(message.getChatId()))
                .build();


        messageSender.sendMessage(ms1);

    }



    public void sendInfoAboutUser(Message message,BotUser user){

        messageSender.sendMessage(SendMessage.builder()
                .parseMode("HTML")
                .chatId(String.valueOf(user.getId()))
                .text("<b>ПІБ: </b> " + user.getFullName() + "\n" +
                        "<b>Група: </b>" + user.getGroup() + "\n" +
                        "<b>Рік набору: </b>" + user.getYear())
                .build());

    }

    public void sendMessage(Message message,String text){
        SendMessage ms1= SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(message.getChatId()))
                .build();


        messageSender.sendMessage(ms1);
    }



}
