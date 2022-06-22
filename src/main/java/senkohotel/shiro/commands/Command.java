package senkohotel.shiro.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Command {
    public Command() {}

    public void exec (MessageReceivedEvent msg, String[] args) {
        msg.getChannel().sendTyping().complete();
    }
}