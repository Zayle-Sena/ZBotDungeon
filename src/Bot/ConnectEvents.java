package Bot;

import net.dv8tion.jda.core.events.*;
import net.dv8tion.jda.core.hooks.EventListener;

/**
 * @author PC
 */
public class ConnectEvents implements EventListener {

    @Override
    public void onEvent(Event event) {
        if (event instanceof DisconnectEvent) {
            Main.mainFrame.printToConsole("############### Lost Connection! ###############");
        }
        if (event instanceof ReconnectedEvent | event instanceof ResumedEvent) {
            Main.mainFrame.printToConsole("############### Reconnected! ###############");
        }
    }
}
