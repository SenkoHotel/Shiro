package senkohotel.shiro.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import senkohotel.hotelbot.Main;
import senkohotel.hotelbot.utils.MessageUtils;

import java.util.HashMap;

public class GuildMemberListener extends ListenerAdapter {
    HashMap<String, Message> joins = new HashMap<>();

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        if (event.getGuild().getId().equals("791993321374613514")) {
            if (event.getUser().isBot()) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setColor(Main.accentColor)
                        .setTitle("[Bot] " + event.getUser().getName() + " was added to the server!");

                MessageUtils.send("843139330016935947", embed);
                return;
            }

            MessageBuilder message = new MessageBuilder()
                    .setContent("<@" + event.getMember().getId() + "> <@&806036007858208775>") // <@&806036007858208775>
                    .setEmbeds(
                            new EmbedBuilder()
                                    .setTitle("Welcome " + event.getUser().getName() + "!")
                                    .setDescription("> \u256d Read <#791998146565373952> and follow them!\n" +
                                                    "> \ufe31 Check <#793168591335849984> for more roles!\n" +
                                                    "> \u2570 Most importantly.... Have fun!")
                                    .setImage("https://cdn.discordapp.com/attachments/964560961497358397/982309210995953704/unknown.png?size=4096")
                                    .setThumbnail(event.getUser().getAvatarUrl())
                                    .setColor(0xefb64f)
                                    .build()
                    );

            Message joinmessage = MessageUtils.send("843139330016935947", message);
            joins.put(event.getMember().getId(), joinmessage);
        }
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        if (event.getGuild().getId().equals("791993321374613514")) {
            if (joins.containsKey(event.getUser().getId())) {
                MessageBuilder message = new MessageBuilder()
                        .setContent("<@&806036007858208775>")
                        .setEmbeds(
                                new EmbedBuilder()
                                        .setTitle(event.getUser().getName() + " just joi...")
                                        .setDescription("Nevermind they left again ;-;")
                                        .setColor(0xFF5555)
                                        .build()
                        );
                joins.get(event.getUser().getId()).editMessage(message.build()).complete();
                joins.remove(event.getUser().getId());
            }
        }
    }
}
