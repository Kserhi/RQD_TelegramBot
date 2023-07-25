package com.example.botforuni.handlers;

import com.example.botforuni.Keybords.Keyboards;
import com.example.botforuni.cache.BotUserCache;
import com.example.botforuni.cache.Cache;
import com.example.botforuni.domain.BotUser;
import com.example.botforuni.services.BotUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import com.example.botforuni.services.SendMessageService;
@Component
public class CallbackQueryHandler implements Handler<CallbackQuery> {


    private SendMessageService sendMessageService;
    private final Cache<BotUser> cache;



    @Autowired
    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }


    public CallbackQueryHandler(Cache<BotUser> cache) {
        this.cache = cache;
    }

    @Override
    public void choose(CallbackQuery callbackQuery) {
        Message message =callbackQuery.getMessage();
        switch (callbackQuery.getData()){
            case "/menu":
                sendMessageService.sendMessage(
                        message,
                        "Виберіть необхідну послугу ⤵ ",
                        Keyboards.menuKeyboard()
                );
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
                //генерує користувача з меседжа  та записує в кеш
                cache.add(
                        BotUserCache.generateUserFromMessage(
                                message,
                                BotUserDataService.STATEMENTFORMILITARI));

                break;

            case "statementForStudy":

//                sendMessageService.sendMessage(message,"Реєстрація студента");
//                sendMessageService.sendRegMenu(message);

                sendMessageService.sendMessage(message,"Введіть своє повне імя");
                //генерує користувача з меседжа  та записує в кеш
                cache.add(
                        BotUserCache.generateUserFromMessage(
                                message,
                                BotUserDataService.STATEMENTFORSTUDY));

                break;
        }

        }
    }

