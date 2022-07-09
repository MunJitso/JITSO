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
        jda.addEventListener(new BanCommand());
        jda.addEventListener(new KickCommand());

        CommandListUpdateAction commands = jda.updateCommands();

        commands.addCommands(
                Commands.slash("clean","purge all the messages of a channel."),
                Commands.slash("info", "Gets user's info")
                        .addOption(OptionType.MENTIONABLE,"user","show the specified user's info ")
                        .setDefaultPermissions(DefaultMemberPermissions.ENABLED),
                Commands.slash("ban", "ban every person.")
                        .addOption(OptionType.MENTIONABLE, "user", "user to ban", true)
                        .addOption(OptionType.STRING,"reason", "why?", false)
                        .setDefaultPermissions(DefaultMemberPermissions.ENABLED),
                Commands.slash("kick", "kick every person.")
                        .addOption(OptionType.MENTIONABLE, "user", "user to kick", true)
                        .addOption(OptionType.STRING,"reason", "why?", false),
                Commands.slash("unban", "unban a user from the channel")
                        .addOption(OptionType.STRING, "id", "user to unban", true)
                );
        commands.queue();
    }
}