package com.botforuni.Keybords;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Keyboards {


    public static InlineKeyboardMarkup chooseStatementKeyboard() {
        //менюшка вибору запиту

        return InlineKeyboardMarkup.builder()
                .keyboardRow(
                        Collections.singletonList(
                                InlineKeyboardButton.builder()
                                        .text("Замовити довідку з місця навчання")
                                        .callbackData("statementForStudy")

                                        .build()
                        ))
                .keyboardRow(
                        Collections.singletonList(
                                InlineKeyboardButton.builder()
                                        .text("Замовити довідку для військомату")
                                        .callbackData("statementForMilitaryOfficer")
                                        .build()
                        )
                )
                .keyboardRow(
                        Collections.singletonList(
                                InlineKeyboardButton.builder()
                                        .text("Головне меню")
                                        .callbackData("/menu").build()


                        )
                )
                .build();

    }

    public static InlineKeyboardMarkup menuKeyboard() {
        //менюшка вибору запиту


        return InlineKeyboardMarkup.builder()
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

    }

    public static InlineKeyboardMarkup starKeyboard() {
        ///менюшка після старту ❗Потрібна послуга деканату

        return InlineKeyboardMarkup.builder()
                .keyboardRow(
                        Collections.singletonList(
                                InlineKeyboardButton.builder()
                                        .text("❗Потрібна послуга деканату")
                                        .callbackData("/menu").build()


                        )

                )
                .build();


    }

    public static InlineKeyboardMarkup linkToMenuKeyboard() {


        return InlineKeyboardMarkup.builder()
                .keyboardRow(
                        Collections.singletonList(
                                InlineKeyboardButton.builder()
                                        .text("Головне меню")
                                        .callbackData("/menu").build()


                        )

                )
                .build();

    }

    public static InlineKeyboardMarkup helpMenu() {


        return InlineKeyboardMarkup.builder()
                .keyboardRow(
                        Collections.singletonList(
                                InlineKeyboardButton.builder()
                                        .text("Посилання")
                                        .url("https://telegra.ph/P" +
                                                "OS%D0%86BNIK-KORISTUVACHA-TELEGRAM-" +
                                                "BOTA-LDU-BZHD-05-22")
                                        .build()
                        ))
                .build();

    }

    public static InlineKeyboardMarkup confirmationKeyboard() {

        return InlineKeyboardMarkup.builder()
                .keyboardRow(
                        Collections.singletonList(
                                InlineKeyboardButton.builder()
                                        .text("Підтвердити✔")
                                        .callbackData("confirm")
                                        .build()
                        ))
                .keyboardRow(
                        Collections.singletonList(
                                InlineKeyboardButton.builder()
                                        .text("Скасувати❌")
                                        .callbackData("cancel")
                                        .build()
                        )
                )
                .build();

    }

    public static ReplyKeyboardMarkup phoneKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(KeyboardButton.builder().text("Номер телефону")
                .requestContact(true)
                .build());
        keyboardRows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup  chooseFaculty(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        List<String> list=new ArrayList<>();
        list.add("Факультет цивільного захисту");
        list.add("Факультет пожежної та техногенної безпеки");
        list.add("Факультет психології і соціального захисту");

        list.forEach(string -> {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(string));
            keyboardRows.add(keyboardRow);
        });

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

}
