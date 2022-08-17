package senkohotel.hotelbot.listeners;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import senkohotel.hotelbot.Main;
import senkohotel.hotelbot.commands.CommandList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MessageListener extends ListenerAdapter {
    String[] welcomeEmotes = {
            "792071753228353548",
            "844802666503995392",
            "852891485959356447",
            "835934243011035157",
            "792071751899021335",
            "841889936785276988",
            "818047092085227530"
    };

    public void onMessageReceived(@NotNull MessageReceivedEvent msg) {
        String content = msg.getMessage().getContentRaw().toLowerCase();
        for (String prefix : Main.prefix) {
            if (content.startsWith(prefix))
                CommandList.check(msg, prefix);
        }

        if (content.startsWith("welcome")) {
            List<String> picks = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Random rng = new Random();
                String pick = welcomeEmotes[rng.nextInt(welcomeEmotes.length)];
                while (picks.contains(pick)) {
                    pick = welcomeEmotes[rng.nextInt(welcomeEmotes.length)];
                }
                picks.add(pick);
            }

            for (String pick : picks) {
                Emoji emote = msg.getGuild().getEmojiById(pick);
                msg.getMessage().addReaction(emote).complete();
            }
        }
    }
}