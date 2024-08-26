package com.botforuni.handlers;

import com.botforuni.Keybords.Keyboards;
import com.botforuni.domain.TelegramUser;
import com.botforuni.services.SendMessageService;
import com.botforuni.services.StatementService;
import com.botforuni.services.TelegramUserService;
import com.botforuni.utils.Constants;
import com.botforuni.domain.PositionInTelegramChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CallbackQueryHandler implements Handler<CallbackQuery> {

    @Autowired
    private SendMessageService sendMessageService;


    @Override
    public void choose(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();

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

//                sendMessageService.sendAllInfoAboutUserFromDataBasa(message);
            }


            case "statementForMilitaryOfficer" -> {
                Long idOfStatement=StatementService.generateStatement(
                        telegramUser.getTelegramId(),
                        Constants.STATEMENTFORMILITARI);

                /// змінили позицію користувача

                telegramUser.setPosition(PositionInTelegramChat.INPUTUSERNAME);
                telegramUser.setIdOfStatement(idOfStatement);
                TelegramUserService.save(telegramUser);


                sendMessageService.sendMessage(message, "Введіть свій ПІБ:");

            }
            case "statementForStudy" -> {
                Long idOfStatement=StatementService.generateStatement(
                        telegramUser.getTelegramId(),
                        Constants.STATEMENTFORSTUDY);

                /// змінили позицію користувача

                telegramUser.setPosition(PositionInTelegramChat.INPUTUSERNAME);
                telegramUser.setIdOfStatement(idOfStatement);
                TelegramUserService.save(telegramUser);

                sendMessageService.sendMessage(message, "Введіть свій ПІБ:");

            }


            case "confirm" -> {
                telegramUser.setPosition(PositionInTelegramChat.NONE);
                telegramUser.setIdOfStatement((long) -1);
                TelegramUserService.save(telegramUser);
                sendMessageService.sendMessage(message, "Реєстрація пройшла успішно❗");
                sendMessageService.sendMessage(
                        message,
                        Constants.MENU,
                        Keyboards.menuKeyboard()
                );

            }



            case "cancel" -> {
                if (PositionInTelegramChat.CONFIRMATION.equals(telegramUser.getPosition())){
                    StatementService.deleteById(telegramUser.getIdOfStatement());
                    telegramUser.setPosition(PositionInTelegramChat.NONE);
                    telegramUser.setIdOfStatement((long) -1);
                    TelegramUserService.save(telegramUser);


                    sendMessageService.sendMessage(
                            message,
                            Constants.MENU,
                            Keyboards.menuKeyboard()
                    );

                }

            }
        }
    }
}

