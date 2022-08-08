package org.discordbot.SlashCommands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.ThreadLocalRandom;

import static org.discordbot.Colors.getAestheticColor;
import static org.discordbot.Colors.getColorList;

public class ServerInfoCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if("server".equals(event.getName())){
            int random = ThreadLocalRandom.current().nextInt(getColorList().size()-1);
            Guild guild = event.getGuild();
            assert guild != null;
            String boostLevel;
            switch (guild.getBoostTier()){
                case TIER_1:
                    boostLevel = "Level 1 Perk";
                    break;
                case TIER_2:
                    boostLevel = "Level 2 Perk";
                    break;
                case TIER_3:
                    boostLevel = "Level 3 Perk";
                    break;
                default:
                    boostLevel = "No Boosts";
            }
            EmbedBuilder message = new EmbedBuilder();
            message.setColor(getAestheticColor(random))
                    .setTitle(guild.getName() + "'s Info")
                    .setThumbnail(guild.getIconUrl())
                    .addField("Name: ", guild.getName(), true)
                    .addField("Members: ", guild.getMemberCount() + "", true)
                    .addField("Channels: ", "Text Channels: " + guild.getTextChannels().size() + "\nVoice Channels: " + guild.getVoiceChannels().size(), true)
                    .addField("Owner: ", guild.retrieveOwner(true).complete().getUser().getAsMention(), true)
                    .addField("Creation Date: " , "<t:" + guild.getTimeCreated().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))+ ":R>", true)
                    .addField("Boost Tier: ",boostLevel,true)
                    .setFooter("Requested by " + event.getUser().getAsTag(), event.getUser().getAvatarUrl());
            event.replyEmbeds(message.build()).submit();

        }
    }
}
