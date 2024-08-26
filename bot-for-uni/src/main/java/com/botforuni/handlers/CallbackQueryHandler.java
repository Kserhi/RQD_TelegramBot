package com.botforuni.handlers;

import com.botforuni.Keybords.Keyboards;
import com.botforuni.domain.TelegramUser;
import com.botforuni.services.BotUserDataService;
import com.botforuni.services.SendMessageService;
import com.botforuni.cache.BotUserCache;
import com.botforuni.cache.Cache;
import com.botforuni.domain.BotUser;
import com.botforuni.services.StatementService;
import com.botforuni.services.TelegramUserService;
import com.botforuni.utils.Constants;
import com.botforuni.domain.Position;
import com.botforuni.utils.PositionInTelegramChat;
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

        TelegramUser telegramUser = TelegramUserService.get(message.getChatId());


        switch (callbackQuery.getData()) {
            case "/menu" -> sendMessageService.sendMessage(
                    message,
                    Constants.MENU,
                    Keyboards.menuKeyboard()
            );
            case "choose_statement" -> sendMessageService.sendMessage(
                    message,
                    Constants.CHOOSESTATEMENT,
                    Keyboards.chooseStatementKeyboard()
            );


            case "statements" -> {
                sendMessageService.sendMessage(
                        message,
                        Constants.STATEMENTS);

                sendMessageService.sendAllInfoAboutUserFromDataBasa(message);
            }


            case "statementForMilitaryOfficer" -> {
                StatementService.generateStatement(
                        telegramUser.getTelegramId(),
                        Constants.STATEMENTFORMILITARI);

                /// змінили позицію користувача

                telegramUser.setPosition(PositionInTelegramChat.INPUTUSERNAME);
                TelegramUserService.add(telegramUser);

                sendMessageService.sendMessage(message, "Введіть свій ПІБ:");


                //генерує користувача з меседжа  та записує в кеш
                cache.add(
                        BotUserCache.generateUserFromMessage(
                                message,
                                Constants.STATEMENTFORMILITARI));
            }
            case "statementForStudy" -> {
                Long idOfStatement=StatementService.generateStatement(
                        telegramUser.getTelegramId(),
                        Constants.STATEMENTFORSTUDY);

                /// змінили позицію користувача

                telegramUser.setPosition(PositionInTelegramChat.INPUTUSERNAME);
                telegramUser.setIdOfStatement(idOfStatement);
                TelegramUserService.add(telegramUser);


                sendMessageService.sendMessage(message, "Введіть своє повне імя");
                //генерує користувача з меседжа  та записує в кеш
                cache.add(
                        BotUserCache.generateUserFromMessage(
                                message,
                                Constants.STATEMENTFORSTUDY));
            }


            case "confirm" -> {
                user.setPosition(Position.NONE);
                sendMessageService.sendMessage(message, "Реєстрація пройшла успішно❗");
                BotUserDataService.putUserInDataBase(user);
                sendMessageService.sendMessage(message, "Ваша заявка⤵");
                sendMessageService.sendInfoAboutUserFromDataBasa(message, user.getStatement());
            }
            case "cancel" -> {
                sendMessageService.sendMessage(
                        message,
                        Constants.MENU,
                        Keyboards.menuKeyboard()
                );
                user.setPosition(Position.NONE);
            }
        }
    }
}

