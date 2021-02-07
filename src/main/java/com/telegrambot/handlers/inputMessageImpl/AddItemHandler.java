package com.telegrambot.handlers.inputMessageImpl;

import com.telegrambot.cache.UserDataCache;
import com.telegrambot.handlers.InputMessageHandler;
import com.telegrambot.mapper.UserSubscriptionMapper;
import com.telegrambot.model.Item;
import com.telegrambot.model.UserSubscription;
import com.telegrambot.model.enumeration.BotState;
import com.telegrambot.service.ReplyMessagesService;
import com.telegrambot.service.UserSubscriptionService;
import com.telegrambot.service.WebParserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@AllArgsConstructor
public class AddItemHandler implements InputMessageHandler {
    private final UserDataCache userDataCache;
    private final WebParserService webParserService;
    private final UserSubscriptionService userSubscriptionService;
    private final ReplyMessagesService messagesService;
    private final UserSubscriptionMapper subscriptionMapper;

    @Override
    public SendMessage handle(Message message) {
        userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.SEARCH_COST_STARTED);
        Item item = webParserService.getItem(message.getText());
        userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.SEARCH_COST_FINISHED);

        if (item == null) {
            userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.ADD_ITEM);
            return messagesService.getReplyMessage(message.getChatId(), "reply.query.failed");
        }

        if (userSubscriptionService.getUserSubscriptionByIdAndLink(message.getChatId(),
                item.getLink()).isPresent())
            return messagesService.getReplyMessage(message.getChatId(), "reply.query.item.userHasSubscription");

        UserSubscription subscription = subscriptionMapper.toSubscription(item);
        subscription.setChatId(message.getChatId());
        userSubscriptionService.saveUserSubscription(subscription);
        return messagesService.getReplyMessage(message.getChatId(),
                "reply.query.item.subscribed", subscription.getName());
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ADD_ITEM;
    }
}
