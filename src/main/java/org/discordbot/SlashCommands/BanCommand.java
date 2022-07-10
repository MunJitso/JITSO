package org.discordbot.SlashCommands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

public class BanCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if ("ban".equals(event.getName())) {
            Member author = event.getMember();
            assert author != null;
            if (author.hasPermission(Permission.BAN_MEMBERS, Permission.ADMINISTRATOR)) {
                Guild guild = event.getGuild();
                assert guild != null;
                try {
                    Member member = event.getOptionsByType(OptionType.MENTIONABLE).get(0).getAsMember();
                    assert member != null;
                    String reason = event.getOptionsByType(OptionType.STRING).get(0).getAsString();
                    guild.ban(member, 0, reason).queue();
                } catch (IndexOutOfBoundsException err) {
                    Member member = event.getOptionsByType(OptionType.MENTIONABLE).get(0).getAsMember();
                    assert member != null;
                    guild.ban(member, 0).queue();
                }
                event.reply("banned.").setEphemeral(true).queue();
            } else event.reply("shut up, you don't have permission.").setEphemeral(true).submit();
        }
    }
}
