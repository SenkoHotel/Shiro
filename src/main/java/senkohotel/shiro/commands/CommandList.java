package senkohotel.shiro.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import senkohotel.shiro.Main;

import java.util.TreeMap;

public class CommandList {

    static TreeMap<String, Command> commands = new TreeMap<>();

    public static void initList() {
        // commands.put("<name>", new <CommandClass>());
    }

    public static void check(MessageReceivedEvent msg) {
        String[] args = msg.getMessage().getContentRaw().substring(Main.prefix.length()).split(" ");

        if (args.length > 0)
            exec(msg, args);
    }

    static void exec (MessageReceivedEvent msg, String[] args) {
        if (commands.containsKey(args[0])) {
            commands.get(args[0]).exec(msg, args);
        }
    }
}
