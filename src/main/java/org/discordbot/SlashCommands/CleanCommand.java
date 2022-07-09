package org.discordbot.SlashCommands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


public class CleanCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if ("clean".equals(event.getName())) {
            Member author = event.getMember();
            assert author != null;
            if (author.hasPermission(Permission.ADMINISTRATOR, Permission.MESSAGE_MANAGE)) {
                TextChannel textChannel = event.getTextChannel();
                textChannel.createCopy().queue();
                textChannel.delete().queue();
            } else event.reply("Search for permission.").setEphemeral(true).submit();
        }
    }
}
