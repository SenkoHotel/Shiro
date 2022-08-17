package senkohotel.hotelbot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class SlashCommand {
    public String name;
    public String description;
    public List<OptionData> options = new ArrayList<>();

    public SlashCommand() {
        this.name = "";
        this.description = "";
    }

    public void exec(SlashCommandInteraction interact) {
        reply(interact, "No implementation found for " + interact.getName());
    }

    public void reply(SlashCommandInteraction interact, String content) {
        interact.reply(content).complete();
    }

    public void reply(SlashCommandInteraction interact, MessageBuilder message) {
        interact.reply(message.build()).complete();
    }

    public void reply(SlashCommandInteraction interact, EmbedBuilder embed) {
        interact.replyEmbeds(embed.build()).complete();
    }
}
