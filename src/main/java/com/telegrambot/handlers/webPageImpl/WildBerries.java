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
public class WildBerries implements WebPageHandler {

    @Override
    public Item handleWebPage(Document document) {
        try {
            Element elementBrand = document.selectFirst("span.brand");
            Element elementItem = document.selectFirst("span.name");
            Element elementPrice = document.selectFirst("span.final-cost");
            String brand = elementBrand.ownText();
            String itemName = elementItem.ownText();
            Double price = Double.valueOf(elementPrice.text().replaceAll("\\D+", ""));
            return Item
                    .builder()
                    .brand(brand)
                    .name(itemName)
                    .price(price)
                    .build();
        }
        catch (Exception e) {
            log.error("Error while parsing wildBerries " + e);
            return null;
        }
    }

    @Override
    public WebPage getWebPageHandler() {
        return WebPage.WILDBERRIES;
    }
}
