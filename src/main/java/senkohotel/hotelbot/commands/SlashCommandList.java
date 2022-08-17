package senkohotel.hotelbot.commands;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.reflections.Reflections;
import senkohotel.hotelbot.Main;

import java.util.*;

public class SlashCommandList {
    static TreeMap<String, SlashCommand> commands = new TreeMap<>();

    public static void initList() {
        Reflections reflections = new Reflections("senkohotel");
        Set<Class<? extends SlashCommand>> classes = reflections.getSubTypesOf(SlashCommand.class);
        for (Class<? extends SlashCommand> cmd : classes) {
            try {
                SlashCommand slashCommand = cmd.getConstructor().newInstance();

                if (slashCommand.name.isEmpty()) continue;

                commands.put(slashCommand.name, slashCommand);
                Main.LOG.info("Added slash command " + slashCommand.name);
            } catch (Exception e) {
                Main.LOG.error("Couldn't add command " + cmd.getName(), e);
            }
        }
    }

    public static void initGuild(GuildReadyEvent gre) {
        List<CommandData> slashCommands = new ArrayList<>();

        for (Map.Entry<String, SlashCommand> entry : commands.entrySet()) {
            SlashCommandData slashCommand = Commands.slash(entry.getKey(), entry.getValue().description);
            slashCommands.add(slashCommand);
        }

        gre.getGuild().updateCommands().addCommands(slashCommands).complete();
    }

    public static void initGlobal() {
        List<CommandData> slashCommands = new ArrayList<>();

        for (Map.Entry<String, SlashCommand> entry : commands.entrySet()) {
            SlashCommandData slashCommand = Commands.slash(entry.getKey(), entry.getValue().description);
            slashCommand.addOptions(entry.getValue().options);
            slashCommands.add(slashCommand);
        }

        Main.bot.updateCommands().addCommands(slashCommands).complete();
    }

    public static void exec(SlashCommandInteractionEvent interact) {
        if (commands.containsKey(interact.getName())) {
            commands.get(interact.getName()).exec(interact);
        }
    }
}
