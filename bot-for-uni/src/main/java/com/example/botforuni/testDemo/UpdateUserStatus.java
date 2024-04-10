package com.example.botforuni.testDemo;



import com.example.botforuni.domain.BotUser;
import com.example.botforuni.services.BotUserDataService;
import com.example.botforuni.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateUserStatus {
    SendMessageService sendMessageService;

    public UpdateUserStatus(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    public void run() {
        while (true){
            try {
                Thread.sleep(3000);
                List<BotUser> botUserList= BotUserDataService.getTrueUsers();
                botUserList.forEach(botUser ->
                        sendMessageService.
                                sendMessage(botUser.getTelegramId(),
                                        botUser.getStatement()+ " готова.\n" +
                                                "Зверніться,будь-ласка,в деканат."

                                ));
                BotUserDataService.updateIsRedy(botUserList);


            } catch (Exception e) {
                System.out.println("Exception " + e);
            }
        }


    }
}
