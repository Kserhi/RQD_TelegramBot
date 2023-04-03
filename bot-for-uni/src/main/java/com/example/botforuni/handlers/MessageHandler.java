package com.example.botforuni.handlers;

import com.example.botforuni.Keybords.Keyboards;
import com.example.botforuni.messagesender.MessageSender;
import com.example.botforuni.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler implements Handler<Message>{

    private final MessageSender messageSender;

    private SendMessageService sendMessageService;

    @Autowired
    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    public MessageHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void choose(Message message) {
        if (message.hasText()){
            if (message.getText().equals("/start")){
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(message.getChatId()));
                sendMessageService.sendStartMenu(message);

            }
        }

    }
}
