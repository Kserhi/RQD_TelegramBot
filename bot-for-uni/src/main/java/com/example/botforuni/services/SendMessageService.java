package com.example.botforuni.services;

import com.example.botforuni.Keybords.Keyboards;
import com.example.botforuni.cache.Cache;
import com.example.botforuni.database.UserData;
import com.example.botforuni.domain.BotUser;
import com.example.botforuni.messagesender.MessageSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class SendMessageService {
    private final MessageSender messageSender;
    private final UserData userData;


    public SendMessageService(MessageSender messageSender,UserData userData) {
        this.userData=userData;
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


        List<String> ss= userData.getUserInfoFomDataBasa(message.getChatId());
        String sss=ss.toString();



        messageSender.sendMessage(SendMessage.builder()
                .parseMode("HTML")
                .chatId(String.valueOf(message.getChatId()))
                                .text(sss)
//                .text("<b>ПІБ: </b> " + ss. + "\n" +
//                        "<b>Група: </b>" +  + "\n" +
//                        "<b>Рік набору: </b>" + + "\n" +
//                        "<b>Номер телефону: </b>"+

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
