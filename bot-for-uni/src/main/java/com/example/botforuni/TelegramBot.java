package com.example.botforuni;

import com.example.botforuni.processors.Processor;
import com.example.botforuni.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "ldubgdDekanat_bot";
    }

    @Override
    public String getBotToken() {
        return "6139727723:AAGhYLSHJaIzSF0yDyps1b3d14PLB3oXQnI";
    }

    private Processor processor;

    @Autowired
    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public void onUpdateReceived(Update update) {
        processor.process(update);
    }

}
