package com.telegrambot.service.impl;

import com.telegrambot.service.LocaleMessageService;
import com.telegrambot.service.ReplyMessagesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@AllArgsConstructor
public class ReplyMessagesServiceImpl implements ReplyMessagesService {

    private LocaleMessageService localeMessageService;

    public SendMessage getReplyMessage(long chatId, String replyMessage) {
        return new SendMessage(String.valueOf(chatId), localeMessageService.getMessage(replyMessage));
    }

    public SendMessage getReplyMessage(long chatId, String replyMessage, Object... args) {
        return new SendMessage(String.valueOf(chatId), localeMessageService.getMessage(replyMessage, args));
    }

    public String getReplyText(String replyText) {
        return localeMessageService.getMessage(replyText);
    }

    public String getReplyText(String replyText, Object... args) {
        return localeMessageService.getMessage(replyText, args);
    }

}
