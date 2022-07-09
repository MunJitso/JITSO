package org.discordbot.eventListeners;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JoinLeaveEvent extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        List<TextChannel> channels = guild.getTextChannelsByName("─⊱⁜ʜɪ-ᴀɴᴅ-ʙʏᴇ⁜⊰─", true);
        TextChannel channel = channels.get(0);
        channel.sendMessage("Welcome To " + event.getGuild().getName() + ", " + event.getMember().getUser().getAsMention() + ".").queue();
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        Guild guild = event.getGuild();
        List<TextChannel> channels = guild.getTextChannelsByName("─⊱⁜ʜɪ-ᴀɴᴅ-ʙʏᴇ⁜⊰─", true);
        TextChannel channel = channels.get(0);
        channel.sendMessage(String.format("Bye Bye, **%s**.", event.getUser().getAsTag())).queue();
    }
}
