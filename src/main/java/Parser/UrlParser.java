package Parser;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

import java.io.IOException;
import org.jsoup.nodes.Element;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

public class UrlParser {
    List<String> list = new ArrayList<>();

    public UrlParser(String URL) {
        try {
            Document doc = Jsoup.connect(URL)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
            Element listElemetns = doc.body();
            for(Element element : listElemetns.getAllElements()){
                if (element.className().equals("rate")){
                    list.add(element.text());
                }
            }
            this.setInline();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    private void setInline() {
        List<List<InlineKeyboardButton>> arrayButtons = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (String price: list ) {
            buttons.add(new InlineKeyboardButton().setText(price).setCallbackData(price));
            System.out.println(price);
        }
        arrayButtons.add(buttons);
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(arrayButtons);
    }



}