package com.example.botforuni.testData;

import com.example.botforuni.domain.BotUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConf {
    private final BotUser botUser;

    public UserConf(BotUser botUser) {
        this.botUser = botUser;
    }

    @Bean
    CommandLineRunner commandLineRunner(UserData userData) {
        return args -> {
            User user = new User();
            user.setName(botUser.getFullName());
            userData.saveAll(
                    List.of(user)
            );

        };
    }

}
