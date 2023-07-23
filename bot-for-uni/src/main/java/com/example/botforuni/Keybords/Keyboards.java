package com.example.botforuni.Keybords;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Keyboards {
    //переніс сюда менюшки шоб тягнути їх без проблем

    public static InlineKeyboardMarkup menuKeyboard() {
        //менюшка вибору запиту
        InlineKeyboardMarkup inlineKeyboardMarkup=InlineKeyboardMarkup.builder()
                .keyboardRow(
                        Collections.singletonList(
                                InlineKeyboardButton.builder()
                                        .text("Замовити довідку")
                                        .callbackData("choose_statement")
                                        .build()
                        ))
                .keyboardRow(
                        Collections.singletonList(
                                InlineKeyboardButton.builder()
                                        .text("Переглянути мої довідки")
                                        .callbackData("statements")
                                        .build()
                        )
                )
                .build();







        return inlineKeyboardMarkup;

    }
    public static ReplyKeyboardMarkup regKeyboard() {
        //менюшка вибору запиту
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();


        row1.add("Реєстрація");
        row2.add("❌ Скасувати");


        keyboardRows.add(row1);
        keyboardRows.add(row2);


        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;

    }

    public static InlineKeyboardMarkup starKeyboard() {
        ///менюшка після старту ❗Потрібна послуга деканату

        InlineKeyboardMarkup inlineKeyboardMarkup= InlineKeyboardMarkup.builder()
                .keyboardRow(
                        Collections.singletonList(
                                InlineKeyboardButton.builder()
                                        .text("❗Потрібна послуга деканату")
                                        .callbackData("/menu").build()


                        )

                )
                .build();
        return inlineKeyboardMarkup;

    }

    public static ReplyKeyboardMarkup confirmationKeyboard() {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Підтвердити✔");
        keyboardRow.add("Скасувати❌");
        keyboardRows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;

    }

    public static ReplyKeyboardMarkup phoneKeyboard(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);


        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(KeyboardButton.builder()
                .text("Номер телефону")
                .requestContact(true)
                .build());

        keyboardRows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }



}
