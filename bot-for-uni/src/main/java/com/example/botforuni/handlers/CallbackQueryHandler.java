package com.example.botforuni.handlers;

import com.example.botforuni.messagesender.MessageSender;
import com.example.botforuni.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class CallbackQueryHandler implements Handler<CallbackQuery> {

    private MessageSender messageSender;
    private SendMessageService sendMessageService;

    @Autowired
    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public CallbackQueryHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void choose(CallbackQuery callbackQuery) {
        if (callbackQuery.getData().equals("/menu")) {
            SendMessage sendMessage=SendMessage.builder()
                    .text("Вдало")
                    .chatId(String.valueOf(callbackQuery.getMessage().getChatId())).build();
            messageSender.sendMessage(sendMessage);
        }
    }
}
