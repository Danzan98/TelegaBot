package com.telegrambot.botconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "telegrambot")
@Getter
@Setter
public class TelegramBotConfig {
    private String webHookPath;
    private String userName;
    private String botToken;
}
