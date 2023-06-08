package com.example.botforuni;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BotForUniApplicationTests {

    @Autowired
    private TelegramBot telegramBot;

    private SendMessage sentMessage;

    @Test
    public void testConnectionAndSendMessage() {
        // Створюємо тестове оновлення
        Update update = new Update();
        Message message = new Message();
        message.setText("Тестове повідомлення");
        update.setMessage(message);

        // Викликаємо метод onUpdateReceived бота для обробки оновлення
        telegramBot.onUpdateReceived(update);

        // Перевіряємо, чи було надіслане повідомлення
        assertNotNull(sentMessage);
        assertEquals("Тестове повідомлення", sentMessage.getText());
    }

    // Метод для збереження надісланого повідомлення
    public void setSentMessage(SendMessage message) {
        sentMessage = message;
    }
}
