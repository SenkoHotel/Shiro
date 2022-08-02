package senkohotel.hotelbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Command {
    public String name = "";
    public String desc = "No description available.";
    public boolean hidden = false; // hide it from the help list

    public Command() {}

    public void exec(MessageReceivedEvent msg, String[] args) throws Exception {
        msg.getChannel().sendTyping().complete();
    }

    public boolean hasArgument (String arg, String[] args) {
        for (String a : args) {
            if (a.equals(arg))
                return true;
        }

        return false;
    }
}