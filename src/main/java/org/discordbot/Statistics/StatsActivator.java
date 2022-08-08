package org.discordbot.Statistics;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class StatsActivator extends ListenerAdapter {
    int users = 0;
    int bots = 0;

    private void updatingChannels(Guild guild,String allMembersID, String membersID, String botsId){
        VoiceChannel allMembersStats = guild.getVoiceChannelById(allMembersID);
        VoiceChannel membersStats = guild.getVoiceChannelById(membersID);
        VoiceChannel botsStats = guild.getVoiceChannelById(botsId);
        assert allMembersStats != null;
        assert membersStats != null;
        assert botsStats != null;

        allMembersStats.getManager().setName("Member Count: " + guild.getMemberCount()).queue();
        membersStats.getManager().setName("Members: " + users).queue();
        botsStats.getManager().setName("Bots: " + bots).queue();
    }
    private void memberCount(Guild guild, boolean createChannels){
        users = 0;
        bots = 0;
        guild.loadMembers().onSuccess(members -> {
            int user = 0;
            int bot = 0;
            for(Member member: members){
                if (member.getUser().isBot())
                    bot++;
                else
                    user++;
            }
            users = user;
            bots = bot;
            if(createChannels){
                guild.createVoiceChannel("All Members: " + guild.getMemberCount()).queue();
                guild.createVoiceChannel("Members: " + users).queue();
                guild.createVoiceChannel("Bots: " + bots).queue();
            } else{
                updatingChannels(guild, "1001646065449193482", "1001646066497765529", "1001646067533742161");
            }
        });
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        assert guild != null;
        if(event.getName().equals("stats")){
            memberCount(guild, false);
        }
    }
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        memberCount(event.getGuild(), false);
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        memberCount(event.getGuild(), false);
    }
}
