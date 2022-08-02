package senkohotel.hotelbot.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import senkohotel.hotelbot.Main;
import senkohotel.hotelbot.commands.CommandList;

public class MessageListener extends ListenerAdapter {
    public void onMessageReceived(@NotNull MessageReceivedEvent msg) {
        String content = msg.getMessage().getContentRaw().toLowerCase();
        for (String prefix : Main.prefix) {
            if (content.startsWith(prefix))
                CommandList.check(msg, prefix);
        }
    }
}