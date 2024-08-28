package com.botforuni.handlers;

import com.botforuni.Keybords.Keyboards;
import com.botforuni.domain.Position;
import com.botforuni.domain.Statement;
import com.botforuni.domain.TelegramUser;
import com.botforuni.services.SendMessageService;
import com.botforuni.services.StatementService;
import com.botforuni.services.TelegramUserService;
import com.botforuni.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler implements Handler<Message> {

    @Autowired
    private SendMessageService sendMessageService;

    @Override
    public void choose(Message message) {




        TelegramUser telegramUser= TelegramUserService.getOrGenerate(message.getChatId());


        if (telegramUser.getPosition()!=Position.NONE){
            Statement statement = StatementService.findById(telegramUser.getIdOfStatement());

            switch (telegramUser.getPosition()){
                case INPUT_USER_NAME -> {
                    statement.setFullName(message.getText());
                    telegramUser.setPosition(Position.INPUT_USER_GROUP);

                    TelegramUserService.save(telegramUser);
                    StatementService.save(statement);

                    sendMessageService.sendMessage(message, "Введіть вашу групу (Наприклад: КН23c)⤵");

                }

                case INPUT_USER_GROUP -> {
                    statement.setGroupe(message.getText());
                    telegramUser.setPosition(Position.INPUT_USER_YEAR);

                    TelegramUserService.save(telegramUser);
                    StatementService.save(statement);

                    sendMessageService.sendMessage(message, "Введіть ваш рік набору(Наприклад: 2021)⤵");



                }
                case INPUT_USER_YEAR -> {
                    statement.setYearEntry(message.getText());
                    telegramUser.setPosition(Position.INPUT_USER_FACULTY);

                    TelegramUserService.save(telegramUser);
                    StatementService.save(statement);

                    sendMessageService.sendMessage(
                            message,
                            "Виберіть ваш факультет",
                            Keyboards.chooseFaculty()
                    );



                }



                case INPUT_USER_FACULTY -> {
                    statement.setFaculty(message.getText());
                    telegramUser.setPosition(Position.INPUT_USER_PHONE);

                    TelegramUserService.save(telegramUser);
                    StatementService.save(statement);

                    sendMessageService.sendMessage(message, "Введіть ваш номер телефону⤵");
                    sendMessageService.sendMessage(
                            message,
                            "Нажміть, щоб поділитися контактом",
                            Keyboards.phoneKeyboard()
                    );


                }

                case INPUT_USER_PHONE -> {
                        if (message.hasContact()){
                            statement.setPhoneNumber(message.getContact().getPhoneNumber());
                            telegramUser.setPosition(Position.CONFIRMATION);

                            TelegramUserService.save(telegramUser);
                            StatementService.save(statement);


                            sendMessageService.sendMessage(
                                    message,
                                    statement.toString()
                            );

                            sendMessageService.sendMessage(
                                    message,
                                    "Нажміть, щоб підтвердити дані",
                                    Keyboards.confirmationKeyboard()
                            );
                        }else {
                            sendMessageService.sendMessage(
                                    message,
                                    "Нажміть кнопку щоб поділитись контактом"
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
