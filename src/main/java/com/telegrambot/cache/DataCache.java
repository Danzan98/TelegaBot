package com.telegrambot.cache;
import com.telegrambot.model.enumeration.BotState;

public interface DataCache {
    void setUsersCurrentBotState(int userId, BotState botState);

    BotState getUsersCurrentBotState(int userId);
}
