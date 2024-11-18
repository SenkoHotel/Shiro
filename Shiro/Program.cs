using System.Text;
using DSharpPlus;
using DSharpPlus.Entities;
using DSharpPlus.EventArgs;
using HotelLib;
using HotelLib.Logging;
using HotelLib.Utils;

namespace Shiro;

public static class Program
{
    private static List<string> welcomeEmotes { get; } = new()
    {
        "792071753228353548",
        "844802666503995392",
        "852891485959356447",
        "835934243011035157",
        "792071751899021335",
        "841889936785276988",
        "818047092085227530"
    };

    private static readonly Dictionary<ulong, DiscordMessage> joins = new();

    public static async Task Main()
    {
        var config = HotelBot.LoadConfig<Config>();
        var bot = new HotelBot(config.Token)
        {
            AccentColor = new DiscordColor("#e5d5ce")
        };

        bot.Client.MessageCreated += onMessage;
        bot.Client.GuildMemberAdded += memberJoin;
        bot.Client.GuildMemberRemoved += memberLeave;
        await bot.Start();
    }

    private static async Task onMessage(DiscordClient sender, MessageCreateEventArgs args)
    {
        var content = args.Message.Content;

        if (args.Message.Stickers.Any())
            content += $" {args.Message.Stickers[0].Name}";

        content = content.ToLower().Trim();

        if (content.StartsWith("welcomen't") || content.StartsWith("welcoment"))
        {
            try
            {
                var emote = await args.Guild.GetEmojiAsync(810515319981867029);

                if (emote is null)
                    return;

                await args.Message.CreateReactionAsync(emote);
            }
            catch
            {
                // ignored
            }

            return;
        }

        if (content.StartsWith("welcome"))
        {
            var picks = new List<string>();

            for (var i = 0; i < 3; i++)
            {
                var rng = new Random();
                var pick = welcomeEmotes[rng.Next(welcomeEmotes.Count)];

                while (picks.Contains(pick))
                    pick = welcomeEmotes[rng.Next(welcomeEmotes.Count)];

                picks.Add(pick);
            }

            foreach (var pick in picks)
            {
                try
                {
                    var emote = await args.Guild.GetEmojiAsync(ulong.Parse(pick));

                    if (emote is null)
                        continue;

                    await args.Message.CreateReactionAsync(emote);
                }
                catch (Exception e)
                {
                    Logger.Log(e);
                }
            }
        }
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
                      .WithAllowedMentions(Mentions.All)
                      .WithEmbed(new DiscordEmbedBuilder()
                                 .WithTitle($"Welcome {member.Username}!")
                                 .WithDescription(content.ToString())
                                 .WithImageUrl("https://share.flux.moe/66d29dacbd5b27492ce4d5e9.png")
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
                          .WithAllowedMentions(Mentions.All)
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
