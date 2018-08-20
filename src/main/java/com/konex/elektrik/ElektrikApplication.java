package com.konex.elektrik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.logging.Logger;

@SpringBootApplication
public class ElektrikApplication extends SpringBootServletInitializer {

	final static Logger log = Logger.getLogger(ElektrikApplication.class.getName());
	public static void main(String[] args) {

		// Initialize Api Context
//        ApiContextInitializer.init();
//
//        // Instantiate Telegram Bots API
//        TelegramBotsApi botsApi = new TelegramBotsApi();


		SpringApplication.run(ElektrikApplication.class, args);
		System.err.println("=======================================================================================" +
				"\nSERVER STARTED");
		// Register our bot
//        try {
//            botsApi.registerBot((LongPollingBot) context.getBean("konexBotTest"));
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }

		log.info("SERVER STARTED");
	}
}
