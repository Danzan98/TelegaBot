package com.telegrambot.bot;

import com.telegrambot.handlers.InputMessageHandler;
import com.telegrambot.model.enumeration.BotState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    private final Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState currentState, Message message) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        if (isItemSearchState(currentState)) {
            return messageHandlers.get(BotState.ADD_ITEM);
        }

        return messageHandlers.get(currentState);
    }

    private boolean isItemSearchState(BotState currentState) {
        switch (currentState) {
            case ADD_ITEM:
            case SEARCH_COST_STARTED:
            case SEARCH_COST_FINISHED:
                return true;
            default:
                return false;
        }
    }

}
