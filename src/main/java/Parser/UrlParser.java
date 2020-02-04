package Parser;

import Bot.Bot;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

import java.io.IOException;
import org.jsoup.nodes.Element;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

public class UrlParser {
    List<String> list = new ArrayList<>();
    String patternForNumeric = "[^0-9\\.]+";
    public UrlParser(String URL) {

        try {
            Document doc = Jsoup.connect(URL)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
            Element listElemetns = doc.body();
            for(Element element : listElemetns.getAllElements()){
                if (element.className().equals("rate")){
                    list.add(element.text().split(patternForNumeric)[0]);
                }
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public List<String> getList(){
        return list;
    }



}