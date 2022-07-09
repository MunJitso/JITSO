package org.discordbot.eventListeners;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class AntiSwearingEvent extends ListenerAdapter {
    String[] LIST_OF_BAD_WORDS = {"anal", "anus", "arse", "ass", "motherfucker", "balls", "bastard", "bitch", "blowjob", "blow job", "buttplug", "cock", "coon", "cunt", "dildo", "fag", "dyke", "fuck", "fucking", "nigger", "Goddamn", "jizz", "nigga" };
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        User user = event.getAuthor();
        String[] message = event.getMessage().getContentRaw().split(" ");
        for (String s : message) {
            for (String list_of_bad_word : LIST_OF_BAD_WORDS) {
                if (s.equalsIgnoreCase(list_of_bad_word)) {
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessage("Respect the members, " + user.getAsMention()).queue();
                }
            }
        }
    }
}
