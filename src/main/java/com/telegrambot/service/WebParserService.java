package com.telegrambot.service;

import com.telegrambot.model.Item;

import java.util.Optional;

public interface WebParserService {
    Optional<Item> getItem(String link);
}
