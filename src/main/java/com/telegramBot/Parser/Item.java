package com.telegramBot.Parser;

public class Item {

    private String link;
    private Integer price;

    public Item (String link, Integer price){
        this.link = link;
        this.price = price;
    }

    public String getLink(){
        return link;
    }

    public Integer getPrice(){
        return price;
    }

}
