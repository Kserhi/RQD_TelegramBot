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
import java.util.List;

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
    BotUser botUser=cache.findBy(message.getChatId());

            sendInfoAboutUserFromDataBasa(message,BotUserDataService.STATEMENTFORSTUDY,botUser);
//            sendInfoAboutUserFromDataBasa(message,BotUserDataService.STATEMENTFORMILITARI,botUser);


            messageSender.sendMessage(SendMessage.builder()
                    .chatId(String.valueOf(message.getChatId()))
                    .replyMarkup(inlineKeyboardMarkup)
                    .text("Довідки для військомату не знайдено")
                    .build());


    }

    public void sendInfoAboutUserFromDataBasa(Message message,String statement,BotUser botUser){

        List<BotUser> users=BotUserDataService.getAllInfoAboutUser(message,statement);
//        List<BotUser> users=BotUserDataService.getAllInfoAboutUser(message,botUser.getStatement());
        sendMessage(message,users.get(0).getFullName());


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

//    public void getPhoneNumber(BotUser user) {
//        SendMessage message = SendMessage.builder()
//                .text("Натисніть кнопку, щоб надіслати свій номер телефону:")
//                .chatId(String.valueOf(user.getTelegramId()))
//                .replyMarkup(ReplyKeyboardMarkup.builder()
//                        .keyboardRow(Collections.singletonList(
//                                KeyboardButtonContact.builder()
//                                        .text("Відправити номер телефону")
//                                        .build()
//                        ))
//                        //привіт
//                        .oneTimeKeyboard(true)
//                        .resizeKeyboard(true)
//                        .build())
//                .build();

//        messageSender.sendMessage(message);
//        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
//                .keyboardRow(Collections.singletonList(
//                        InlineKeyboardButton.builder()
//                                .text("Отримати номер телефону")
//                                .requestContact(true) // Включаємо опцію запиту контакту (телефону)
//                                .build()
//                ))
//                .build();
//
//        messageSender.sendMessage(SendMessage.builder()
//                .chatId(String.valueOf(user.getTelegramId()))
//                .text("Будь ласка, натисніть кнопку \"Отримати номер телефону\", щоб надіслати свій номер телефону.")
//                .replyMarkup(inlineKeyboardMarkup)
//                .build());
//   }

}
