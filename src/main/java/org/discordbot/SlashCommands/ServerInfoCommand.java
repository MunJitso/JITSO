package org.discordbot.SlashCommands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.discordbot.Colors;
import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.ThreadLocalRandom;

import static org.discordbot.Colors.getColorList;

public class ServerInfoCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if("server".equals(event.getName())){
            int random = ThreadLocalRandom.current().nextInt(getColorList().size()-1);
            Guild guild = event.getGuild();
            assert guild != null;
            EmbedBuilder message = new EmbedBuilder();
            message.setColor(Colors.getAestheticColor(random))
                    .setTitle(guild.getName() + "'s Info")
                    .setThumbnail(guild.getIconUrl())
                    .addField("Name: ", guild.getName(), true)
                    .addField("Members: ", "Total: " + guild.getMembers().size() + "\nBoosters: " + guild.getBoosters().size(), true)
                    .addField("Channels: ", "Text Channels: " + guild.getTextChannels().size() + "\nVoice Channels" + guild.getVoiceChannels().size(), true)
                    .addField("Owner: ", guild.retrieveOwner(true).complete().getUser().getAsMention(), true)
                    .addField("Creation Date: " , guild.getTimeCreated().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)), true)
                    .addField("Boost Tier: ",guild.getBoostTier().toString(),true)
                    .setFooter("Requested by " + event.getUser().getAsTag(), event.getUser().getAvatarUrl());
            event.replyEmbeds(message.build()).submit();

        }
    }
}
