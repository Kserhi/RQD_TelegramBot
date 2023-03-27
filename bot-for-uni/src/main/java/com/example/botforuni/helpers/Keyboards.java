package com.example.botforuni.helpers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
public class Keyboards {
    public ReplyKeyboardMarkup afterStartKeyboard() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("❗️Потрібна послуга деканату");

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(keyboardRow))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }

}
