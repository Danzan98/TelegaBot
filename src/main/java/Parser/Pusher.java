package Parser;

import Bot.Bot;
import org.telegram.telegrambots.api.objects.Message;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Pusher {

    public Pusher() { };
    UrlParser parser = new UrlParser();
    Bot bot = new Bot();
    int oldPrice = 0;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    public void pushNotifies(Message msg, Dict dictionary) {
        Runnable checkPrice = () -> {
            for (Map.Entry<String, Integer> entry : dictionary.getAllItems()) {
                oldPrice = entry.getValue();
                parser.parsingSite(entry.getKey());
                if (oldPrice > parser.getCurrentPrice()){
                    bot.sendMsg(msg, "Wave" + entry.getKey());
                }
            }
        };
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(checkPrice, 0, 3600, TimeUnit.SECONDS);
    }


}
