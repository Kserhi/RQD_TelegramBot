package com.example.botforuni.services;

import com.example.botforuni.Keybords.Keyboards;
import com.example.botforuni.cache.Cache;
import com.example.botforuni.domain.BotUser;
import com.example.botforuni.messagesender.MessageSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SendMessageService {
    private final MessageSender messageSender;



    public SendMessageService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }



    public void sendStartMenu(Message message){

       SendMessage ms1= SendMessage.builder()
                .text("\uD83D\uDC4BПривіт! За допомогою цього чат-бота ви зможете зробити запит до деканату!")
                .replyMarkup(Keyboards.starKeyboard())
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
                        "<b>Група: </b>" + user.getGroupe() + "\n" +
                        "<b>Рік набору: </b>" + user.getYearEntry())
                .build());

    }

    public void sendMessage(Message message,String text){
        SendMessage ms1= SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(message.getChatId()))
                .build();


        messageSender.sendMessage(ms1);
    }
    public void sndConfirmationMenu(Message message){

        SendMessage ms1= SendMessage.builder()
                .text("Нажміть, щоб підтвердити дані")
                .chatId(String.valueOf(message.getChatId()))
                .replyMarkup(Keyboards.confirmationKeyboard())
                .build();
        messageSender.sendMessage(ms1);
    }



}
