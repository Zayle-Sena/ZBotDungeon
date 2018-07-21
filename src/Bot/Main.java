package Bot;

import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * @author PC
 */
public class Main {
    public static Connection connection = new Connection();
    public static MainFrame mainFrame;

    public static void main(String[] args) {
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    public static String connect() {
        try {
            connection.Connect();
        } catch (Exception e) {
            return "ZBot failed to initialize!";
        }
        return "ZBot online!";
    }

    public static String stop() {
        try {
            mainFrame.game.stopGame();
            connection.stop();
        } catch (Exception e) {
            mainFrame.printToConsole(e.toString());
            return "ZBot failed to stop properly!";
        }
        return "ZBot stopped.";
    }

    public static ArrayList<String> getChannels() {
        return connection.getChannels();
    }

    public static ArrayList<String> getUsers() {
        return connection.getUsers();
    }

    public static void sendChannelMessage(String message, String channel) {
        TextChannel target = getChannel(channel);
        target.sendMessage(message).queue();

        connection.commandListener.printToConsole("[" + target.getName() + "] [ZBot]: " + message);
    }

    public static void sendUserMessage(String message, String user) {
        User target = getUser(user);
        target.openPrivateChannel().complete().sendMessage(message).queue();

        connection.commandListener.printToConsole("[" + target.getName() + "] [ZBot]: " + message);
    }

    public static User getUser(String user) {
        String id = "";
        JDA jda = connection.getJDA();
        User target;

        List<User> users = jda.getUsers();

        for (User u : users) {
            if (user.contains(u.getId())) {
                id = u.getId();
            }
        }

        target = jda.getUserById(id);
        return target;
    }

    public static TextChannel getChannel(String channel) {
        String id = "";
        JDA jda = connection.getJDA();
        TextChannel target;

        List<TextChannel> channels = jda.getTextChannels();

        for (TextChannel c : channels) {
            if (channel.contains(c.getName()) & channel.contains(c.getGuild().getName())) {
                id = c.getId();
            }
        }

        target = jda.getTextChannelById(id);
        return target;
    }
}

