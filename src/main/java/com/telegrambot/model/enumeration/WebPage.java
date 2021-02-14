package com.telegrambot.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WebPage {
    LAMODA(".*\\.lamoda\\.ru.*"),
    WILDBERRIES(".*\\.wildberries\\.ru.*"),
//    ASOS(".*\\.asos\\.com.*"),
    FARFETCH(".*\\.farfetch\\.com.*"),
    OZON(".*\\.ozon.ru.*");

    private final String url;
}
