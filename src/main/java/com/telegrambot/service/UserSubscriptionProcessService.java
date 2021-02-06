package com.telegrambot.service;

import com.telegrambot.bot.TelegramBot;
import com.telegrambot.model.UserSubscription;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserSubscriptionProcessService {

    private UserSubscriptionService subscriptionService;
    private ReplyMessagesService messagesService;
    private TelegramBot telegramBot;

//    @Scheduled(fixedRateString = "${subscriptions.processPeriod}")
    public void processAllUsersSubscriptions() {
        log.info("Выполняю обработку подписок пользователей.");
        subscriptionService.getAllSubscriptions().forEach(this::processSubscription);
        log.info("Завершил обработку подписок пользователей.");
    }
    //TODO
    private void processSubscription(UserSubscription subscription) {
        subscription.getChatId();
    }
    // TODO
    private void sendUserNotification(UserSubscription subscription, String priceChangeMessage) {
        StringBuilder notificationMessage = new StringBuilder(messagesService.getReplyText("subscription.trainTicketsPriceChanges",
                subscription.getLink(), subscription.getPrice())).append(priceChangeMessage);

        notificationMessage.append(messagesService.getReplyText("subscription.lastTicketPrices"));

        telegramBot.sendMessage(subscription.getChatId(), notificationMessage.toString());
    }

}
