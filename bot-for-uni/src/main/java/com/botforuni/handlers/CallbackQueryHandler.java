package com.botforuni.handlers;

import com.botforuni.keybords.Keyboards;
import com.botforuni.domain.*;
import com.botforuni.services.*;
import com.botforuni.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class CallbackQueryHandler implements Handler<CallbackQuery> {
    @Autowired
    private TelegramUserService telegramUserService;
    @Autowired
    private StatementService statementService;
    @Autowired
    private StatementCacheService statementCacheService;
    @Autowired
    private SendMessageService sendMessageService;
    @Autowired
    private StatementInfoService statementInfoService;


    @Override
    public void choose(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();

        TelegramUserCache telegramUserCache = telegramUserService.getOrGenerate(message.getChatId());


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
                List<Statement> statements = statementService.getAllUserStatements(telegramUserCache.getTelegramId());

                if (statements.isEmpty()) {
                    sendMessageService.sendMessage(message, "У вас немає жодної зареєстрованої заявки", Keyboards.linkToMenuKeyboard());
                } else {
                    Statement lastStatement = statements.remove(0);
                    statements.forEach(statement -> sendMessageService.sendMessage(message, statement.toString()));
                    sendMessageService.sendMessage(message, lastStatement.toString(), Keyboards.linkToMenuKeyboard());
                }

            }
            case "statementForMilitaryOfficer" -> {
                StatementCache statementCache= statementCacheService
                        .generateStatement(
                                telegramUserCache,
                                Constants.STATEMENTFORMILITARI);

                telegramUserCache.setPosition(Position.INPUT_USER_NAME);
                telegramUserCache.setStatementCache(statementCache);
                telegramUserService.save(telegramUserCache);



                sendMessageService.sendMessage(message, "Введіть свій ПІБ:");

            }
            case "statementForStudy" -> {
                StatementCache statementCache= statementCacheService
                        .generateStatement(
                                telegramUserCache,
                                Constants.STATEMENTFORSTUDY);

                telegramUserCache.setPosition(Position.INPUT_USER_NAME);
                telegramUserCache.setStatementCache(statementCache);
                telegramUserService.save(telegramUserCache);

                sendMessageService.sendMessage(message, "Введіть свій ПІБ:");

            }


            case "confirm" -> {
                Statement statement=statementService.mapStatement(telegramUserCache.getStatementCache());

                StatementInfo statementInfo=statementInfoService.generate(statement);

                statement.setStatementInfo(statementInfo);

                statementService.save(statement);




                statementCacheService.removeAll(telegramUserCache.getTelegramId());


                telegramUserCache.setStatementCache(null);
                telegramUserCache.setPosition(Position.NONE);
                telegramUserService.save(telegramUserCache);




                sendMessageService.sendMessage(
                        message,
                        "Реєстрація пройшла успішно❗",
                        Keyboards.linkToMenuKeyboard());


            }



            case "cancel" -> {
                if (Position.CONFIRMATION== telegramUserCache.getPosition()){

                    statementCacheService.removeAll(telegramUserCache.getTelegramId());


                    telegramUserCache.setStatementCache(null);
                    telegramUserCache.setPosition(Position.NONE);
                    telegramUserService.save(telegramUserCache);


                    sendMessageService.sendMessage(
                            message,
                            "Реєстрацію довідки скасовано",
                            Keyboards.linkToMenuKeyboard()
                    );

                }

            }
        }
    }
}

