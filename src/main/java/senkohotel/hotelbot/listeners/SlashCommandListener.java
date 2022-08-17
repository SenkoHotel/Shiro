package senkohotel.hotelbot.listeners;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import senkohotel.hotelbot.Main;
import senkohotel.hotelbot.commands.SlashCommandList;

public class SlashCommandListener extends ListenerAdapter {
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        if (!Main.debug)
            return;

        try {
            SlashCommandList.initGuild(event);
        } catch (Exception e) {
            Main.LOG.error("Couldn't initialize commands for guild " + event.getGuild().getName(), e);
        }
    }

    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        try {
            SlashCommandList.exec(event);
        } catch (Exception e) {
            event.reply("Something went wrong...").complete();
            Main.LOG.error("Couldn't execute slash command " + event.getName(), e);
        }
    }
}
