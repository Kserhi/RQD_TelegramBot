package com.botforuni.handlers;

import com.botforuni.keybords.Keyboards;
import com.botforuni.domain.Position;
import com.botforuni.domain.StatementCache;
import com.botforuni.domain.TelegramUserCache;
import com.botforuni.services.SendMessageService;
import com.botforuni.services.TelegramUserService;
import com.botforuni.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler implements Handler<Message> {
    @Autowired
    private TelegramUserService telegramUserService;

    @Autowired
    private SendMessageService sendMessageService;

    @Override
    public void choose(Message message) {




        TelegramUserCache telegramUserCache = telegramUserService.getOrGenerate(message.getChatId());


        if (telegramUserCache.getPosition()!=Position.NONE){
            StatementCache statementCache =telegramUserCache.getStatementCache();

            switch (telegramUserCache.getPosition()){
                case INPUT_USER_NAME -> {
                    statementCache.setFullName(message.getText());
                    telegramUserCache.setPosition(Position.INPUT_USER_GROUP);
                    telegramUserCache.setStatementCache(statementCache);
                    telegramUserService.save(telegramUserCache);
                    sendMessageService.sendMessage(message, "Введіть вашу групу (Наприклад: КН23c)⤵");

                }

                case INPUT_USER_GROUP -> {
                    statementCache.setGroupe(message.getText());
                    telegramUserCache.setPosition(Position.INPUT_USER_YEAR);
                    telegramUserCache.setStatementCache(statementCache);
                    telegramUserService.save(telegramUserCache);

                    sendMessageService.sendMessage(message, "Введіть ваш рік набору(Наприклад: 2021)⤵");



                }
                case INPUT_USER_YEAR -> {
                    statementCache.setYearEntry(message.getText());
                    telegramUserCache.setPosition(Position.INPUT_USER_FACULTY);

                    telegramUserCache.setStatementCache(statementCache);
                    telegramUserService.save(telegramUserCache);


                    sendMessageService.sendMessage(
                            message,
                            "Виберіть ваш факультет",
                            Keyboards.chooseFaculty()
                    );



                }



                case INPUT_USER_FACULTY -> {
                    statementCache.setFaculty(message.getText());
                    telegramUserCache.setPosition(Position.INPUT_USER_PHONE);

                    telegramUserCache.setStatementCache(statementCache);
                    telegramUserService.save(telegramUserCache);


                    sendMessageService.sendMessage(message,
                            "Введіть ваш номер телефону⤵",
                            Keyboards.keyboardRemove());
                    sendMessageService.sendMessage(
                            message,
                            "Нажміть, щоб поділитися контактом",
                            Keyboards.phoneKeyboard()
                    );


                }

                case INPUT_USER_PHONE -> {
                        if (message.hasContact()){
                            statementCache.setPhoneNumber(message.getContact().getPhoneNumber());
                            telegramUserCache.setPosition(Position.CONFIRMATION);

                            telegramUserCache.setStatementCache(statementCache);
                            telegramUserService.save(telegramUserCache);


                            sendMessageService.sendMessage(
                                    message,
                                    statementCache.toString(),
                                    Keyboards.keyboardRemove()

                            );

                            sendMessageService.sendMessage(
                                    message,
                                    "Нажміть, щоб підтвердити дані",
                                    Keyboards.confirmationKeyboard()
                            );
                        }else {
                            sendMessageService.sendMessage(
                                    message,
                                    "Нажміть кнопку щоб поділитись контактом",
                                    Keyboards.phoneKeyboard()
                            );
                        }




                }


            }



        }else if (message.hasText()) {
            switch (message.getText()) {
                case "/start" -> sendMessageService.sendMessage(
                        message,
                        Constants.START,
                        Keyboards.starKeyboard());
                case "/help" -> sendMessageService.sendMessage(
                        message,
                        Constants.HELP,
                        Keyboards.helpMenu()
                );
            }
        }




    }
}
