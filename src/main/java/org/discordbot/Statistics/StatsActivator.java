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
    int[] users = {0};
    int[] bots = {0};
    private VoiceChannel allMembersStats;
    private VoiceChannel membersStats;
    private VoiceChannel botsStats;

    public void memberCount(Guild guild){
        users = new int[]{0};
        bots = new int[]{0};
        guild.loadMembers().onSuccess(members -> {
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
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        assert guild != null;
        if(event.getName().equals("stats")){
            memberCount(guild);
            guild.createVoiceChannel("All Members: " + guild.getMemberCount()).queue();
            allMembersStats = guild.getVoiceChannelsByName("Member Count: " + guild.getMembers().size(), false).get(0);
            guild.createVoiceChannel("Members: " + users[0]).queue();
            membersStats = guild.getVoiceChannelsByName("Members: " + users[0], false).get(0);
            guild.createVoiceChannel("Bots: " + bots[0]).queue();
            botsStats = guild.getVoiceChannelsByName("Bots: " + bots[0], false).get(0);
            event.reply("Done").setEphemeral(true).submit();
        }
    }
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        memberCount(event.getGuild());
        allMembersStats.getManager().setName("All Members: " + event.getGuild().getMembers().size()).queue();
        membersStats.getManager().setName("Members: " + users[0]).queue();
        botsStats.getManager().setName("Bots: " + bots[0]).queue();

    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        memberCount(event.getGuild());
        allMembersStats.getManager().setName("Member Count: " + event.getGuild().getMembers().size()).queue();
        membersStats.getManager().setName("Members: " + users[0]).queue();
        botsStats.getManager().setName("Bots: " + bots[0]).queue();
    }
}
