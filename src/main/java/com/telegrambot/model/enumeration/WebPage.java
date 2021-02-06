package com.telegrambot.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WebPage {
    LAMODA(".*www\\.lamoda\\.ru.*"),
    WILDBERRIES(".*www\\.wildberries\\.ru.*"),
    ASOS(".*www\\.asos\\.com.*"),
    FARFETCH("");

    private final String url;
}
