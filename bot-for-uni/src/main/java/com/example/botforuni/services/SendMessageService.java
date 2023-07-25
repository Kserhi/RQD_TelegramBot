package com.example.botforuni.services;

import com.example.botforuni.domain.BotUser;
import com.example.botforuni.Keybords.Keyboards;
import com.example.botforuni.messagesender.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.Collections;
@Service
public class SendMessageService {
    private final MessageSender messageSender;
    


    public SendMessageService(MessageSender messageSender) {

        this.messageSender = messageSender;
    }

    /**
     * Надсилає просте текстове повідомлення до чату з вказаним текстом.
     *
     * @param message Об'єкт, що представляє отримане повідомлення від користувача.
     * @param text    Текст повідомлення для надсилання.
     */
    public void sendMessage(Message message, String text) {
        // Створення об'єкту SendMessage для надсилання текстового повідомлення
        SendMessage ms1 = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(message.getChatId()))
                .build();

        // Надсилання повідомлення за допомогою messageSender.sendMessage()
        messageSender.sendMessage(ms1);
    }

    /**
     * Надсилає текстове повідомлення до чату з можливістю додати інлайн клавіатуру.
     *
     * @param messageFromUser Об'єкт, що представляє отримане повідомлення від користувача.
     * @param text            Текст повідомлення для надсилання.
     * @param inlineKeyboard  Об'єкт з інлайн клавіатурою (кнопки для взаємодії).
     */
    public void sendMessage(Message messageFromUser, String text, InlineKeyboardMarkup inlineKeyboard) {
        // Створення об'єкту SendMessage для надсилання текстового повідомлення з інлайн клавіатурою
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(messageFromUser.getChatId()))
                .replyMarkup(inlineKeyboard)
                .build();

        // Надсилання повідомлення за допомогою messageSender.sendMessage()
        messageSender.sendMessage(message);
    }

    /**
     * Надсилає текстове повідомлення до чату з клавіатурою відповіді.
     *
     * @param messageFromUser Об'єкт, що представляє отримане повідомлення від користувача.
     * @param text            Текст повідомлення для надсилання.
     * @param replyKeyboard   Об'єкт з клавіатурою відповіді (звичайна клавіатура з кнопками).
     */
    public void sendMessage(Message messageFromUser, String text, ReplyKeyboardMarkup replyKeyboard) {
        // Створення об'єкту SendMessage для надсилання текстового повідомлення з клавіатурою відповіді
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(messageFromUser.getChatId()))
                .replyMarkup(replyKeyboard)
                .build();

        // Надсилання повідомлення за допомогою messageSender.sendMessage()
        messageSender.sendMessage(message);
    }


    public void choose_statement(Message message) {

        SendMessage ms1 = SendMessage.builder()
                .text("Виберіть тип довідки⤵")

                .chatId(String.valueOf(message.getChatId()))
                .replyMarkup(InlineKeyboardMarkup.builder()
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

        messageSender.sendMessage(ms1);
    }

    public void sendRegMenu(Message message) {

        SendMessage ms1 = SendMessage.builder()
                .text("Пройдіть реєстрацію⤵")
                .replyMarkup(Keyboards.regKeyboard())
                .chatId(String.valueOf(message.getChatId()))
                .build();


        messageSender.sendMessage(ms1);

    }



    public void sendAllInfoAboutUserFromDataBasa(Message message) {


        InlineKeyboardMarkup inlineKeyboardMarkup=InlineKeyboardMarkup.builder()
                .keyboardRow(
                        Collections.singletonList(
                                InlineKeyboardButton.builder()
                                        .text("Повернутись")
                                        .callbackData("/menu")
                                        .build()
                        ))
                .build();
        
            BotUserDataService.getAllInfoAboutUser(message.getChatId(),BotUserDataService.STATEMENTFORMILITARI);

            messageSender.sendMessage(SendMessage.builder()
                    .chatId(String.valueOf(message.getChatId()))
                    .replyMarkup(inlineKeyboardMarkup)
                    .text("Довідки для військомату не знайдено")
                    .build());


    }


    public void sendInfoAboutUserFromCache(Message message, BotUser user) {
        messageSender.sendMessage(SendMessage.builder()
                .parseMode("HTML")
                .chatId(String.valueOf(user.getTelegramId()))
                .text("<b>ПІБ: </b> " + user.getFullName() + "\n" +
                        "<b>Група: </b>" + user.getGroupe() + "\n" +
                        "<b>Рік набору: </b>" + user.getYearEntry() + "\n" +
                        "<b>Номер телефону: </b>" + user.getPhoneNumber() + "\n" +
                        "<b>Тип заявки: </b>" + user.getStatement())

                .build());

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


}
