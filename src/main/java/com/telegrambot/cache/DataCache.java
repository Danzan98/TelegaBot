package com.telegrambot.cache;
import com.telegrambot.model.enumeration.BotState;

public interface DataCache {
    void setUsersCurrentBotState(Long userId, BotState botState);

    BotState getUsersCurrentBotState(Long userId);
}
