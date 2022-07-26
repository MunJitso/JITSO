package org.discordbot.eventListeners;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JoinLeaveEvent extends ListenerAdapter {
    private void sendMessage(Guild guild, String message){
        List<TextChannel> channels = guild.getTextChannelsByName("─⊱⁜ʜɪ-ᴀɴᴅ-ʙʏᴇ⁜⊰─", true);
        TextChannel channel = channels.get(0);
        channel.sendMessage(message).queue();
    }
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        sendMessage(event.getGuild(), "Welcome To " + event.getGuild().getName() + ", " + event.getMember().getUser().getAsMention() + ".");
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        sendMessage(event.getGuild(), "Bye Bye, **" + event.getUser().getAsTag() + "**.");
    }
}
