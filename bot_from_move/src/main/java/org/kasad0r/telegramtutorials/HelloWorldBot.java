package org.kasad0r.telegramtutorials;

import org.kasad0r.telegramtutorials.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelloWorldBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.username}")
    private String username;
    @Value("${telegram.bot.token}")
    private String token;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
    private SendMessageService sendMessageService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {

            Message message = update.getMessage();

            if (message.hasText()) {
                String textFromUser =message.getText();
                System.out.println(message.getChat().getUserName());
                System.out.println(textFromUser);


                switch (textFromUser){
                    case "Створити заявку":
                        sendMessageService.menuZayavku(message);
                        break;
                    case "Шось іще":
                        sendMessageService.jastMesege(message,"ну шось там");
                        break;
                    case "Заявка 1":
                        sendMessageService.jastMesege(message,"Всьо тіпа заявка 1 прийннта");
                        break;
                    case "Заявка 2":
                        sendMessageService.jastMesege(message,"Всьо тіпа заявка 2 прийннта");
                        break;
                    case "Заявка 3":
                        sendMessageService.jastMesege(message,"Всьо тіпа заявка 3 прийннта");
                        break;
                    default:
                        sendMessageService.menu(message);
                        break;
                }
            }
        }
    }







    @Autowired
    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }
}
