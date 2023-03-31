package com.example.botforuni.messagesender;

import com.example.botforuni.MyFirstBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MessageSenderImpl implements MessageSender {
    private MyFirstBot myFirstBot;
    @Override
    public void sendMessage(SendMessage sendMessage) {
        try {
            myFirstBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    @Autowired
    public void setMyFirstBot(MyFirstBot myFirstBot) {
        this.myFirstBot = myFirstBot;
    }
}
