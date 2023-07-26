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
     * @param messageFromUser Об'єкт, що представляє отримане повідомлення від користувача.
     * @param text    Текст повідомлення для надсилання.
     */
    public void sendMessage(Message messageFromUser, String text) {
        // Створення об'єкту SendMessage для надсилання текстового повідомлення
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(String.valueOf(messageFromUser.getChatId()))
                .build();

        // Надсилання повідомлення за допомогою messageSender.sendMessage()
        messageSender.sendMessage(message);
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






    public void sendAllInfoAboutUserFromDataBasa(Message message) {
        BotUser botUser=BotUserDataService.getAllInfoAboutUser(
                message.getChatId(),
                BotUserDataService.STATEMENTFORSTUDY);


        if (botUser.getTelegramId()!=null){
            sendMessage(
                    message,
                    botUser.toString()
            );

        }else {
            sendMessage(
                    message,
                    "Інформації про "+BotUserDataService.STATEMENTFORSTUDY +" незнайдено");
        }


        botUser=BotUserDataService.getAllInfoAboutUser(
                message.getChatId(),
                BotUserDataService.STATEMENTFORMILITARI);


        if (botUser.getTelegramId()!=null){
            sendMessage(message,botUser.toString(),Keyboards.linkToMenuKeyboard());

        }else {
            sendMessage(
                    message,
                    "Інформації про "+BotUserDataService.STATEMENTFORMILITARI
                            +" незнайдено",
                    Keyboards.linkToMenuKeyboard());
        }


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
            sendMessage(message,botUser.toString(),Keyboards.linkToMenuKeyboard());

        }


    }

}
