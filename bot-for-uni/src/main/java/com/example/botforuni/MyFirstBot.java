package com.example.botforuni;

import com.example.botforuni.UserInfo.UserRequest;
import com.example.botforuni.UserInfo.UserSession;
import com.example.botforuni.service.UserSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


@Slf4j
@Component
public class MyFirstBot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "ldubgdDekanat_bot";
    }

    @Override
    public String getBotToken() {
        return "6139727723:AAGhYLSHJaIzSF0yDyps1b3d14PLB3oXQnI";
    }

    private final Dispatcher dispatcher;
    private final UserSessionService userSessionService;

    @Autowired
    public MyFirstBot(Dispatcher dispatcher, UserSessionService userSessionService) {
        this.dispatcher = dispatcher;
        this.userSessionService = userSessionService;
    }

    public MyFirstBot(DefaultBotOptions options, Dispatcher dispatcher, UserSessionService userSessionService) {
        super(options);
        this.dispatcher = dispatcher;
        this.userSessionService = userSessionService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String textFromUser = update.getMessage().getText();

            Long userId = update.getMessage().getFrom().getId();
            String userFirstName = update.getMessage().getFrom().getFirstName();

            log.info("[{}, {}] : {}", userId, userFirstName, textFromUser);

            Long chatId = update.getMessage().getChatId();
            UserSession session = userSessionService.getSession(chatId);

            UserRequest userRequest = UserRequest
                    .builder()
                    .update(update)
                    .userSession(session)
                    .chatId(chatId)
                    .build();

            boolean dispatched = dispatcher.dispatch(userRequest);

            if (!dispatched) {
                log.warn("Unexpected update from user");
            }
        } else {
            log.warn("Unexpected update from user");
        }
//        System.out.println("Повідомлення отримано: " + update.getMessage().getText());
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setText("Привіт користувач, я отримав твоє повідомлення: " + update.getMessage().getText());
//        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//        try {
//            execute(sendMessage);
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }

    }
}
