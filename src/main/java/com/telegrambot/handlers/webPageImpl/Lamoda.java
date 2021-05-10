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
public class Lamoda implements WebPageHandler {

    @Override
    public Item handleWebPage(Document document) {
        try {
            Element elementTitle = document.selectFirst("h1.product-title__brand-name");
            Element elementPrice = document.selectFirst("div.product-prices");
            String brand = elementTitle.ownText();
            String itemName = elementTitle.child(0).text();
            int amountOfChildren = elementPrice.childrenSize();
            Double price = Double.valueOf(elementPrice.child(amountOfChildren - 1).text().replaceAll("\\D+", ""));
            return Item
                    .builder()
                    .brand(brand)
                    .name(itemName)
                    .price(price)
                    .build();
        }
        catch (Exception e) {
            log.error("Error while parsing lamoda " + e);
            return null;
        }
    }

    @Override
    public WebPage getWebPageHandler() {
        return WebPage.LAMODA;
    }
}
