package com.telegrambot.service;

import com.telegrambot.bot.TelegramBot;
import com.telegrambot.model.Item;
import com.telegrambot.model.UserSubscription;
import com.telegrambot.service.impl.WebParserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserSubscriptionProcessService {

    private final UserSubscriptionService subscriptionService;
    private final ReplyMessagesService messagesService;
    private final TelegramBot telegramBot;
    private final WebParserServiceImpl webParserService;

    @Scheduled(fixedRateString = "${subscriptions.processPeriod}")
    public void processAllUsersSubscriptions() {
        log.info("Выполняю обработку подписок пользователей.");
        if (!subscriptionService.getAllSubscriptions().isEmpty()) {
            subscriptionService.getAllSubscriptions().forEach(this::processSubscription);
        }
        log.info("Завершил обработку подписок пользователей.");
    }

    private void processSubscription(UserSubscription subscription) {
        Optional<Item> item = webParserService.getItem(subscription.getLink());
        if (item.isPresent() && item.get().getPrice() < subscription.getPrice()) {
            subscription.setPrice(item.get().getPrice());
            StringBuilder notificationMessage = new StringBuilder(messagesService.getReplyText("subscription.trainTicketsPriceChanges",
                    subscription.getName(), subscription.getPrice(), subscription.getLink()));
            subscriptionService.saveUserSubscription(subscription);
            telegramBot.sendMessage(subscription.getChatId(), notificationMessage.toString());
        }
    }

}
