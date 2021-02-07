package com.telegrambot.handlers.callbackQueryImpl;

import com.telegrambot.bot.TelegramBot;
import com.telegrambot.handlers.CallbackQueryHandler;
import com.telegrambot.model.UserSubscription;
import com.telegrambot.model.enumeration.CallbackQueryType;
import com.telegrambot.service.ReplyMessagesService;
import com.telegrambot.service.UserSubscriptionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

@Component
public class UnsubscribeItemQueryHandler implements CallbackQueryHandler {
    private final UserSubscriptionService subscriptionService;
    private final ReplyMessagesService messagesService;
    private final TelegramBot telegramBot;

    public UnsubscribeItemQueryHandler(UserSubscriptionService subscriptionService,
                                              ReplyMessagesService messagesService,
                                              @Lazy TelegramBot telegramBot) {
        this.subscriptionService = subscriptionService;
        this.messagesService = messagesService;
        this.telegramBot = telegramBot;
    }

    @Override
    public SendMessage handleCallbackQuery(CallbackQuery callbackQuery) {
        final long chatId = callbackQuery.getMessage().getChatId();

        String link = findLink(callbackQuery.getMessage().getText());

        Optional<UserSubscription> optionalUserSubscription = subscriptionService.getUserSubscriptionByIdAndLink(chatId, link);
        if (optionalUserSubscription.isEmpty()) {
            return messagesService.getReplyMessage(chatId, "reply.subscriptions.userHasNoSubscriptions");
        }

        UserSubscription userSubscription = optionalUserSubscription.get();
        subscriptionService.deleteUserSubscription(userSubscription.getId());

        return messagesService.getReplyMessage(chatId, "reply.query.item.unsubscribed", userSubscription.getName());
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return CallbackQueryType.UNSUBSCRIBE;
    }

    private String findLink (String text) {
        String[] subString = text.split("\\s");
        return subString[subString.length - 1];
    }
}
