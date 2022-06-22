package senkohotel.shiro;

import com.google.gson.JsonParser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import senkohotel.shiro.commands.CommandList;
import senkohotel.shiro.listeners.GuildMemberListener;
import senkohotel.shiro.listeners.MessageListener;

import javax.security.auth.login.LoginException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;

public class Main {
    public static String prefix = "shiro ";
    public static JDA bot;
    public static int accentColor = 0xf3ecf4;

    public static void main(String[] args) throws LoginException {
        CommandList.initList();

        JDABuilder jda = JDABuilder.createDefault(loadToken());
        jda.enableIntents(EnumSet.allOf(GatewayIntent.class));
        jda.setRawEventsEnabled(true);
        jda.setActivity(Activity.watching("new hotel visitors arrive"));
        bot = jda.build();
        bot.addEventListener(new MessageListener());
        bot.addEventListener(new GuildMemberListener());
    }

    static String loadToken() {
        try {
            return JsonParser.parseString(Files.readString(Path.of("config/shiro.json"))).getAsJsonObject().get("token").getAsString();
        } catch (Exception ex) {
            System.out.println("Failed to load token");
            return "";
        }
    }
}
