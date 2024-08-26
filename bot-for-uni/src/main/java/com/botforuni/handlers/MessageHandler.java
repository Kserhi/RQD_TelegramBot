package com.botforuni.handlers;

import com.botforuni.Keybords.Keyboards;
import com.botforuni.cache.Cache;
import com.botforuni.domain.BotUser;
import com.botforuni.domain.Statement;
import com.botforuni.domain.TelegramUser;
import com.botforuni.services.SendMessageService;
import com.botforuni.services.StatementService;
import com.botforuni.services.TelegramUserService;
import com.botforuni.utils.Constants;
import com.botforuni.utils.PositionInTelegramChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler implements Handler<Message> {

    @Autowired
    private SendMessageService sendMessageService;




    private final Cache<BotUser> cache;


    public MessageHandler(Cache<BotUser> cache) {
        this.cache = cache;

    }



    @Override
    public void choose(Message message) {




        TelegramUser telegramUser= TelegramUserService.get(message.getChatId());


        if (!telegramUser.getPosition().equals(PositionInTelegramChat.NONE)){
            Statement statement = StatementService.findById(telegramUser.getIdOfStatement());

            switch (telegramUser.getPosition()){
                case PositionInTelegramChat.INPUTUSERNAME -> {
                    statement.setFullName(message.getText());
                    telegramUser.setPosition(PositionInTelegramChat.INPUTUSERGROUP);

                    TelegramUserService.save(telegramUser);
                    StatementService.save(statement);

                    sendMessageService.sendMessage(message, "Введіть вашу групу(Наприклад: КН23c)⤵");

                }

                case PositionInTelegramChat.INPUTUSERGROUP -> {
                    statement.setGroupe(message.getText());
                    telegramUser.setPosition(PositionInTelegramChat.INPUTUSERYEAR);

                    TelegramUserService.save(telegramUser);
                    StatementService.save(statement);

                    sendMessageService.sendMessage(message, "Введіть ваш рік набору(Наприклад: 2021)⤵");



                }
                case PositionInTelegramChat.INPUTUSERYEAR -> {
                    statement.setYearEntry(message.getText());
                    telegramUser.setPosition(PositionInTelegramChat.INPUTUSERFACULTY);

                    TelegramUserService.save(telegramUser);
                    StatementService.save(statement);

                    sendMessageService.sendMessage(
                            message,
                            "Виберіть ваш факультет",
                            Keyboards.chooseFaculty()
                    );



                }



                case PositionInTelegramChat.INPUTUSERFACULTY -> {
                    statement.setFaculty(message.getText());
                    telegramUser.setPosition(PositionInTelegramChat.INPUTUSERPHONE);

                    TelegramUserService.save(telegramUser);
                    StatementService.save(statement);

                    sendMessageService.sendMessage(message, "Введіть ваш номер телефону⤵");
                    sendMessageService.sendMessage(
                            message,
                            "Нажміть, щоб поділитися контактом",
                            Keyboards.phoneKeyboard()
                    );


                }

                case PositionInTelegramChat.INPUTUSERPHONE -> {
                        statement.setPhoneNumber(message.getContact().getPhoneNumber());
                        telegramUser.setPosition(PositionInTelegramChat.CONFIRMATION);

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
