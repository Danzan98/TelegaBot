package com.telegrambot.handlers;

import com.telegrambot.model.Item;
import com.telegrambot.model.enumeration.WebPage;
import org.jsoup.nodes.Document;

public interface WebPageHandler {
    Item handleWebPage(Document document);

    WebPage getWebPageHandler();
}
