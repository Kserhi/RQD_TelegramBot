package com.example.botforuni.handlers;

import com.example.botforuni.messagesender.MessageSender;
import com.example.botforuni.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

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
        Message message =callbackQuery.getMessage();
        switch (callbackQuery.getData()){
            case "/menu":
                sendMessageService.sendMenu(message);
                break;
            case "choose_statement":
                sendMessageService.sendMessage(message,"вдало вибір");
                break;
            case "statements":
                sendMessageService.sendMessage(message,"Ваші довідки:");

                break;

        }

        }
    }

