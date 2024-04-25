package com.example.botforuni.utils;

import com.example.botforuni.domain.BotUser;
import com.example.botforuni.services.BotUserDataService;
import com.example.botforuni.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class UpdateUserStatus {
    @Autowired
    private SendMessageService sendMessageService;

    @Autowired
    private ScheduledExecutorService scheduler;



    private Runnable task;

    @PostConstruct
    public void init() {
        task = () -> {
            try {
                sendInfoAboutStatusUserStatement();
            } catch (Exception e) {
                System.out.println("Exception " + e);
            }
        };
        scheduler.scheduleAtFixedRate(task, 0, 3, TimeUnit.HOURS); // Планування завдання кожні 12 годин
    }

    @PreDestroy
    public void destroy() {
        scheduler.shutdown();
    }

    private void sendInfoAboutStatusUserStatement() {
        List<BotUser> botUserList= BotUserDataService.getTrueUsers();
        botUserList.forEach(botUser ->
                sendMessageService.
                        sendMessage(botUser.getTelegramId(),
                                botUser.getStatement()+ Constans.STATUSOFSTATEMENT
                        ));
        BotUserDataService.updateIsRedy(botUserList);
    }
}
