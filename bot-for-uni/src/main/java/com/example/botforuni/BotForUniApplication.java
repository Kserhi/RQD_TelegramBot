package com.example.botforuni;

import com.example.botforuni.services.SendMessageService;
import com.example.botforuni.testDemo.UpdateUserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotForUniApplication implements ApplicationRunner {

	@Autowired
	private SendMessageService sendMessageService;

	public static void main(String[] args) {
		SpringApplication.run(BotForUniApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		new UpdateUserStatus(sendMessageService).run();

	}
}








