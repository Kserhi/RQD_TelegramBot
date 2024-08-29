package com.botforuni.handlers;

import com.botforuni.Keybords.Keyboards;
import com.botforuni.domain.Position;
import com.botforuni.domain.TelegramUser;
import com.botforuni.services.SendMessageService;
import com.botforuni.services.StatementService;
import com.botforuni.services.TelegramUserService;
import com.botforuni.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CallbackQueryHandler implements Handler<CallbackQuery> {
    @Autowired
    private TelegramUserService telegramUserService;
    @Autowired
    private SendMessageService sendMessageService;


    @Override
    public void choose(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();

        TelegramUser telegramUser = telegramUserService.getOrGenerate(message.getChatId());


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
                if (StatementService.userHaveStatement(telegramUser.getTelegramId())){
                    sendMessageService.sendMessage(
                            message,
                            Constants.STATEMENTS);

                    StatementService.getAllUserStatements(telegramUser.getTelegramId()).forEach(
                            statement -> sendMessageService.sendMessage(message,statement.toString())
                    );
                }else {
                    sendMessageService.sendMessage(
                            message,
                            "У вас немає жодної зареєстрованої заявки");
                }

                sendMessageService.sendMessage(
                        message,
                        Constants.MENU,
                        Keyboards.menuKeyboard());



            }


            case "statementForMilitaryOfficer" -> {
                Long idOfStatement=StatementService.generateStatement(
                        telegramUser.getTelegramId(),
                        Constants.STATEMENTFORMILITARI);

                /// змінили позицію користувача

                telegramUser.setPosition(Position.INPUT_USER_NAME);
                telegramUser.setIdOfStatement(idOfStatement);
                telegramUserService.save(telegramUser);


                sendMessageService.sendMessage(message, "Введіть свій ПІБ:");

            }
            case "statementForStudy" -> {
                Long idOfStatement=StatementService.generateStatement(
                        telegramUser.getTelegramId(),
                        Constants.STATEMENTFORSTUDY);

                /// змінили позицію користувача

                telegramUser.setPosition(Position.INPUT_USER_NAME);
                telegramUser.setIdOfStatement(idOfStatement);
                telegramUserService.save(telegramUser);

                sendMessageService.sendMessage(message, "Введіть свій ПІБ:");

            }


            case "confirm" -> {
                telegramUser.setPosition(Position.NONE);
                telegramUser.setIdOfStatement((long) -1);
                telegramUserService.save(telegramUser);
                sendMessageService.sendMessage(message, "Реєстрація пройшла успішно❗");
                sendMessageService.sendMessage(
                        message,
                        Constants.MENU,
                        Keyboards.menuKeyboard()
                );

            }



            case "cancel" -> {
                if (Position.CONFIRMATION==telegramUser.getPosition()){
                    StatementService.deleteById(telegramUser.getIdOfStatement());
                    telegramUser.setPosition(Position.NONE);
                    telegramUser.setIdOfStatement((long) -1);
                    telegramUserService.save(telegramUser);


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

