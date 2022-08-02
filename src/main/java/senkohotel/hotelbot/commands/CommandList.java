package senkohotel.hotelbot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import senkohotel.hotelbot.Main;

import java.util.TreeMap;
import org.reflections.Reflections;
import senkohotel.hotelbot.utils.MessageUtils;

import java.util.*;

public class CommandList {

    static TreeMap<String, Command> commands = new TreeMap<>();

    public static void initList() {
        Reflections reflections = new Reflections("senkohotel");
        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);
        for (Class<? extends Command> cmd : classes) {
            try {
                addCommand(cmd.getConstructor().newInstance());
            } catch (Exception e) {
                System.out.println("Couldn't add command " + cmd.getName());
                e.printStackTrace();
            }
        }
    }

    static void addCommand (Command cmd) {
        if (cmd.name.equals("")) // dont add it since it doesnt have a way to use it
            return;

        commands.put(cmd.name, cmd);
        Main.LOG.info("Added command " + cmd.name);
    }

    public static void check(MessageReceivedEvent msg, String prefix) {
        String[] args = msg.getMessage().getContentRaw().substring(prefix.length()).split(" ");

        if (args.length > 0)
            exec(msg, args);
    }

    static void exec(MessageReceivedEvent msg, String[] split) {
        if (commands.containsKey(split[0])) {
            String[] args = Arrays.copyOfRange(split, 1, split.length);

            try {
                commands.get(split[0]).exec(msg, args);
            } catch (Exception e) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Error")
                        .setColor(0xFF5555)
                        .setDescription("An error occured while executing the command!")
                        .addField("Stacktrace", "```java\n" + e.toString() + "```", false);
                MessageUtils.reply(msg, embed);
            }
        }
    }

    public static TreeMap<String, Command> getCommands() {
        return commands;
    }
}
