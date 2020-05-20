package com.telegramBot.Parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dict {
    public Dict() {}

    private Map<Long, Map<String, Integer>> items = new HashMap<>();
    private HashMap<String, Integer> Items = new HashMap<>();

    public void addItem (Long chatId, String link, Integer price) {
        items.put(chatId, new HashMap<String, Integer>(){{put(link, price);}});
    }
    public void addItem(String link, Integer price){
        Items.put(link, price);
    }

    public Integer getPriceOfItem(String link){
        return Items.get(link);
    }

    public void deleteItem(String link){
        Items.remove(link);
    }

    public Set<Map.Entry<String, Integer>> getAllItems(){
        return Items.entrySet();
    }

    public Set<Map.Entry<String, Integer>> getAllItems(Long chatId){
        System.out.println("ChatId: " + items.get(chatId).entrySet().toString());
        return items.get(chatId).entrySet();
    }


    public boolean existItem(String link){
        return Items.containsKey(link);
    }

}
