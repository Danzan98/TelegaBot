package com.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
@EnableScheduling
public class  Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();

        SpringApplication.run(Main.class, args);
    }
}
