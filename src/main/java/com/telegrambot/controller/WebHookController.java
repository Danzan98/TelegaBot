package com.telegrambot.controller;

import com.telegrambot.model.UserSubscription;
import com.telegrambot.service.UserSubscriptionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.telegrambot.bot.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;


@Slf4j
@RestController
@AllArgsConstructor
public class WebHookController {
    private final TelegramBot telegramBot;
    private final UserSubscriptionService subscriptionService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramBot.onWebhookUpdateReceived(update);
    }

    @GetMapping(value = "/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserSubscription> getSubscriptions() {
        List<UserSubscription> userTicketsSubscriptions = subscriptionService.getAllSubscriptions();
        return userTicketsSubscriptions;
    }
}
