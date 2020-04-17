package Parser;

import Bot.Bot;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

import java.io.IOException;
import org.jsoup.nodes.Element;

public class UrlParser {

    String patternForNumeric = "[^0-9\\.\\s]+";
    String nameOfElement = "";
    private String URL;
    private int currentPrice;
    public UrlParser() { }

    public void parsingSite(String URL) {
        this.URL = URL;
        if (URL.matches(".*www\\.lamoda\\.ru.*")) {
            nameOfElement = "ii-product__price-current ii-product__price-current_big";
        }
        else  if (URL.matches(".*www\\.wildberries\\.ru.*")) {
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
            Element listElemetns = doc.body();
            for(Element element : listElemetns.getAllElements()) {
                if (element.className().equals(nameOfElement)) {
                    currentPrice = Integer.parseInt(element.text().split(patternForNumeric)[0].replaceAll("\\s", ""));
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