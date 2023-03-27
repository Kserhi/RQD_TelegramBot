package com.example.botforuni.handler.allHandlers;

import com.example.botforuni.UserInfo.UserRequest;
import com.example.botforuni.handler.UserRequestHandler;
import com.example.botforuni.helpers.Keyboards;
import com.example.botforuni.service.TelegramService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class StartHandler extends UserRequestHandler {
    private static String startCommand = "/start";

    private final TelegramService telegramService;

    private final Keyboards keyboards;

    public StartHandler(TelegramService telegramService, Keyboards keyboards) {
        this.telegramService = telegramService;
        this.keyboards = keyboards;
    }

    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), startCommand);
    }

    @Override
    public void handle(UserRequest request) {
        ReplyKeyboard replyKeyboard = keyboards.afterStartKeyboard();
        telegramService.sendMessage(request.getChatId(),
                "\uD83D\uDC4BПривіт! За допомогою цього чат-бота ви зможете зробити запит до деканату!",
                replyKeyboard);
        telegramService.sendMessage(request.getChatId(),
                "Обирайте з меню нижче ⤵️");
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
