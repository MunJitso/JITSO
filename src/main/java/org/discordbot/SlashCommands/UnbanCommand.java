package org.discordbot.SlashCommands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UnbanCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if ("unban".equals(event.getName())) {
            Member author = event.getMember();
            assert author != null;
            if (author.hasPermission(Permission.BAN_MEMBERS, Permission.ADMINISTRATOR)) {
                try {
                    Guild guild = event.getGuild();
                    assert guild != null;
                    String id = event.getOptionsByType(OptionType.STRING).get(0).getAsString();
                    List<Guild.Ban> banList = guild.retrieveBanList().complete();
                    boolean isInList = false;
                    for (Guild.Ban ban : banList)
                        if (ban.getUser().getId().equals(id)) {
                            isInList = true;
                        }
                    if (isInList) {
                        guild.unban(User.fromId(id)).queue();
                        event.reply("Unbanned").setEphemeral(true).queue();
                    } else
                        event.reply("you have to unban someone who's banned from the server..").setEphemeral(true).submit();
                } catch (IndexOutOfBoundsException | NumberFormatException err) {
                    event.reply("use Id to unban the target. USE DEVELOPER MODE...").setEphemeral(true).queue();
                }
            } else event.reply("shut up, you don't have permission.").setEphemeral(true).submit();
        }
    }
}
