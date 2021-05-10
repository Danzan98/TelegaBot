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
public class Farfetch implements WebPageHandler {

    @Override
    public Item handleWebPage(Document document) {
        try {
            Element product = document.selectFirst("h1._d561bf");
            Element elementBrand = product.child(0);
            Element elementItem = product.child(1);
            Element elementPrice = document.selectFirst("div._7dad7e");
            String brand = elementBrand.text();
            String itemName = elementItem.text();
            Double price = Double.valueOf(elementPrice.text().replaceAll("\\D+", ""));
            return Item
                    .builder()
                    .brand(brand)
                    .name(itemName)
                    .price(price)
                    .build();
        }
        catch (Exception e) {
            log.error("Error while parsing farfetch " + e);
            return null;
        }
    }

    @Override
    public WebPage getWebPageHandler() {
        return WebPage.FARFETCH;
    }
}
