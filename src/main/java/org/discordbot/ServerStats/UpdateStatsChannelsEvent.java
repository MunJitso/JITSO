package org.discordbot.ServerStats;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class UpdateStatsChannelsEvent extends ListenerAdapter{

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        int[] users = {0};
        int[] bots = {0};
        event.getGuild().loadMembers().onSuccess(members -> {
            int user = 0;
            int bot = 0;
            for(Member member: members){
                if (member.getUser().isBot())
                    bot++;
                else
                    user++;
            }
            users[0] = user;
            bots[0] = bot;
        });
        StatsChannels.allMembersStats.getManager().setName("All Members: " + event.getGuild().getMembers().size()).queue();
        StatsChannels.membersStats.getManager().setName("Members: " + users[0]).queue();
        StatsChannels.botsStats.getManager().setName("Bots: " + bots[0]).queue();

    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        int[] users = {0};
        int[] bots = {0};
        event.getGuild().loadMembers().onSuccess(members -> {
            int user = 0;
            int bot = 0;
            for(Member member: members){
                if (member.getUser().isBot())
                    bot++;
                else
                    user++;
            }
            users[0] = user;
            bots[0] = bot;
        });
        StatsChannels.allMembersStats.getManager().setName("All Members: " + event.getGuild().getMembers().size()).queue();
        StatsChannels.membersStats.getManager().setName("Members: " + users[0]).queue();
        StatsChannels.botsStats.getManager().setName("Bots: " + bots[0]).queue();
    }
}
