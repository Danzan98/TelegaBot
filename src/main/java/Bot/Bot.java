package Bot;

import Parser.Dict;
import Parser.Pusher;
import Parser.UrlParser;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private String urlPattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private String commandPattern ="^/[a-z]*";
    UrlParser urlParser = new UrlParser();
    Pusher pusher = new Pusher();
    SendMessage sendMessage = new SendMessage();
    Dict dict = new Dict();
    /* Обработка входящего сообщения */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            Message message = update.getMessage();
            // switch case TO DO
            if (message.getText().matches(urlPattern)){
                urlParser.parsingSite(message.getText());
                dict.addItem(message.getText(), urlParser.getCurrentPrice());
                pusher.pushNotifies(message, dict);
            }
            else if (message.getText().matches(commandPattern)){
                sendMsg(message, "It's a command");
            }
            else {
                sendMsg(message, "It's not a URL");
            }
        }
        else if (update.hasCallbackQuery()){
            System.out.println(update.getCallbackQuery().getData());
        }

    }
    /* Отправляет сообщение пользователю */
    public void sendMsg(Message message, String text) {
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
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
    public String getBotToken() {
        return "1067129429:AAGsji9kFmlbkAXSPxR8yZmxzWVOlZh5aBQ";
    }
    public synchronized void setButtons() {
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
        keyboardSecondRow.add(new KeyboardButton("/settings"));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void setInline(List<String> list){
        List<List<InlineKeyboardButton>> arrayButtons = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (String price : list)
            buttons.add(new InlineKeyboardButton().setText(price).setCallbackData(price));
        arrayButtons.add(buttons);
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(arrayButtons);
        sendMessage.setReplyMarkup(markupKeyboard);
    }


}
