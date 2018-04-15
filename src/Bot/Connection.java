package Bot;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author keltonjelsma
 */
public class Connection {
    public static JDA discord = null;
    public CommandListener commandListener;

    public void Connect() {


        try {

            discord = new JDABuilder(AccountType.BOT)
                    .setToken(Constants.ZBotToken)
                    .setAutoReconnect(true)
                    .buildBlocking();

        } catch (IllegalArgumentException | InterruptedException | LoginException | RateLimitedException e) {

            System.out.println("Connection to Discord failed. Check internet connection.");

        } finally {

        }
        commandListener = new CommandListener();
        ConnectEvents connectEvents = new ConnectEvents();
        discord.addEventListener(commandListener);
        discord.addEventListener(connectEvents);


    }

    public void stop() {
        commandListener.saveGame();
        discord.shutdown();
    }

    public JDA getJDA() {
        return discord;
    }

    public ArrayList<String> getChannels() {
        ArrayList<String> result = new ArrayList();
        List<TextChannel> channels = getJDA().getTextChannels();
        TextChannel t;

        //Add all the channels to the list
        for (int i = 0; i < channels.size(); i++) {
            t = channels.get(i);
            if (t.canTalk()) {
                result.add("(" + t.getGuild().getName() + "): " + t.getName());
            }
        }

        //Sort the channels alphabetically
        Collections.sort(result, (String s1, String s2) -> {
            return s1.compareToIgnoreCase(s2);
        });

        //Return the result, obviously
        return result;
    }

    public ArrayList<String> getUsers() {
        ArrayList<String> result = new ArrayList();
        List<User> users = getJDA().getUsers();
        User u;

        //Add all the channels to the list
        for (int i = 0; i < users.size(); i++) {
            u = users.get(i);
            if (!u.isBot()) {
                if (u.hasPrivateChannel()) {
                    result.add(u.getName() + " (" + u.getId() + ")");
                } else {
                    u.openPrivateChannel();
                    result.add(u.getName() + " (" + u.getId() + ")");
                }
            }
        }

        //Sort the channels alphabetically
        Collections.sort(result, (String s1, String s2) -> {
            return s1.compareToIgnoreCase(s2);
        });

        //Return the result, obviously
        return result;
    }

}
