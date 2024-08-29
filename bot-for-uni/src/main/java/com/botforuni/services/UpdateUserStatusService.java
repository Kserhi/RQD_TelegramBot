package com.botforuni.services;

import com.botforuni.domain.Position;
import com.botforuni.domain.Statement;
import com.botforuni.domain.StatementInfo;
import com.botforuni.domain.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class UpdateUserStatusService {
    @Autowired
    private TelegramUserService telegramUserService;
    @Autowired
    private SendMessageService sendMessageService;

    @Autowired
    private ScheduledExecutorService scheduler;



    private Runnable task;

    @PostConstruct
    public void init() {
        task = () -> {
            try {
                sendNotificationAboutStatementStatus();
            } catch (Exception e) {
                System.out.println("Проблеми із сповіщення про статус заявки" + e);
            }
        };
        scheduler.scheduleAtFixedRate(task, 0, 3, TimeUnit.SECONDS); // Планування завдання кожні 12 годин
    }

    @PreDestroy
    public void destroy() {
        scheduler.shutdown();
    }

    private void sendNotificationAboutStatementStatus() {

        List<StatementInfo>infoList=StatementInfoService.getReadyStatement();
        List<StatementInfo>readyInfoList=new ArrayList<>();

        if (!infoList.isEmpty()) {
            infoList.forEach(statementInfo -> {
                Statement st=statementInfo.getStatement();
                TelegramUser tgUser=telegramUserService.findById(st.getTelegramId());

                if (tgUser.getPosition()== Position.NONE){
                    sendMessageService.sendInfoAboutReadyStatement(st);
                    readyInfoList.add(statementInfo);
                }

            });

            if (!readyInfoList.isEmpty()){
                readyInfoList.forEach(statementInfo ->statementInfo.setReady(true));
            }

            StatementInfoService.saveAll(readyInfoList);

        }


    }
}
