package com.example.botforuni.utils;



import com.example.botforuni.domain.BotUser;
import com.example.botforuni.services.BotUserDataService;
import com.example.botforuni.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UpdateUserStatus {
    @Autowired
    SendMessageService sendMessageService;

    @Autowired
    public void sendInfoAboutStatusUserStatement() {
        while (true){
            try {
                List<BotUser> botUserList= BotUserDataService.getTrueUsers();
                botUserList.forEach(botUser ->
                        sendMessageService.
                                sendMessage(botUser.getTelegramId(),
                                        botUser.getStatement()+ " готова.\n" +
                                                "Зверніться,будь-ласка,в деканат."

                                ));
                BotUserDataService.updateIsRedy(botUserList);

                //кожні 12 годин
                Thread.sleep(43200000);


            } catch (Exception e) {
                System.out.println("Exception " + e);
            }
        }


    }
}
