package com.example.botforuni.handlers;

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

    private BotUser generateUserFromMessage(Message message){
        BotUser user = new BotUser();
        user.setUsername(message.getFrom().getUserName());
        user.setId(message.getChatId());
        user.setPosition(Position.INPUT_USER_NAME);
        return user;
    }

    @Override
    public void choose(Message message) {
        BotUser user = cache.findBy(message.getChatId());
        if (user != null){
            switch (user.getPosition()){
                case INPUT_USER_NAME:
                    user.setFullName(message.getText());
                    user.setPosition(Position.INPUT_USER_GROUP);
                    messageSender.sendMessage(
                            SendMessage.builder()
                            .text("Введіть вашу групу⤵")
                            .chatId(String.valueOf(message.getChatId()))
                            .build()
                    );
                    break;
                case INPUT_USER_GROUP:
                    user.setGroup(message.getText());
                    user.setPosition(Position.INPUT_USER_YEAR);
                    messageSender.sendMessage(
                            SendMessage.builder()
                                    .text("Введіть ваш рік набору⤵")
                                    .chatId(String.valueOf(message.getChatId()))
                                    .build()
                    );
                    break;
                case INPUT_USER_YEAR:
                    user.setYear(message.getText());
                    user.setPosition(Position.NONE);
                    messageSender.sendMessage(SendMessage.builder()
                            .parseMode("HTML")
                            .chatId(String.valueOf(user.getId()))
                            .text("<b>ПІБ: </b> " + user.getFullName() + "\n" +
                                    "<b>Група: </b>" + user.getGroup() + "\n" +
                                    "<b>Рік набору: </b>" + user.getYear())
                            .build());
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
                    messageSender.sendMessage(
                            SendMessage.builder()
                                    .text("Введіть ваш ПІБ⤵")
                                    .chatId(String.valueOf(message.getChatId()))
                                    .build()
                    );
                    break;

            }
        }
    }
}
