package com.botforuni.messageSender;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;

public interface MessageSender {
    void sendMessage(SendMessage sendMessage);

    void sendEdit(EditMessageReplyMarkup editMessageReplyMarkup);
}
