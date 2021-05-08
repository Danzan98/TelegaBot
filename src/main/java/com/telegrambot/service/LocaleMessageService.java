package com.telegrambot.service;

public interface LocaleMessageService {
    String getMessage(String message);

    String getMessage(String message, Object... args);
}
