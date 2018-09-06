package com.konex.elektrik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.LongPollingBot;

import java.util.logging.Logger;

@SpringBootApplication
public class ElektrikApplication extends SpringBootServletInitializer {

	final static Logger log = Logger.getLogger(ElektrikApplication.class.getName());
	public static void main(String[] args) {

		//	Initialize ApiContext
		ApiContextInitializer.init();

		// Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();


		ConfigurableApplicationContext context = SpringApplication.run(ElektrikApplication.class, args);
		System.err.println("=======================================================================================" +
				"\nSERVER STARTED");
		// Register our bot
        try {
            botsApi.registerBot((LongPollingBot) context.getBean("konexBotTest"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

		log.info("SERVER STARTED");
	}
}
