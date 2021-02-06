package com.telegrambot.bot;

import com.telegrambot.handlers.CallbackQueryHandler;
import com.telegrambot.model.enumeration.CallbackQueryType;
import com.telegrambot.service.ReplyMessagesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CallbackQueryFacade {
    private final ReplyMessagesService messagesService;
    private final List<CallbackQueryHandler> callbackQueryHandlers;

    public SendMessage processCallbackQuery(CallbackQuery usersQuery) {
        CallbackQueryType usersQueryType = CallbackQueryType.valueOf(usersQuery.getData().split("\\|")[0]);

        Optional<CallbackQueryHandler> queryHandler = callbackQueryHandlers.stream().
                filter(callbackQuery -> callbackQuery.getHandlerQueryType().equals(usersQueryType)).findFirst();

        return queryHandler.map(handler -> handler.handleCallbackQuery(usersQuery)).
                orElse(messagesService.getReplyMessage(usersQuery.getMessage().getChatId(), "reply.query.failed"));
    }
}

