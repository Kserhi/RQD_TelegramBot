package com.example.botforuni.services;

import com.example.botforuni.Keybords.Keyboards;
import com.example.botforuni.jdbc.UserData;
import com.example.botforuni.domain.BotUser;
import com.example.botforuni.messagesender.MessageSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

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



    public void sendInfoAboutUserFromDataBasa(Message message){
        List<String> infoInLsit= UserData.getUserInfoFomDataBasa(message.getChatId());
//        String info=infoInLsit.toString();
        String name = infoInLsit.get(0);
        String group = infoInLsit.get(4);
        String year = infoInLsit.get(1);
        String phone = infoInLsit.get(3);
        String stat = infoInLsit.get(2);
        messageSender.sendMessage(SendMessage.builder()
                .parseMode("HTML")
                .chatId(String.valueOf(message.getChatId()))
                .text("<b>ПІБ: </b> " + name + "\n" +
                        "<b>Група: </b>" + group + "\n" +
                        "<b>Рік набору: </b>" + year + "\n" +
                        "<b>Номер телефону: </b>" + phone + "\n" +
                        "<b>Тип заявки: </b>" + stat)
                .build());
    }
    public void sendInfoAboutUserForomCache(Message message,BotUser user){
        messageSender.sendMessage(SendMessage.builder()
                                .parseMode("HTML")
                                .chatId(String.valueOf(user.getId()))
                                .text("<b>ПІБ: </b> " + user.getFullName() + "\n" +
                                        "<b>Група: </b>" + user.getGroupe()+ "\n" +
                                        "<b>Рік набору: </b>" + user.getYearEntry()+ "\n" +
                                        "<b>Номер телефону: </b>" + user.getPhoneNumber())

                .build());

    }


    public void sendMessage(Message message,String text){
        SendMessage ms1= SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(message.getChatId()))
                .build();


        messageSender.sendMessage(ms1);
    }
    public void sendConfirmationMenu(Message message){

        SendMessage ms1= SendMessage.builder()
                .text("Нажміть, щоб підтвердити дані")
                .chatId(String.valueOf(message.getChatId()))
                .replyMarkup(Keyboards.confirmationKeyboard())
                .build();
        messageSender.sendMessage(ms1);
    }

    public void phoneNum(Message message){
        SendMessage ms1= SendMessage.builder()
                .text("Нажміть, щоб поділитися контактом")
                .chatId(String.valueOf(message.getChatId()))
                .replyMarkup(Keyboards.phoneKeyboard())
                .build();
        messageSender.sendMessage(ms1);
    }


}
