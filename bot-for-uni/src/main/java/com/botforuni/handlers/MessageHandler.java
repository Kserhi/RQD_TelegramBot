package com.botforuni.handlers;

import com.botforuni.Keybords.Keyboards;
import com.botforuni.cache.Cache;
import com.botforuni.domain.BotUser;
import com.botforuni.domain.TelegramUser;
import com.botforuni.services.SendMessageService;
import com.botforuni.services.StatementService;
import com.botforuni.services.TelegramUserService;
import com.botforuni.utils.Constans;
import com.botforuni.domain.Position;
import com.botforuni.messageSender.MessageSender;
import com.botforuni.utils.PositionInTelegramChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Component
public class MessageHandler implements Handler<Message> {

    @Autowired
    private SendMessageService sendMessageService;
    @Autowired
    private MessageSender messageSender;



    private final Cache<BotUser> cache;


    public MessageHandler(Cache<BotUser> cache) {
        this.cache = cache;

    }



    @Override
    public void choose(Message message) {

//        витягуємо телеграм користувача з бази даних
//        якшо запису неіснує генеруємо його

        TelegramUser telegramUser;
        Optional<TelegramUser> telegramUserOptional = TelegramUserService
                .findBy(message.getChatId());


        if (telegramUserOptional.isPresent()){

            telegramUser =telegramUserOptional.get();
        }else {
            telegramUser= new TelegramUser(
                    message.getChatId(),
                    PositionInTelegramChat.NONE);
            TelegramUserService.add(telegramUser);
        }


        if (!telegramUser.getPosition().equals(PositionInTelegramChat.NONE)){
            switch (telegramUser.getPosition()){
                case PositionInTelegramChat.INPUTUSERNAME -> {

                }


            }



        }else if (message.hasText()) {
            switch (message.getText()) {
                case "/start" -> sendMessageService.sendMessage(
                        message,
                        Constans.START,
                        Keyboards.starKeyboard());
                case "/help" -> sendMessageService.sendMessage(
                        message,
                        Constans.HELP,
                        Keyboards.helpMenu()
                );
            }
        }


        BotUser user = cache.findBy(message.getChatId());




        if (user != null && user.getPosition() != Position.NONE) {
            //нагадування для себе тут буде перевірка чи юзер має довідку для навчання чи для війська
            // в залежності від того який тип заявки така і реєстрація

            switch (user.getPosition()) {
                case INPUT_USER_NAME:
                    user.setFullName(message.getText());
                    user.setPosition(Position.INPUT_USER_GROUP);
                    sendMessageService.sendMessage(message, "Введіть вашу групу(Наприклад: КН23c)⤵");
                    break;
                case INPUT_USER_GROUP:
                    user.setGroupe(message.getText());
                    user.setPosition(Position.INPUT_USER_YEAR);
                    sendMessageService.sendMessage(message, "Введіть ваш рік набору(Наприклад: 2021)⤵");
                    break;
                case INPUT_USER_YEAR:
                    user.setYearEntry(message.getText());
                    user.setPosition(Position.INPUT_USER_PHONE);
                    sendMessageService.sendMessage(message, "Введіть ваш номер телефону⤵");
                    sendMessageService.sendMessage(
                            message,
                            "Нажміть, щоб поділитися контактом",
                            Keyboards.phoneKeyboard()
                    );
                    break;
                case INPUT_USER_PHONE:
                    user.setPhoneNumber(message.getContact().getPhoneNumber());
                    user.setPosition(Position.CONFIRMATION);
                    sendMessageService.sendMessage(
                            message,
                            user.toString()
                    );
                    sendMessageService.sendMessage(
                            message,
                            "Нажміть, щоб підтвердити дані",
                            Keyboards.confirmationKeyboard()
                    );
                    break;


            }
        } else if (message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    sendMessageService.sendMessage(
                            message,
                            Constans.START,
                            Keyboards.starKeyboard());
                    break;
                case "/help":
                    sendMessageService.sendMessage(
                            message,
                            Constans.HELP,
                            Keyboards.helpMenu()
                    );
                    break;


            }
        }
    }
}
