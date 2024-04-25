package com.example.botforuni.services;

import com.example.botforuni.domain.BotUser;
import com.example.botforuni.utils.Constans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class UpdateUserStatusService {
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
