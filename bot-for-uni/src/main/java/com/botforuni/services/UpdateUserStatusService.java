package com.botforuni.services;

import com.botforuni.domain.Position;
import com.botforuni.domain.Statement;
import com.botforuni.domain.StatementInfo;
import com.botforuni.domain.TelegramUserCache;
import com.botforuni.utils.Constants;
import com.botforuni.utils.HashFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UpdateUserStatusService {

    @Autowired
    private TelegramUserService telegramUserService;

    @Autowired
    private SendMessageService sendMessageService;

    @Autowired
    private StatementInfoService statementInfoService;

    @Autowired
    private ScheduledExecutorService scheduler;

    private Runnable task;

    @PostConstruct
    public void init() {
        task = () -> {
            try {
                sendNotificationAboutStatementStatus();
            } catch (Exception e) {
                log.error("Проблеми із сповіщенням про статус заявки: ", e);
            }
        };
        // Планування завдання кожні 12 годин
        scheduler.scheduleAtFixedRate(
                task,
                0,
                Constants.TIMETOSTATEMENTUPDATE,
                TimeUnit.SECONDS);
    }

    @PreDestroy
    public void destroy() {
        scheduler.shutdown();
        log.info("Планувальник завдань зупинено.");
    }

    private void sendNotificationAboutStatementStatus() {
        List<StatementInfo> infoList = statementInfoService.getReadyStatement();
        List<StatementInfo> readyInfoList = new ArrayList<>();

        if (!infoList.isEmpty()) {
            infoList.forEach(statementInfo -> {
                try {
                    Statement statement = statementInfo.getStatement();
                    TelegramUserCache telegramUser = telegramUserService
                            .findById(statement.getTelegramId())
                            .orElseThrow(() -> new IllegalArgumentException("Користувача не знайдено: " + statement.getTelegramId()));

                    if (telegramUser.getPosition() == Position.NONE) {
                        ////todo лги
                        if (statementInfoService.checkFileExistence(statement.getId())){
                            sendMessageService.sendInfoAboutReadyStatementWithFile(statement);
                        }else {
                            sendMessageService.sendInfoAboutReadyStatement(statement);
                        }
                        readyInfoList.add(statementInfo);



                    }
                } catch (Exception e) {
                    log.error("Помилка під час відправки повідомлення про готовність заяви для користувача", e);
                }
            });

            // Оновлюємо статус для готових заявок
            if (!readyInfoList.isEmpty()) {
                readyInfoList.forEach(statementInfo -> statementInfo.setReady(true));
                statementInfoService.saveAll(readyInfoList);
                log.info("Статус готових заявок оновлено для {} заяв.", readyInfoList.size());
            }

        } else {
            log.info("Немає нових заявок для оновлення статусу.");
        }
    }
}
