package com.telegramBot.Parser;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;

public class Dict {

    public Dict() {}

    private static Map<Long, List<Item>> items = new HashMap<>();
    private static boolean exist = false;

    public void addItem (Message message, Integer price) {
        Long chatId = message.getChatId();
        String link = message.getText();
        exist = false;
        if (!items.isEmpty())
            items.get(chatId).forEach(item -> existItem(item.getLink(), link));
        if (!exist)
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

    public boolean isEmpty(){
        return items.isEmpty();
    }

    public boolean existItem(String linkInList, String searchLink){
        if (linkInList.equals(searchLink) && !exist)
            exist = true;
        return exist;
    }

}
