package com.telegrambot.handlers.callbackQueryImpl;

import com.telegrambot.handlers.CallbackQueryHandler;
import com.telegrambot.model.enumeration.CallbackQueryType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class SubscribeItemQueryHandler implements CallbackQueryHandler {

    @Override
    public SendMessage handleCallbackQuery(CallbackQuery callbackQuery) {
        final long chatId = callbackQuery.getMessage().getChatId();
        return null;
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return CallbackQueryType.SUBSCRIBE;
    }
}
