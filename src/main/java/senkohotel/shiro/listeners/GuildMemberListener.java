package senkohotel.shiro.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import senkohotel.shiro.utils.MessageUtils;

public class GuildMemberListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        if (event.getGuild().getId().equals("791993321374613514")) {
            MessageBuilder message = new MessageBuilder()
                    .setContent("<@" + event.getMember().getId() + "> <@&806036007858208775>") // <@&806036007858208775>
                    .setEmbeds(
                            new EmbedBuilder()
                                    .setTitle("Welcome!")
                                    .setDescription(
                                            "> \u256d Read <#791998146565373952> and follow them!\n" +
                                                    "> \ufe31 Check <#793168591335849984> for more roles!\n" +
                                                    "> \u2570 Most importantly.... Have fun!")
                                    .setImage("https://cdn.discordapp.com/attachments/964560961497358397/982309210995953704/unknown.png?size=4096")
                                    .setThumbnail(event.getGuild().getIconUrl())
                                    .setColor(0xefb64f)
                                    .build()
                    );

            MessageUtils.send("843139330016935947", message);
        }
    }
}
