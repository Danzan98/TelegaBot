package com.telegrambot.handlers.inputMessageImpl;

import com.telegrambot.bot.TelegramBot;
import com.telegrambot.cache.DataCache;
import com.telegrambot.cache.UserDataCache;
import com.telegrambot.handlers.InputMessageHandler;
import com.telegrambot.model.UserSubscription;
import com.telegrambot.model.enumeration.BotState;
import com.telegrambot.model.enumeration.CallbackQueryType;
import com.telegrambot.service.ReplyMessagesService;
import com.telegrambot.service.impl.ReplyMessagesServiceImpl;
import com.telegrambot.service.UserSubscriptionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

@Component
public class SubscriptionsMenuHandler implements InputMessageHandler {
    private final UserSubscriptionService subscribeService;
    private final TelegramBot telegramBot;
    private final DataCache userDataCache;
    private final ReplyMessagesService messagesService;

    public SubscriptionsMenuHandler(UserSubscriptionService subscribeService,
                                    UserDataCache userDataCache,
                                    ReplyMessagesServiceImpl messagesService,
                                    @Lazy TelegramBot telegramBot) {
        this.subscribeService = subscribeService;
        this.messagesService = messagesService;
        this.userDataCache = userDataCache;
        this.telegramBot = telegramBot;
    }

    @Override
    public SendMessage handle(Message message) {

        Optional<List<UserSubscription>> usersSubscriptions = subscribeService.getUserSubscriptions(message.getChatId());

        if (!usersSubscriptions.isPresent()) {
            userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.SHOW_MAIN_MENU);
            return messagesService.getReplyMessage(message.getChatId(), "reply.subscriptions.userHasNoSubscriptions");
        }

        for (UserSubscription subscription : usersSubscriptions.get()) {
            String subscriptionInfo = messagesService.getReplyText("subscription.itemInfo",
            subscription.getName(), subscription.getPrice(), subscription.getLink());

            //Посылаем кнопку "Отписаться" с ID подписки
            String unsubscribeData = String.format("%s|%s", CallbackQueryType.UNSUBSCRIBE, subscription.getId());
            telegramBot.sendInlineKeyBoardMessage(message.getChatId(), subscriptionInfo, "Отписаться", unsubscribeData);
        }

        userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.SHOW_MAIN_MENU);

        return messagesService.getReplyMessage(message.getChatId(), "reply.subscriptions.listLoaded");
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_SUBSCRIPTIONS_MENU;
    }
}
