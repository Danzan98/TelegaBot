package com.telegrambot.appconfig;

import com.telegrambot.bot.TelegramBot;
import com.telegrambot.bot.TelegramBotFacade;
import com.telegrambot.botconfig.TelegramBotConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private TelegramBotConfig botConfig;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public TelegramBot telegramBot(TelegramBotFacade telegramFacade) {
        TelegramBot telegramBot = new TelegramBot(telegramFacade);
        return telegramBot;
    }

}
