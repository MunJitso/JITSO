package org.discordbot.SlashCommands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

public class KickCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if ("kick".equals(event.getName())) {
            Member author = event.getMember();
            assert author != null;
            if (author.hasPermission(Permission.ADMINISTRATOR, Permission.KICK_MEMBERS)) {
                Guild guild = event.getGuild();
                Member asMember = event.getOptionsByType(OptionType.MENTIONABLE).get(0).getAsMember();
                assert asMember != null;
                assert guild != null;
                Member member = guild.retrieveMemberById(asMember.getId()).complete();
                try {
                    String reason = event.getOptionsByType(OptionType.STRING).get(0).getAsString();
                    member.kick(reason).queue();
                } catch (IndexOutOfBoundsException err) {
                    member.kick().queue();
                }
                event.reply("kicked.").setEphemeral(true).queue();
            } else event.reply("You do not have permission to kick").setEphemeral(true).submit();
        }
    }
}
