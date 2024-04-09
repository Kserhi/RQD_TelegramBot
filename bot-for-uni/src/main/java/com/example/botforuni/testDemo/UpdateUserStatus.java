package com.example.botforuni.testDemo;



import com.example.botforuni.services.BotUserDataService;
import com.example.botforuni.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserStatus{
    SendMessageService sendMessageService;

    public UpdateUserStatus(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    public void run() {
        while (true){
            try {
                Thread.sleep(3000);
//                BotUserDataService.getTrueUsers().forEach(botUser -> System.out.println(botUser.getFullName()));
                BotUserDataService.getTrueUsers().forEach(botUser -> sendMessageService.sendMessage(botUser.getTelegramId(),"Ваша довідка готова"));

            } catch (Exception e) {
                System.out.println("Exception " + e);
            }
        }


    }
}
