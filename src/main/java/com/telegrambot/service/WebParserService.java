package com.telegrambot.service;


import com.telegrambot.handlers.WebPageHandler;
import com.telegrambot.model.Item;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Slf4j
public class WebParserService {

    private final List<WebPageHandler> webPageHandlers;

    public Item getItem(String link) {
        Item item;

        try {
            Document doc = Jsoup.connect(link)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
            Optional<WebPageHandler> pageHandler =  webPageHandlers.stream()
                    .filter(webPageHandler -> {
                        Pattern pattern = Pattern.compile(webPageHandler.getWebPageHandler().getUrl(), Pattern.CASE_INSENSITIVE);
                        return pattern.matcher(link).find();
                    })
                    .findAny();
            if (pageHandler.isEmpty()) {
                log.info("Couldn't find link in the pool, " + link);
                return null;
            }

            item = pageHandler.get().handleWebPage(doc);
            item.setLink(link);
        }
        catch (IOException e) {
            return null;
        }

        return item;
    }
}
