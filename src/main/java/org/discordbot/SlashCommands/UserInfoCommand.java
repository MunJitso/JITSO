package org.discordbot.SlashCommands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.ThreadLocalRandom;

import static org.discordbot.Colors.*;


public class UserInfoCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("info")) {
            int random = ThreadLocalRandom.current().nextInt(getColorList().size()-1);
            User user = event.getUser();
            Guild guild = event.getGuild();
            assert guild != null;
            User target;
            try{
                target = event.getOptionsByType(OptionType.MENTIONABLE).get(0).getAsUser();
            } catch (IndexOutOfBoundsException err){
                target = user;
            }
            RestAction<Member> members = guild.retrieveMemberById(target.getId());
            Member member = members.complete();
            String isOwner = "No";
            if(target.getId().equals(guild.getOwnerId())) isOwner = "Yes";
            EmbedBuilder message = new EmbedBuilder();
            message.setColor(getAestheticColor(random))
                    .setTitle(target.getName() + "'s ID Card" + "\n")
                    .addField("Tag: ", target.getAsTag(), true)
                    .addField("Id: ", target.getId(), true)
                    .addField("First Discord Join:", target.getTimeCreated().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)), true)
                    .addField("is Owner ?: ", isOwner, true)
                    .addField("First Guild Join: ", member.getTimeJoined().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)), true)
                    .addField("Role Count: ", String.valueOf(member.getRoles().size()), true)
                    .setThumbnail(target.getAvatarUrl())
                    .setFooter("Requested by " + user.getAsTag(), user.getAvatarUrl());
            event.replyEmbeds(message.build()).submit();
        }
    }


}