package com.example.botforuni.handlers;

import com.example.botforuni.Keybords.Keyboards;
import com.example.botforuni.cache.Cache;
import com.example.botforuni.domain.BotUser;
import com.example.botforuni.domain.Position;
import com.example.botforuni.messagesender.MessageSender;
import com.example.botforuni.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler implements Handler<Message>{

    private final MessageSender messageSender;

    private SendMessageService sendMessageService;

    private final Cache<BotUser> cache;

    @Autowired
    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    public MessageHandler(MessageSender messageSender, Cache<BotUser> cache) {
        this.messageSender = messageSender;
        this.cache = cache;
    }

    private static BotUser generateUserFromMessage(Message message){
        BotUser user = new BotUser();
        user.setUsername(message.getFrom().getUserName());
        user.setId(message.getChatId());
        user.setPosition(Position.INPUT_USER_NAME);
        return user;
    }

    @Override
    public void choose(Message message) {
        BotUser user = cache.findBy(message.getChatId());


        if (user != null && user.getPosition()!=Position.NONE){
            switch (user.getPosition()){
                case INPUT_USER_NAME:
                    user.setFullName(message.getText());
                    user.setPosition(Position.INPUT_USER_GROUP);
                    sendMessageService.sendMessage(message,"Введіть вашу групу⤵");
                    break;
                case INPUT_USER_GROUP:
                    user.setGroup(message.getText());
                    user.setPosition(Position.INPUT_USER_YEAR);
                    sendMessageService.sendMessage(message,"Введіть ваш рік набору⤵");
                    break;
                case INPUT_USER_YEAR:
                    user.setYear(message.getText());
                    user.setPosition(Position.NONE);
                    sendMessageService.sendInfoAboutUser(message,user);
                    break;

            }
        }else if (message.hasText()) {
            String textFromUser = message.getText();
            switch (textFromUser) {//порівнюєм текст від юзера з командами
                case "/start":
                    sendMessageService.sendStartMenu(message);
                    sendMessageService.sendStartMenuDemo(message);
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
                    sendMessageService.sendMessage(message,"Введіть ваш ПІБ⤵");
                    break;
                case "/reset":
                    cache.remove(user);
                    break;
                case "/help":
                    sendMessageService.sendInfoAboutUser(message,user);
                    break;
            }
        }
    }
}
