package com.example.botforuni.handlers;

import com.example.botforuni.cache.Cache;
import com.example.botforuni.domain.BotUser;
import com.example.botforuni.domain.Position;
import com.example.botforuni.jdbc.UserData;
import com.example.botforuni.messagesender.MessageSender;
import com.example.botforuni.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler implements Handler<Message> {


    private SendMessageService sendMessageService;
    private MessageSender messageSender;


    private final Cache<BotUser> cache;

    @Autowired
    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public MessageHandler(Cache<BotUser> cache) {
        this.cache = cache;

    }



    @Override
    public void choose(Message message) {
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
                    sendMessageService.phoneNum(message);
                    break;
                case INPUT_USER_PHONE:
                    user.setPhoneNumber(message.getContact().getPhoneNumber());
                    user.setPosition(Position.CONFIMATION);
                    sendMessageService.sendInfoAboutUserFromCache(message, user);
                    sendMessageService.sendConfirmationMenu(message);
                    break;
                case CONFIMATION:
                    switch (message.getText()) {
                        case "Підтвердити✔":
                            user.setPosition(Position.NONE);
                            sendMessageService.sendMessage(message, "Реєстрація пройшла успішно❗");
                            UserData.putUserInDataBase(user);
                            sendMessageService.sendMessage(message, "Ваша заявка⤵");
                            sendMessageService.sendInfoAboutUserFromCache(message,user);

                            break;
                        case "Скасувати❌":
                            sendMessageService.sendMessage(message, "Введіть дані ще раз");
                            sendMessageService.sendMessage(message, "Введіть ваш ПІБ(Наприклад: Барабах Павло Романович)⤵");
                            user.setPosition(Position.INPUT_USER_NAME);

                            break;
                    }
                    break;
            }
        } else if (message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    sendMessageService.sendStartMenu(message);
                    break;
                case "/menu":
                    sendMessageService.sendMenu(message);
                    break;
                case "/help":
                    sendMessageService.sendHelp(message);
                    break;

            }
        }
    }
}
