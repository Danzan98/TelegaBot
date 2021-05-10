package com.telegrambot.service.impl;


import com.telegrambot.handlers.WebPageHandler;
import com.telegrambot.model.Item;
import com.telegrambot.service.WebParserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebParserServiceImpl implements WebParserService {

    private final List<WebPageHandler> webPageHandlers;
    private final RestTemplate restTemplate;

    public Optional<Item> getItem(String link) {
        try {
            Optional<WebPageHandler> pageHandler =  webPageHandlers.stream()
                    .filter(webPageHandler -> {
                        Pattern pattern = Pattern.compile(webPageHandler.getWebPageHandler().getUrl(), Pattern.CASE_INSENSITIVE);
                        return pattern.matcher(link).find();
                    })
                    .findAny();
            if (pageHandler.isEmpty()) {
                log.info("Couldn't find link in the pool, " + link);
                return Optional.empty();
            }
            WebPageHandler webPageHandler = pageHandler.get();
            ResponseEntity<String> response = restTemplate.getForEntity(link, String.class);
            Document doc = Jsoup.parse(Objects.requireNonNull(response.getBody()));
            Item item = webPageHandler.handleWebPage(doc);
            item.setLink(link);
            return Optional.of(item);
        }
        catch (Exception e) {
            log.error("Error while parsing " + link + " message: " + e.getMessage());
            return Optional.empty();
        }
    }
}
