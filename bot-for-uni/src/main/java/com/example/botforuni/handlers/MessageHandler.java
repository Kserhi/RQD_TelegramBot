package com.example.botforuni.handlers;

import com.example.botforuni.Keybords.Keyboards;
import com.example.botforuni.cache.Cache;
import com.example.botforuni.database.UserData;
import com.example.botforuni.domain.BotUser;
import com.example.botforuni.domain.Position;
import com.example.botforuni.messagesender.MessageSender;
import com.example.botforuni.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler implements Handler<Message> {



    private SendMessageService sendMessageService;

    private final Cache<BotUser> cache;
    private final UserData userData;
    @Autowired
    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    public MessageHandler( Cache<BotUser> cache,UserData userData) {
        this.cache = cache;
        this.userData=userData;

    }

    private static BotUser generateUserFromMessage(Message message) {
        BotUser user = new BotUser();
        user.setUsername(message.getFrom().getUserName());
        user.setId(message.getChatId());
        user.setPosition(Position.INPUT_USER_NAME);
        return user;
    }

    @Override
    public void choose(Message message) {
        BotUser user = cache.findBy(message.getChatId());

        if (user != null && user.getPosition() != Position.NONE) {
            switch (user.getPosition()) {
                case INPUT_USER_NAME:
                    user.setFullName(message.getText());
                    user.setPosition(Position.INPUT_USER_GROUP);
                    sendMessageService.sendMessage(message, "Введіть вашу групу(Наприклад: КН23c)⤵");
                    break;
                case INPUT_USER_GROUP:
                    user.setGroup(message.getText());
                    user.setPosition(Position.INPUT_USER_YEAR);
                    sendMessageService.sendMessage(message, "Введіть ваш рік набору(Наприклад: 2021)⤵");
                    break;
                case INPUT_USER_YEAR:
                    user.setYear(message.getText());
                    user.setPosition(Position.CONFIMATION);
                    sendMessageService.sendInfoAboutUser(message, user);
                    sendMessageService.sndConfirmationMenu(message);
                    break;
                case CONFIMATION:
                    switch (message.getText()) {
                        case "Підтвердити✔":
                            user.setPosition(Position.NONE);
                            sendMessageService.sendMessage(message, "Реєстрація пройшла успішно❗");
                            userData.put(user);
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
            String textFromUser = message.getText();
            switch (textFromUser) {//порівнюєм текст від юзера з командами
                case "/start":
                    sendMessageService.sendStartMenu(message);
                    sendMessageService.sendMessage(message, "Обирайте з меню нижче ⤵️");
                    break;
                case "❗Потрібна послуга деканату":
                case "❌ Скасувати":
                case "/menu":
                    sendMessageService.sendMenu(message);
                    break;
                case "Створити довідку з місця навчання":
                    sendMessageService.sendRegMenu(message);
                    break;
                case "Реєстрація":
                    cache.add(generateUserFromMessage(message));
                    sendMessageService.sendMessage(message, "Введіть ваш ПІБ(Наприклад: Барабах Павло Романович)⤵");
                    break;
                case "/reset":
                    cache.remove(user);
                    break;
                case "/help":
                    userData.get(message.getChatId());

//                    userData.getAllUsers();
                    sendMessageService.sendInfoAboutUser(message, user);
                    break;
            }
        }
    }
}
