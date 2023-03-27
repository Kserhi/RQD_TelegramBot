package com.example.botforuni.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Slf4j
@Component
public class MyFirstBotSender extends DefaultAbsSender {
    @Value("6139727723:AAGhYLSHJaIzSF0yDyps1b3d14PLB3oXQnI")
    private String botToken;

    protected MyFirstBotSender() {
        super(new DefaultBotOptions());
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

}
