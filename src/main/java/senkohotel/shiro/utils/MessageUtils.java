package senkohotel.shiro.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import senkohotel.shiro.Main;

public class MessageUtils {
    public static void reply(MessageReceivedEvent msg, String content) {
        msg.getMessage()
                .reply(content)
                .mentionRepliedUser(false)
                .complete();
    }

    public static void reply(MessageReceivedEvent msg, EmbedBuilder embed) {
        msg.getMessage()
                .reply(new MessageBuilder().setEmbeds(embed.build()).build())
                .mentionRepliedUser(false)
                .complete();
    }

    public static void reply(MessageReceivedEvent msg, MessageBuilder message) {
        msg.getMessage()
                .reply(message.build())
                .mentionRepliedUser(false)
                .complete();
    }

    public static void send(String channelID, String content) {
        Main.bot.getTextChannelById(channelID).sendMessage(content).complete();
    }

    public static void send(String channelID, MessageBuilder message) {
        Main.bot.getTextChannelById(channelID).sendMessage(message.build()).complete();
    }

    public static void send(String channelID, EmbedBuilder embed) {
        Main.bot.getTextChannelById(channelID).sendMessage(new MessageBuilder().setEmbeds(embed.build()).build()).complete();
    }
}
