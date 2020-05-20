package com.telegramBot.Parser;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

import java.io.IOException;
import org.jsoup.nodes.Element;
import com.telegramBot.Patterns;
//TODO Write parser for reading prices with comma
public class UrlParser {

    String nameOfElement = "";
    private String URL;
    private int currentPrice;
    public UrlParser() { }

    public void parsingSite(String URL) {
        this.URL = URL;
        if (URL.matches(Patterns.lamoda)) {
            nameOfElement = "ii-product__price-current ii-product__price-current_big";
        }
        else  if (URL.matches(Patterns.wb)) {
            nameOfElement = "final-cost";
        }
        else if (URL.matches("asos")){
            //TODO
        }
        else if (URL.matches("farfetch")){
            //TODO
        }
        currentPrice = 0;
        try {
            Document doc = Jsoup.connect(URL)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
            for(Element element : doc.body().getAllElements()) {
                if (element.className().equals(nameOfElement)) {
                    currentPrice = Integer.parseInt(element.text().split(Patterns.patternForNumeric)[0].replaceAll("\\s", ""));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCurrentPrice() {
        return currentPrice;
    }
}