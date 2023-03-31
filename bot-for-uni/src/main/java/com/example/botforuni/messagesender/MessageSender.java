package com.example.botforuni.messagesender;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface MessageSender {
    void sendMessage(SendMessage sendMessage);
}
