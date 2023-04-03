package com.example.botforuni.handlers;

import com.example.botforuni.Keybords.Keyboards;
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

    @Autowired
    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    public MessageHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void choose(Message message) {
        if (message.hasText()) {
            String textFromUser = message.getText();
            switch (textFromUser) {//порівнюєм текст від юзера з командами
                case "/start":
                case "На головну":
                    sendMessageService.sendStartMenu(message);
                    sendMessageService.sendStartMenuDemo(message);
                    break;
                case "❗Потрібна послуга деканату":
                case "❌ Скасувати":
                    sendMessageService.sendMenu(message);
                    break;

                case "Створити довідку з місця навчання":
                    sendMessageService.sendRegMenu(message);
                    break;
                case "Реєстрація":
                    sendMessageService.rer(message);
                    break;

            }

        }
    }
}
