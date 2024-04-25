package com.botforuni.handlers;

import com.botforuni.Keybords.Keyboards;
import com.botforuni.services.BotUserDataService;
import com.botforuni.services.SendMessageService;
import com.botforuni.cache.BotUserCache;
import com.botforuni.cache.Cache;
import com.botforuni.domain.BotUser;
import com.botforuni.utils.Constans;
import com.botforuni.domain.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CallbackQueryHandler implements Handler<CallbackQuery> {

    @Autowired
    private SendMessageService sendMessageService;
    private final Cache<BotUser> cache;




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
                                Constans.STATEMENTFORMILITARI));

                break;

            case "statementForStudy":


                sendMessageService.sendMessage(message, "Введіть своє повне імя");
                //генерує користувача з меседжа  та записує в кеш
                cache.add(
                        BotUserCache.generateUserFromMessage(
                                message,
                                Constans.STATEMENTFORSTUDY));

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

