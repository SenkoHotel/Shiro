using System.Text;
using DSharpPlus;
using DSharpPlus.Entities;
using DSharpPlus.EventArgs;
using HotelLib;
using HotelLib.Utils;

namespace Shiro;

public static class Program
{
    private static readonly Dictionary<ulong, DiscordMessage> joins = new();

    public static async Task Main()
    {
        var config = HotelBot.LoadConfig<Config>();
        var bot = new HotelBot(config.Token);

        bot.Client.GuildMemberAdded += memberJoin;
        bot.Client.GuildMemberRemoved += memberLeave;
        await bot.Start();
    }

    private static async Task memberJoin(DiscordClient sender, GuildMemberAddEventArgs args)
    {
        var member = args.Member;

        if (member.IsBot)
            return;

        var content = new StringBuilder();
        content.AppendLine("> \u256d Read <#791998146565373952> and follow them!");
        content.AppendLine("> \ufe31 Check <#793168591335849984> for more roles!");
        content.AppendLine("> \u2570 Most importantly.... Have fun!");

        var builder = new DiscordMessageBuilder()
                      .WithContent($"{member.Mention} <@&806036007858208775>")
                      .WithEmbed(new DiscordEmbedBuilder()
                                 .WithTitle($"Welcome {member.Username}!")
                                 .WithDescription(content.ToString())
                                 .WithImageUrl("https://cdn.discordapp.com/attachments/964560961497358397/982309210995953704/unknown.png?size=4096")
                                 .WithThumbnail(member.AvatarUrl)
                                 .WithColor(new DiscordColor(239, 182, 79))
                                 .Build());

        var channel = member.Guild.GetChannel(843139330016935947);

        if (channel is null)
            return;

        var message = await channel.SendMessageAsync(builder);
        joins.Add(member.Id, message);
    }

    private static async Task memberLeave(DiscordClient sender, GuildMemberRemoveEventArgs args)
    {
        var member = args.Member;

        if (member.IsBot)
            return;

        if (joins.TryGetValue(member.Id, out var message))
        {
            var joinDate = message.CreationTimestamp.ToUnixTimeSeconds();
            var leaveDate = DateTimeOffset.UtcNow.ToUnixTimeSeconds();
            var difference = leaveDate - joinDate;

            var builder = new DiscordMessageBuilder()
                          .WithContent("<@&806036007858208775>")
                          .WithEmbed(new DiscordEmbedBuilder()
                                     .WithTitle($"{member.Username} just joi...")
                                     .WithDescription("Nevermind they left again ;-;")
                                     .WithFooter($"They were here for {TimeUtils.Format(difference)}")
                                     .WithColor(new DiscordColor(255, 85, 85))
                                     .Build());

            await message.ModifyAsync(builder);
        }
    }
}
