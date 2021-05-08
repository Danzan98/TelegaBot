package com.telegrambot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ReplyMessagesService {
    SendMessage getReplyMessage(long chatId, String replyMessage);

    SendMessage getReplyMessage(long chatId, String replyMessage, Object... args);

    String getReplyText(String replyText);

    String getReplyText(String replyText, Object... args);
}
