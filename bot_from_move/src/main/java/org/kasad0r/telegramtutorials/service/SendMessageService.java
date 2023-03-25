package org.kasad0r.telegramtutorials.service;

import org.kasad0r.telegramtutorials.messagesender.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

@Service
public class SendMessageService {

    private final MessageSender messageSender;

    public SendMessageService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void jastMesege(Message message, String text){
       var ms1= SendMessage.builder()
                .text(text)
                .chatId(message.getChatId().toString())
                .build();
        messageSender.sendMessage(ms1);
    }



    public void menu(Message message){
        var markup=new ReplyKeyboardMarkup();
        var keyboardRows=new ArrayList<KeyboardRow>();


        KeyboardRow row1=new KeyboardRow();
        KeyboardRow row2=new KeyboardRow();

        row1.add("Створити заявку");
        row1.add("Шось іще");
        row2.add("Головна");

        keyboardRows.add(row1);
        keyboardRows.add(row2);

        markup.setKeyboard(keyboardRows);

        markup.setResizeKeyboard(true);



        SendMessage sendMessage=new SendMessage();
        sendMessage.setText("Меню");
        sendMessage.setChatId(message.getChatId().toString());

        sendMessage.setReplyMarkup(markup);

        messageSender.sendMessage(sendMessage);






    }
}



