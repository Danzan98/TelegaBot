package com.telegrambot.service;

import com.telegrambot.bot.TelegramBot;
import com.telegrambot.model.Item;
import com.telegrambot.model.UserSubscription;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserSubscriptionProcessService {

    private final UserSubscriptionService subscriptionService;
    private final ReplyMessagesService messagesService;
    private final TelegramBot telegramBot;
    private final WebParserService webParserService;

    @Scheduled(fixedRateString = "${subscriptions.processPeriod}")
    public void processAllUsersSubscriptions() {
        log.info("Выполняю обработку подписок пользователей.");
        if (!subscriptionService.getAllSubscriptions().isEmpty()) {
            subscriptionService.getAllSubscriptions().forEach(this::processSubscription);
        }
        log.info("Завершил обработку подписок пользователей.");
    }

    private void processSubscription(UserSubscription subscription) {
        Item item = webParserService.getItem(subscription.getLink());
        if (item.getPrice() < subscription.getPrice()) {
            subscription.setPrice(item.getPrice());
            StringBuilder notificationMessage = new StringBuilder(messagesService.getReplyText("subscription.trainTicketsPriceChanges",
                    subscription.getName(), subscription.getPrice(), subscription.getLink()));
            subscriptionService.saveUserSubscription(subscription);
            telegramBot.sendMessage(subscription.getChatId(), notificationMessage.toString());
        }
    }

}
