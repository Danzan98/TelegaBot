package com.telegramBot.Bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import com.telegramBot.Parser.*;
import com.telegramBot.Patterns;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class Bot extends TelegramLongPollingBot  {

    /* Parser of sites  */
    UrlParser urlParser = new UrlParser();
    /* Initialize a dictionary  */
    Dict dict = new Dict();

    /* Обработка входящего сообщения */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            Message message = update.getMessage();
            // switch case TO DO
            if (message.getText().matches(Patterns.urlPattern)){

                String nameOfSite = message.getText();
                urlParser.parsingSite(nameOfSite);

                sendMsg(message, String.valueOf(urlParser.getCurrentPrice()));

                dict.addItem(message, urlParser.getCurrentPrice());
                notifyUser(message, dict);
            }
            else if (message.getText().matches(Patterns.commandPattern)){
                String feedback = baseCommands(message);
                sendMsg(message, feedback);
            }
            else {
                sendMsg(message, "It's not a URL");
                System.out.println("Incorrect message: " + message);
            }
        }
        else if (update.hasCallbackQuery()){
            System.out.println(update.getCallbackQuery().getData());
        }

    }
    /* Отправляет сообщение пользователю */
    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        setButtons(sendMessage);
        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "Testing558Bot";
    }

    @Override
    public String getBotToken()
    {
        return "1067129429:AAGsji9kFmlbkAXSPxR8yZmxzWVOlZh5aBQ";
    }

    public synchronized void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("/help"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("/shoppingList"));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    // TODO
    public void setInline(List<String> list){
        List<List<InlineKeyboardButton>> arrayButtons = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (String price : list)
            buttons.add(new InlineKeyboardButton().setText(price).setCallbackData(price));
        arrayButtons.add(buttons);
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(arrayButtons);
     //   sendMessage.setReplyMarkup(markupKeyboard);
    }

    /*Уведомление пользователя при изменении цены*/
    int oldPrice;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);
    public void notifyUser(Message message, Dict dictionary){
        Long chatId = message.getChatId();
        oldPrice = 0;
        Runnable checkPrice = () -> {
            for (Item entry : dictionary.getAllItems(chatId)) {
                oldPrice = entry.getPrice();
                urlParser.parsingSite(entry.getLink());
                if (oldPrice > urlParser.getCurrentPrice()) {
                    sendMsg(message, "Wave " + entry.getLink() + " getCurrentPrice: " + urlParser.getCurrentPrice());
                }
            }
        };
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(checkPrice, 0, 3600, TimeUnit.SECONDS);
    }

    /* Базовые команды для пользователя */
    private String baseCommands(Message message){
        Long chatId = message.getChatId();
        String response = "";
       switch (message.getText()){
           case "/start":
               response = "Hi! I'm a bot which will notify you when the price of specified clothes or shoes is changed";
               break;
           case "/help":
               response = "Specify a link to the product for adding it to the shopping list";
               break;
           case "/shoppingList":
               if (!dict.isEmpty()){
                 for ( Item entry : dict.getAllItems(chatId)) {
                   System.out.println("Product: " + entry.getLink()  + " Price: " + entry.getPrice() + "\n");
                   response = response + "Product: " + entry.getLink() + " Price: " + entry.getPrice() + "\n";
                 }
               }
               else
                   response = "Your shopping list is empty";
               break;
           default:
               response = "I don't know this command.";
               break;
       }
       return response;
    }


}
