package com.example.botforuni.handlers;

import com.example.botforuni.Keybords.Keyboards;
import com.example.botforuni.cache.BotUserCache;
import com.example.botforuni.cache.Cache;
import com.example.botforuni.domain.BotUser;
import com.example.botforuni.utils.Constans;
import com.example.botforuni.domain.Position;
import com.example.botforuni.services.BotUserDataService;
import com.example.botforuni.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

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
        Message message = callbackQuery.getMessage();
        BotUser user = cache.findBy(message.getChatId());
        switch (callbackQuery.getData()) {
            case "/menu":
                sendMessageService.sendMessage(
                        message,
                        Constans.MENU,
                        Keyboards.menuKeyboard()
                );
                break;
            case "choose_statement":
                sendMessageService.sendMessage(
                        message,
                        Constans.CHOOSESTATEMENT,
                        Keyboards.chooseStatementKeyboard()
                );
                break;
            case "statements":
                sendMessageService.sendMessage(
                        message,
                        Constans.STATEMENTS);

                sendMessageService.sendAllInfoAboutUserFromDataBasa(message);

                break;

            case "statementForMilitaryOfficer":
                sendMessageService.sendMessage(message, "Введіть своє повне імя");
                //генерує користувача з меседжа  та записує в кеш
                cache.add(
                        BotUserCache.generateUserFromMessage(
                                message,
                                BotUser.STATEMENTFORMILITARI));

                break;

            case "statementForStudy":


                sendMessageService.sendMessage(message, "Введіть своє повне імя");
                //генерує користувача з меседжа  та записує в кеш
                cache.add(
                        BotUserCache.generateUserFromMessage(
                                message,
                                BotUser.STATEMENTFORSTUDY));

                break;

            case "confirm":
                user.setPosition(Position.NONE);
                sendMessageService.sendMessage(message, "Реєстрація пройшла успішно❗");
                BotUserDataService.putUserInDataBase(user);
                sendMessageService.sendMessage(message, "Ваша заявка⤵");
                sendMessageService.sendInfoAboutUserFromDataBasa(message,user.getStatement());
                break;

            case "cancel":
                sendMessageService.sendMessage(
                        message,
                        Constans.MENU,
                        Keyboards.menuKeyboard()
                );
                user.setPosition(Position.NONE);
                break;


        }
    }
}

