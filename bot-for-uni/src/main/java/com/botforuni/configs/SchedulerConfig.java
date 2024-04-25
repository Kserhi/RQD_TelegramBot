package com.botforuni.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class SchedulerConfig {

    @Bean(destroyMethod="shutdown")
    public ScheduledExecutorService taskScheduler() {
        return Executors.newScheduledThreadPool(1); // Вказуйте кількість потоків, яка вам потрібна
    }
}
