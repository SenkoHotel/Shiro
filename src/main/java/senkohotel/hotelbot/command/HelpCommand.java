package senkohotel.hotelbot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import senkohotel.hotelbot.Main;
import senkohotel.hotelbot.commands.Command;
import senkohotel.hotelbot.commands.CommandList;
import senkohotel.hotelbot.utils.MessageUtils;

import java.util.Map;

public class HelpCommand extends Command {
    public HelpCommand() {
        super();
        name = "help";
        hidden = true;
    }

    @Override
    public void exec(MessageReceivedEvent msg, String[] args) throws Exception {
        super.exec(msg, args);

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(Main.bot.getSelfUser().getName() + " help page")
                .setColor(Main.accentColor)
                .setDescription("A list off all commands i can use!");

        boolean isEmpty = true;
        for (Map.Entry<String, Command> command : CommandList.getCommands().entrySet()) {
            if (!command.getValue().hidden) {
                isEmpty = false;
                embed.addField(command.getKey(), command.getValue().desc, true);
            }
        }

        if (isEmpty) {
            embed.setDescription("A list off all comman...\nWait...\nThere's nothing here.");
            embed.setImage("https://c.tenor.com/ZDIzN0XPt_oAAAAC/senkosan-senko.gif");
        }

        MessageUtils.reply(msg, embed);
    }
}
