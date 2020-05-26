package com.telegramBot.Parser;

import org.telegram.telegrambots.api.objects.Message;

import java.util.*;

public class Dict {

    public Dict() {}

    private static Map<Long, List<Item>> items = new HashMap<>();

    public void addItem (Message message, Integer price) {
        Long chatId = message.getChatId();
        String link = message.getText();
        items.computeIfAbsent(chatId, k -> new ArrayList<>()).add(new Item(link, price));
    }

    // TODO
    public void deleteItem(Message message){
        Long chatId = message.getChatId();
        String link = message.getText();
        items.remove(link);
    }

    public List<Item> getAllItems(Long chatId){
        return items.get(chatId);
    }

    // TODO
    public boolean existItem(Long chatId, String link){
        return true; //items.get(chatId);
    }

}
