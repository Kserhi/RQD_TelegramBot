package com.example.botforuni.services;

import com.example.botforuni.Keybords.Keyboards;
import com.example.botforuni.cache.Cache;
import com.example.botforuni.domain.BotUser;
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

    private final Cache<BotUser> cache;

    public SendMessageService(MessageSender messageSender,Cache<BotUser> cache) {
        this.cache=cache;
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

            sendInfoAboutUserFromDataBasa(message,BotUserDataService.STATEMENTFORSTUDY);
//            sendInfoAboutUserFromDataBasa(message,BotUserDataService.STATEMENTFORMILITARI);

//
//            messageSender.sendMessage(SendMessage.builder()
//                    .chatId(String.valueOf(message.getChatId()))
//                    .replyMarkup(inlineKeyboardMarkup)
//                    .text("Довідки для військомату не знайдено")
//                    .build());
//

    }

    public void sendInfoAboutUserFromDataBasa(Message message,String statement){
        BotUser botUser=BotUserDataService.getAllInfoAboutUser(message.getChatId(),statement);

        if(botUser.getTelegramId()==null){
            sendMessage(
                    message,
                    "Інформації незнайдено",
                    Keyboards.linkToMenuKeyboard()
            );
        }else {
            messageSender.sendMessage(SendMessage.builder()
                    .parseMode("HTML")
                    .chatId(String.valueOf(botUser.getTelegramId()))
                    .text("<b>ПІБ: </b> " + botUser.getFullName() + "\n" +
                            "<b>Група: </b>" + botUser.getGroupe() + "\n" +
                            "<b>Рік набору: </b>" + botUser.getYearEntry() + "\n" +
                            "<b>Номер телефону: </b>" + botUser.getPhoneNumber() + "\n" +
                            "<b>Тип заявки: </b>" +botUser.getStatement())
                            .replyMarkup(Keyboards.linkToMenuKeyboard())
                    .build());
        }


    }

    public void sendInfoAboutUserFromCache(BotUser user) {
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


}
