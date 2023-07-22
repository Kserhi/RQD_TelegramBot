package com.example.botforuni.handlers;

import com.example.botforuni.cache.BotUserCache;
import com.example.botforuni.cache.Cache;
import com.example.botforuni.domain.BotUser;
import com.example.botforuni.services.BotUserDataService;
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
    private final Cache<BotUser> cache;



    @Autowired
    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }


    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public CallbackQueryHandler(MessageSender messageSender,Cache<BotUser> cache) {
        this.messageSender = messageSender;
        this.cache = cache;
    }

    @Override
    public void choose(CallbackQuery callbackQuery) {
        Message message =callbackQuery.getMessage();
        switch (callbackQuery.getData()){
            case "/menu":
                sendMessageService.sendMenu(message);
                break;
            case "choose_statement":
                sendMessageService.choose_statement(message);

                break;
            case "statements":
                sendMessageService.sendMessage(message,"Ваші довідки:");
                sendMessageService.sendAllInfoAboutUserFromDataBasa(message);
                break;

            case "statementForMilitaryOfficer":
                sendMessageService.sendMessage(message,"Введіть своє повне імя");
                cache.add(BotUserCache.generateUserFromMessage(message));
                cache.findBy(message.getChatId()).setStatement(BotUserDataService.STATEMENTFORMILITARI);

                break;

            case "statementForStudy":
                sendMessageService.sendMessage(message,"Введіть своє повне імя");
                cache.add(BotUserCache.generateUserFromMessage(message));
                cache.findBy(message.getChatId()).setStatement(BotUserDataService.STATEMENTFORSTUDY);

                break;
        }

        }
    }

