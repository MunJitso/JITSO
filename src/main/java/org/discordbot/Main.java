package org.discordbot;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.discordbot.SlashCommands.*;
import org.discordbot.Statistics.StatsActivator;
import org.discordbot.eventListeners.AntiSwearingEvent;
import org.discordbot.eventListeners.JoinLeaveEvent;

public class Main {

    public static void main(String[] args) throws Exception {
        JDA jda = JDABuilder.createDefault(System.getenv().get("TOKEN"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_BANS, GatewayIntent.GUILD_WEBHOOKS)
                .setActivity(Activity.playing("MunJitso doesn't suck.")).setStatus(OnlineStatus.DO_NOT_DISTURB)
                .build();
        // Events and Commands.
        jda.addEventListener(new AntiSwearingEvent());
        jda.addEventListener(new JoinLeaveEvent());
        jda.addEventListener(new CleanCommand());
        jda.addEventListener(new UserInfoCommand());
        jda.addEventListener(new ServerInfoCommand());
        jda.addEventListener(new BanCommand());
        jda.addEventListener(new UnbanCommand());
        jda.addEventListener(new KickCommand());
        jda.addEventListener(new TicketCommand());
        jda.addEventListener(new StatsActivator());

        CommandListUpdateAction commands = jda.updateCommands();

        commands.addCommands(
                Commands.slash("info", "Gets user's info")
                        .addOption(OptionType.MENTIONABLE,"user","Show the specified user's info ")
                        .setDefaultPermissions(DefaultMemberPermissions.ENABLED),
                Commands.slash("server", "Gets server's info")
                        .setDefaultPermissions(DefaultMemberPermissions.ENABLED),
                Commands.slash("ban", "Bans a user.")
                        .addOption(OptionType.MENTIONABLE, "user", "User to ban", true)
                        .addOption(OptionType.STRING,"reason", "why?", false)
                        .setDefaultPermissions(DefaultMemberPermissions.ENABLED),
                Commands.slash("kick", "Kicks every person.")
                        .addOption(OptionType.MENTIONABLE, "user", "User to kick", true)
                        .addOption(OptionType.STRING,"reason", "Why?", false),
                Commands.slash("unban", "Unbans a user from the channel")
                        .addOption(OptionType.STRING, "id", "User to unban", true),
                Commands.slash("clean","Purges all the messages of a channel."),
                Commands.slash("ticket", "setup a ticket system"),
                Commands.slash("stats", "setuping the stats channels")
                        .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
                );
        commands.queue();
    }
}