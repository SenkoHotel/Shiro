package senkohotel.hotelbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import senkohotel.hotelbot.Main;
import senkohotel.hotelbot.commands.SlashCommand;

public class HelpSlashCommand extends SlashCommand {
    public HelpSlashCommand() {
        this.name = "help";
        this.description = "Displays the list of commands.";
    }

    public void exec(SlashCommandInteraction interact) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(Main.bot.getSelfUser().getName() + " - Help")
                .setDescription("Wait... you dont need a help menu for slash commands.")
                .setColor(Main.accentColor);

        reply(interact, embed);
    }
}
