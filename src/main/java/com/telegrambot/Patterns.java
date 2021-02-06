package com.telegrambot;

public interface Patterns {

    String urlPattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    String commandPattern ="^/[a-zA-Z]*";
    String patternForNumeric = "[^0-9\\.\\s]+";

    String lamoda = ".*www\\.lamoda\\.ru.*";
    String wb = ".*www\\.wildberries\\.ru.*";
    String asos = ".*www\\.asos\\.com.*";
    String ozon = "";

}
