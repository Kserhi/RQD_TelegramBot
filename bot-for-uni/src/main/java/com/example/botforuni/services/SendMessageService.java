package com.example.botforuni.services;

import com.example.botforuni.Keybords.Keyboards;
import com.example.botforuni.jdbc.UserData;
import com.example.botforuni.domain.BotUser;
import com.example.botforuni.messagesender.MessageSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;
import java.util.List;

@Component
public class SendMessageService {
    private final MessageSender messageSender;


    public SendMessageService(MessageSender messageSender) {

        this.messageSender = messageSender;
    }


    public void sendStartMenu(Message message) {

        SendMessage ms1 = SendMessage.builder()
                .text("\uD83D\uDC4BПривіт! За допомогою цього чат-бота ви зможете зробити запит до деканату!")
                .replyMarkup(Keyboards.starKeyboard())
                .chatId(String.valueOf(message.getChatId()))
                .build();


        messageSender.sendMessage(ms1);

    }


    public void sendMenu(Message message) {

        SendMessage ms1 = SendMessage.builder()
                .text("Виберіть необхідну послугу ⤵ ")
                .replyMarkup(Keyboards.menuKeyboard())
                .chatId(String.valueOf(message.getChatId()))
                .build();


        messageSender.sendMessage(ms1);

    }



    public void choose_statement(Message message){


        SendMessage ms=SendMessage.builder()
                .text("Виберіть тип довідки⤵")
                .chatId(String.valueOf(message.getChatId()))
                .replyMarkup( InlineKeyboardMarkup.builder()
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
                        .build())
                .build();

        messageSender.sendMessage(ms);
    }
    public void sendRegMenu(Message message) {

        SendMessage ms1 = SendMessage.builder()
                .text("Пройдіть реєстрацію⤵")
                .replyMarkup(Keyboards.regKeyboard())
                .chatId(String.valueOf(message.getChatId()))
                .build();


        messageSender.sendMessage(ms1);

    }


    public void sendInfoAboutUserFromDataBasa(Message message,String tupStatment) {
        List<String> infoInList = UserData.getUserInfoFomDataBasa(message.getChatId(),tupStatment);
        String name = infoInList.get(0);
        String group = infoInList.get(4);
        String year = infoInList.get(1);
        String phone = infoInList.get(3);
        String stat = infoInList.get(2);
        messageSender.sendMessage(SendMessage.builder()
                .replyMarkup(Keyboards.starKeyboard())
                .parseMode("HTML")
                .chatId(String.valueOf(message.getChatId()))
                .text("<b>ПІБ: </b> " + name + "\n" +
                        "<b>Група: </b>" + group + "\n" +
                        "<b>Рік набору: </b>" + year + "\n" +
                        "<b>Номер телефону: </b>" + phone + "\n" +
                        "<b>Тип заявки: </b>" + stat)
                .build());
    }

    public void sendInfoAboutUserFromCache(Message message, BotUser user) {
        messageSender.sendMessage(SendMessage.builder()
                .parseMode("HTML")
                .chatId(String.valueOf(user.getId()))
                .text("<b>ПІБ: </b> " + user.getFullName() + "\n" +
                        "<b>Група: </b>" + user.getGroupe() + "\n" +
                        "<b>Рік набору: </b>" + user.getYearEntry() + "\n" +
                        "<b>Номер телефону: </b>" + user.getPhoneNumber())

                .build());

    }


    public void sendMessage(Message message, String text) {
        SendMessage ms1 = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(message.getChatId()))
                .build();


        messageSender.sendMessage(ms1);
    }

    public void sendConfirmationMenu(Message message) {

        SendMessage ms1 = SendMessage.builder()
                .text("Нажміть, щоб підтвердити дані")
                .chatId(String.valueOf(message.getChatId()))
                .replyMarkup(Keyboards.confirmationKeyboard())
                .build();
        messageSender.sendMessage(ms1);
    }

    public void phoneNum(Message message) {
        SendMessage ms1 = SendMessage.builder()
                .text("Нажміть, щоб поділитися контактом")
                .chatId(String.valueOf(message.getChatId()))
                .replyMarkup(Keyboards.phoneKeyboard())
                .build();
        messageSender.sendMessage(ms1);
    }

        public void sendHelp(Message message){
            SendMessage ms1 = SendMessage.builder()
                    .text("Довідник для бота")
                    .replyMarkup(
                            InlineKeyboardMarkup.builder()
                                    .keyboardRow(
                                            Collections.singletonList(
                                                    InlineKeyboardButton.builder()
                                                            .text("Посилання")
                                                            .url("https://telegra.ph/Pos%D1%96bni" +
                                                                    "k-koristuvacha-dlya-Bota-LDU-BZHD-02-04")
                                                            .build()
                                            ))
                                    .build()
                    )
                    .chatId(String.valueOf(message.getChatId()))
                    .build();


            messageSender.sendMessage(ms1);

        }

    public void deleteUser(Message message) {
        UserData.deleteUserFromDb(message.getChatId());
    }


}
