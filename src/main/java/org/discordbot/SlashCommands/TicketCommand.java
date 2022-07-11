package org.discordbot.SlashCommands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.EnumSet;

public class TicketCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if("ticket".equals(event.getName())){
            Guild guild = event.getGuild();
            assert guild != null;
            Member bot = guild.getMemberById("878037797355798589");
            assert bot != null;
            EmbedBuilder message = new EmbedBuilder();
            message.setColor(new Color(88, 129, 87))
                    .setTitle("Open a Ticket!")
                    .setDescription("Click on the button below to create a ticket.")
                    .setFooter(bot.getUser().getAsTag(), bot.getUser().getAvatarUrl());
            event.replyEmbeds(message.build())
                    .addActionRow(
                            Button.secondary("ticketCreation", "Create Ticket.")
                    ).submit();
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if(event.getComponentId().equals("ticketCreation")){
            Guild guild = event.getGuild();
            User user = event.getUser();
            assert guild != null;
            Member member = guild.getMemberById(user.getId());
            assert member != null;
            EnumSet<Permission> permissions = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);
            try {
                TextChannel channel = guild.getTextChannelsByName(String.format("ticket-%s", user.getId()), false).get(0);
                event.reply("You already opened a ticket.").setEphemeral(true).queue();
            } catch (IndexOutOfBoundsException err){
                guild.createTextChannel(String.format("ticket-%s", user.getId()))
                        .addPermissionOverride(guild.getPublicRole(), null, permissions)
                        .addMemberPermissionOverride(user.getIdLong(), permissions, null)
                        .queue();
                TextChannel channel = guild.getTextChannelsByName(String.format("ticket-%s", user.getId()), false).get(0);
                event.reply("Created. " + channel.getAsMention()).setEphemeral(true).queue();
            }

        }
    }
}
