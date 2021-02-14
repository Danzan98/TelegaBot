package com.telegrambot.handlers.webPageImpl;

import com.telegrambot.handlers.WebPageHandler;
import com.telegrambot.model.Item;
import com.telegrambot.model.enumeration.WebPage;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Ozon implements WebPageHandler {
    @Override
    public Item handleWebPage(Document document) {
        try {
            Element product = document.selectFirst("h1.b3a8");
            Element elementPrice = document.selectFirst("span.c8q7");
            String brand = product.text();
            String itemName = product.text();
            Double price = Double.valueOf(elementPrice.text().replaceAll("\\D+", ""));
            return new Item(null, brand, itemName, price);
        }
        catch (Exception e) {
            log.error("Error while parsing ozon " + e);
            return null;
        }
    }

    @Override
    public WebPage getWebPageHandler() {
        return WebPage.OZON;
    }
}
