package Bot;

import classes.Core.*;
import classes.Misc.Note;
import classes.Misc.NoteList;
import classes.Misc.TemTable;
import classes.Misc.WildTable;
import java.io.*;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.*;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.*;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * @author Zayle Sena
 */
public class CommandListener extends ListenerAdapter {
    public GameInstance game;
    ArrayList<TextChannel> DesignatedChannels = new ArrayList();

    File gameFile;
    File spellFile;
    FileInputStream fileIn;
    ObjectInputStream objectIn;

    public NoteList NoteList;
    public WildTable wildMagic;
    public TemTable temTable;
    public String primedPrivate;
    public String primedPublic;
    public ArrayList<ArrayList<String>> primedPeople = new ArrayList();
    
    Player usingPlayer;
    
    Random random = new Random();
    int wmChance = 15;
    String wme;

    boolean listConfirm = false;
    boolean cookie = false;

    Pattern dicePattern = Pattern.compile("(\\d+|a\\s)[d]\\d+");
    Matcher diceMatcher;

    CommandListener() {
        boolean loadSuccess = false;
        NoteList = new NoteList();
        wildMagic = new WildTable();
        temTable = new TemTable();
        //Trying to load the game
        try {
            gameFile = new File("game.ser");

            //If there's a saved game
            if (gameFile.exists()) {
                //Load it
                fileIn = new FileInputStream(gameFile);
                objectIn = new ObjectInputStream(fileIn);

                game = (GameInstance) objectIn.readObject();
                fileIn.close();
                objectIn.close();
                loadSuccess = true;
                printToConsole("Game found! Attempting to load.");
                //If there isn't
            } else {
                //Start new game
                printToConsole("Game not found, starting new game.");
                game = new GameInstance(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Loading Spell List
        try {
            spellFile = new File("spells.list");

            //If there's a saved game
            if (spellFile.exists()) {
                //Load it
                fileIn = new FileInputStream(spellFile);
                objectIn = new ObjectInputStream(fileIn);
            
                printToConsole("Notes found! Loading!");
                NoteList = (NoteList)objectIn.readObject();
            fileIn.close();
            objectIn.close();
            loadSuccess = true;
            //If there isn't
            } else {
                //Start new game
                NoteList = new NoteList();
                printToConsole("Notes not found, starting new instance.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (game == null) {
            //This will run if the game is found to be incompatible
            gameFile.delete();
            game = new GameInstance(this);
            printToConsole("Game save corrupted! Creating new game.");
        }
        if (NoteList == null) {
            //This will run if the game is found to be incompatible
            spellFile.delete();
            NoteList = new NoteList();
            printToConsole("Spell List corrupted! Creating new Spell List.");
        }


        DesignatedChannels.add(Connection.discord.getGuildById("318490999556931584").getTextChannelsByName("zbot_channel", true).get(0));
        printToConsole("Testing Grounds Channel added");

        try {
            if (loadSuccess) {
                respondAll("```fix\nZBot is now online!\nGame loaded ^-^```");
            } else {
                respondAll("```fix\nZBot is now online!\nNo game loaded ;-;```");
            }

        } catch (Exception e) {
            printToConsole("Lacking permission to write in public channel. Skipping server.");
        }


        random.setSeed(random.nextLong());
        printToConsole("Command Listener: Command Listener initialized");
    }

    @Override
    //This method triggers whenever ZBot recieves a message. Woot.
    public void onMessageReceived(MessageReceivedEvent event) {

        //Display all recieved messages in the console
        if (!event.getAuthor().isBot()) {
            printToConsole("[" + event.getChannel().getName() + "] [" + event.getAuthor().getName() + "]:\t" + event.getMessage().getContent().replace("\n", "\n\t"));
        }

        //And immediately tries to send it to the other method
        if (event.isFromType(ChannelType.PRIVATE)) {
            onPrivateMessageReceived(event);
        }
        Message message = event.getMessage();
        String messageText = message.getContent();
        User author = message.getAuthor();
        String authorName = author.getName();
        
        Player player = game.getPlayerById(author.getId());
        String noChar = "You don't have a character in the deungeon!\n"
                        + "Use \"" + Constants.ZBotPrefix + "spawn\" to join.";

        if (!author.getId().equals(Constants.ZBotID) | messageText.contains(Constants.ZBotPrefix)) {
            //printToConsole("Message received from " + authorName + "(" + author.getId() + ")");
            //printToConsole("Message Content: " + messageText);

            int count = 0;
            if (messageText.toLowerCase().contains("prefix")) {
                count += 3;
                //printToConsole("\"prefix\" found, +3");
            }
            if (messageText.toLowerCase().contains("use")) {
                count += 1;
                //printToConsole("\"zbot\" found, +3");
            }
            if (messageText.toLowerCase().contains("zbot")) {
                count += 3;
                //printToConsole("\"zbot\" found, +3");
            } else if (messageText.toLowerCase().contains("bot")) {
                count += 2;
                //printToConsole("\"bot\" found, +2");
            }
            if (messageText.toLowerCase().contains("what is") | messageText.toLowerCase().contains("what's")) {
                count += 1;
                //printToConsole("\"what is\" found, +1");
            }
            if (messageText.toLowerCase().contains("command")) {
                count += 2;
                //printToConsole("\"command\" found, +2");
            }
            if (messageText.toLowerCase().contains("?")) {
                count += 1;
                //printToConsole("\"?\" found, +1");
            }
            if (messageText.toLowerCase().contains("tell")) {
                count += 1;
                //printToConsole("\"tell\" found, +1");
            }
            if (count > 4) {
                respond("The current prefix is \"" + Constants.ZBotPrefix + "\"", event);
                return;
            }
            if (messageText.toLowerCase().contains("lewd")) {
                respond("^^^", event);
            }
            if (((messageText.toLowerCase().contains("shut up")
                    | (messageText.toLowerCase().contains("quiet")
                    | messageText.toLowerCase().contains("shush")) |
                    messageText.toLowerCase().contains("shh"))) & event.getChannel().getHistoryBefore(message, 1).complete().getRetrievedHistory().get(0).getAuthor().getId().equals(Constants.ZBotID)) {
                if (event.getChannel().getHistoryBefore(message, 1).complete().getRetrievedHistory().get(0).getAuthor().getId().equals(Constants.ZBotID)) {
                    event.getChannel().getHistoryBefore(message, 1).complete().getRetrievedHistory().get(0).delete().complete();
                }
                Message apology = respond("Sorry ;-;", event);
                try {
                    sleep(2000);
                } catch (Exception e) {}
                    apology.delete().complete();
                
                return;
            }
            if (messageText.toLowerCase().contains("cookie") & !senderIsAdmin(event)) {
                if (random.nextInt(3) == 0) {
                    respond("*Steals the cookie*", event);
                    cookie = true;
                }
            }
            if (messageText.toLowerCase().contains("gimme") & senderIsAdmin(event) & cookie == true) {
                respond("*gives " + author.getName() + " the cookie*", event);
                cookie = false;
            }
            if (messageText.toLowerCase().contains("thank") | messageText.toLowerCase().contains("thx")) {
                if (event.getChannel().getHistoryBefore(event.getMessageId(), 1).complete().getRetrievedHistory().get(0).getAuthor().getId().equals(Constants.ZBotID)) {
                    //printToConsole(event.getChannel().getHistoryBefore(event.getMessageId(), 1).complete().getRetrievedHistory().get(0).getContent());
                    respond("You're welcome! ^-^", event);
                }
            }
        }
        if (messageText.toLowerCase().contains("zayle") & !author.getId().equals(Constants.ZBotID)) {
            String server = null;
            try {
                server = event.getChannel().getName() + " in " + event.getGuild().getName();
            } catch (Exception e) {
            }

            if (server == null) {
                server = "a private message";
            }
            whisper(author.getName() + " mentioned you in " + server + ".", event, Constants.adminIds[0]);
        }
        if ((messageText.toLowerCase().contains("luvia") | messageText.toLowerCase().contains("undyne")) & !author.getId().equals(Constants.ZBotID)) {
            String server = null;
            try {
                server = event.getChannel().getName() + " in " + event.getGuild().getName();
            } catch (Exception e) {
            }

            if (server == null) {
                server = "a private message";
            }
            whisper(author.getName() + " mentioned you in " + server + ".", event, "200522195644973057");
        }

        if (messageText.startsWith(Constants.ZBotPrefix)) {
            String command = messageText.split(" ")[0];
            //Console("Command: " + command);

            switch (clean(command)) {
                case "storenote":
                case "snote":
                case "sn":
                    NoteList.sortNotes();
                    Note note = new Note();
                    note.noteName = messageText.split("<desc>")[0].replaceFirst(command, "").trim();
                    note.noteDesc = messageText.split("<desc>")[1].trim();
                    NoteList.StoredNotes.add(note);
                    respond(markdown(note.getInfo()), event);
                    printToConsole(note.getInfo());
                    printToConsole("Stored new note: " + note.noteName);
                    break;
                case "showsnote":
                case "note":
                    for (Note foundNote : NoteList.StoredNotes) {
                        if (foundNote.noteName.toLowerCase().contains(messageText.replaceFirst(messageText.split(" ")[0], "").toLowerCase().trim())) {
                            respond(markdown(foundNote.getInfo()), event);
                            printToConsole("Displaying spell: " + foundNote.noteName);
                        } else {
                            printToConsole(foundNote.noteName.trim() + " =/= " + messageText.replaceFirst(messageText.split(" ")[0].toLowerCase(), "").trim());
                        }
                    }
                    printToConsole("Finished displaying spells.");
                    break;
                case "listnotes":
                case "lnotes":
                case "ln":
                case "notelist":
                case "notel":
                case "nl":
                case "notes":
                    NoteList.sortNotes();
                    String noteList = "########## List of Stored Notes ##########\n";
                    for (Note foundSpell : NoteList.StoredNotes) {
                        noteList += foundSpell.noteName + "\n";
                    }
                    respond(markdown(noteList), event);
                    printToConsole("Listing all Stored Notes");
                    break;
                case "deletenote":
                case "dnote":
                case "dn":
                    printToConsole("Looking for note");
                    for (int i = 0; i < NoteList.StoredNotes.size(); i++) {
                        printToConsole("Testing " + i + " out of " + NoteList.StoredNotes.size());
                        if (NoteList.StoredNotes.get(i).noteName.trim().equals(messageText.replaceFirst(messageText.split(" ")[0].toLowerCase(), "").trim())) {
                            respond(fix("Deleted " + NoteList.StoredNotes.get(i).noteName), event);
                            printToConsole("Deleted " + NoteList.StoredNotes.get(i).noteName);
                            NoteList.StoredNotes.remove(i);
                            break;
                        } else {
                            printToConsole(NoteList.StoredNotes.get(i).noteName.trim() + " =/= " + messageText.replaceFirst(messageText.split(" ")[0].toLowerCase(), "").trim());
                        }
                    }
                    printToConsole("No note with that name found.");
                    break;
                case "save":
                    if (saveGame()) {
                        respond(fix("Game saved!"), event);
                        printToConsole("Game saved!");
                    } else {
                        respond("```diff\nZayle broke it! Tell her to check her serializable ipmlementations, baka!```", event);
                        printToConsole("Zayle broke it! Tell her to check her serializable ipmlementations, baka!");
                    }
                    break;
                case "commands":
                    String response = "";
                    response += "<Command arguments in square brackets are for the user to input. If something is in \"quote marks\" it must be included as seen.>\n"
                            + "<Slashes (/) denote multiple different way to use the same command. There are a couple other alternatives, but in the interests of\n"
                            + "readability I'm not listing every single variant here.>\n"
                            + "\n1.   " + Constants.ZBotPrefix + "commands\n Shows this list, which you probably know.\n"
                            + "\n2.   " + Constants.ZBotPrefix + "listnotes/notelist/notes\n Show a list of all the stored spells.\n"
                            + "\n3.   " + Constants.ZBotPrefix + "storenote [spell name] \"<desc>\" [spell description]\n Creates and stores a new spell.\n"
                            + "\n4.   " + Constants.ZBotPrefix + "deletenote [spell name]\n Delete a stored note, must type out the entire name to avoid accidents.\n"
                            + "\n5.   " + Constants.ZBotPrefix + "shownote/note [note name]\n Displays the stored information on all notes matching the specified name.\n"
                            + "\n6.   " + Constants.ZBotPrefix + "save\n Saves all changes made to the list of stored notes.\n"
                            + "\n7.   " + Constants.ZBotPrefix + "roll [expression]\n Rolls magical dice according the expression. Support addition and subtraction.\n"
                            + "\n8.   " + Constants.ZBotPrefix + "wildmagic [spell level]\n Rolls to see if you triggered a wild magic effect, rolls an effect if you did.\nCurrent chance is 1 in " + wmChance + " minus the spell's level.\n"
                            + "\n9.   " + Constants.ZBotPrefix + "forcewildmagic\n Displays a random wild magic effect from the table of " + wildMagic.effectList.size() + " effects\n"
                            + "\n\nChanges to the list of notes are ONLY saved when using the \"save\" command"
                            + "\n or when Zayle shuts the bot down safely. If you made a mistake that needs to be"
                            + "\n undone, let her know and she can shut me down without saving.";
                    respond(markdown(response), event);
                    printToConsole("Displaying command help.");
                    break;
                case "roll":
                case "r":
                    String string = roll(messageText.replaceFirst(command, ""));
                    respond(markdown(string), event);
                    break;
                case "wildmagic":
                case "wildmagic?":
                case "wm":
                case "wm?":
                    int wm = this.random.nextInt(wmChance) + 1;
                    int level = 0;

                    try {
                        level = Integer.parseInt(messageText.replaceFirst(command, "").trim());
                    } catch (Exception e) {

                    }

                    if (wm >= wmChance - level) {
                        if (primedPrivate != null & senderIsAdmin(event)) {
                            wme = primedPrivate;
                            primedPrivate = null;
                            if (event.getChannel() instanceof PrivateChannel & senderIsAdmin(event)) {
                                whisper(author.getName() + " just rolled a successful wild magic! \n " + wme, event, Constants.adminIds[0]);
                            }
                        } else if (primedPublic != null & senderIsAdmin(event)) {
                            wme = primedPublic;
                            primedPublic = null;
                            if (event.getChannel() instanceof PrivateChannel & senderIsAdmin(event)) {
                                whisper(author.getName() + " just rolled a successful wild magic! \n " + wme, event, Constants.adminIds[0]);
                            }
                        } else {
                            wme = wildMagic.getRandom();
                            if (event.getChannel() instanceof PrivateChannel & senderIsAdmin(event)) {
                                whisper(author.getName() + " just rolled a successful wild magic! \n " + wme, event, Constants.adminIds[0]);
                            }
                        }

                        for (int i = 0; i < primedPeople.size(); i++) {
                            ArrayList<String> chunk = primedPeople.get(i);
                            if (event.getJDA().getUserById(chunk.get(0)) != null) {
                                wme = chunk.get(1);
                                whisper(author.getName() + " just rolled a successful wild magic! \n " + wme, event, Constants.adminIds[0]);
                                primedPeople.remove(i);
                            }
                        }

                        respond(markdown("<" + wm + " " + Character.toChars(8805)[0] + " " + (wmChance - level) + ">\n" + wme), event);
                        printToConsole("<" + wm + " " + Character.toChars(8805)[0] + " " + (wmChance - level) + ">\n" + wme);

                    } else {
                        wme = null;
                        respond("```diff\n-[" + wm + " < " + (wmChance - level) + "]-\nNothing happens... This time.```", event);
                        printToConsole("-[" + wm + " < " + (wmChance - level) + "]-\nNothing happens... This time.```");
                    }

                    break;
                case "forcewildmagic":
                case "forcewild":
                case "fwm":
                    if (primedPrivate != null & author.getId().equals(Constants.adminIds[0])) {
                        wme = primedPrivate;
                        primedPrivate = null;
                    } else if (primedPublic != null & !author.getId().equals(Constants.adminIds[0])) {
                        wme = primedPublic;
                        primedPublic = null;
                    } else {
                        wme = wildMagic.getRandom();
                    }
                    respond(markdown("-[Here we go!][Hold on to your butt!!]-\n\n" + wme), event);
                    printToConsole("-[Here we go!][Hold on to your butt!!]-\n\n" + wme);
                    break;
                case "listwildmagic":
                    respond("```diff\n- Are you absolutely sure? This command can take a very long time to finish listing all the effects (5+ minutes). It WILL flood this chat channel.\n- Type ..listconfirm if you're sure.```", event);
                    listConfirm = true;
                    break;
                case "listconfirm":
                    if (listConfirm == true) {
                        for (String s : wildMagic.effectList) {
                            respond(markdown(s + "\n"), event);
                        }
                    }
                    listConfirm = false;
                    break;
                case "myid":
                    respond(fix(author.getName() + "'s Discord ID is " + author.getId()), event);
                    break;
                case "":
                    respond(markdown("?"), event);
                    break;
                    
                case "tt":
                case "temtable":
                case "temmietable":
                    String temlevel = messageText.replaceFirst(command, "").trim();
                    
                    
                    switch (Integer.parseInt(temlevel)) {
                        case 1:
                            respond(temTable.getOne(), event);
                            break;
                        case 2:
                            respond(temTable.getTwo(), event);
                            break;
                        case 3:
                            respond(temTable.getThree(), event);
                            break;
                        case 4:
                            respond(temTable.getFour(), event);
                            break;
                    }
                    break;
                    
                    //############################### Game Commands Below ###################################
                case "go":
                case "move":
                case "north":
                case "n":
                case "west":
                case "w":
                case "east":
                case "e":
                case "south":
                case "s":
                    String direction = "";
                    if (messageText.split(" ").length == 0) {
                        direction = command;
                    } else {
                        direction = messageText.replaceFirst(command, "").trim().toLowerCase();
                    }
                    String moveResult = "";
                    
                    if (player == null) {
                        respond(noChar, event);
                        break;
                    }
                    
                    if (!game.checkMove(player, direction)) {
                        respond(markdown("There's no exit there! Use the \"" + Constants.ZBotPrefix + "look\" command to look around."), event);
                        break;
                    }
                    
                    switch (direction.substring(0, 1)){
                        case "n":
                            respondIn(markdown(player.name + " goes through a door to the north"), event);
                            moveResult += "\n" + game.movePlayer(player, "n");
                            notifyOthers(markdown(player.name + " comes from a door to the south"), event);
                            break;
                        case "e":
                            respondIn(markdown(player.name + " goes through a door to the east"), event);
                            moveResult += "\n" + game.movePlayer(player, "e");
                            notifyOthers(markdown(player.name + " comes from a door to the west"), event);
                            break;
                        case "s":
                            respondIn(markdown(player.name + " goes through a door to the south"), event);
                            moveResult += "\n" + game.movePlayer(player, "s");
                            notifyOthers(markdown(player.name + " comes from a door to the north"), event);
                            break;
                        case "w":
                            respondIn(markdown(player.name + " goes through a door to the west"), event);
                            moveResult += "\n" + game.movePlayer(player, "w");
                            notifyOthers(markdown(player.name + " comes from a door to the east"), event);
                            break;
                    }
                    respond(markdown(moveResult), event);
                break;
                
                case "look":
                case "lookaround":
                    if (player == null) {
                        respond(noChar, event);
                        break;
                    }
                    respond(markdown(game.lookAround(player)), event);
                    break;
                case "spawn":
                    respond(fix(game.spawnPlayer(author.getName(), author.getId())), event);
                    respond(markdown(game.lookAround(game.getPlayerById(author.getId()))), event);
                    break;
                case "respawn":
                    if (player == null) {
                        respond(noChar, event);
                        break;
                    }
                    respond(markdown(game.respawn(player)), event);
                    break;
                case "say":
                    String sayText = messageText.replaceFirst(command, "").trim();
                    
                    if (player == null) {
                        respond(noChar, event);
                        break;
                    }
                    
                    sayText = "<" + player.name + ">: \"" + sayText + "\"";
                    respondIn(markdown(sayText), event);
                    break;
                case "emote":
                case "me":
                    String emoteText = messageText.replaceFirst(command, "").trim();
                    
                    if (player == null) {
                        respond(noChar, event);
                        break;
                    }
                    
                    emoteText = "> " + player.name + " " + emoteText;
                    respondIn(markdown(emoteText), event);
                    break;
                case "stats":
                    if (player == null) {
                        respond(noChar, event);
                        break;
                    }
                    
                    respond(markdown(game.getStatBlock(player)), event);
                    
                    break;
                    
                case "inventory":
                    if (player == null) {
                        respond(noChar, event);
                        break;
                    }
                    
                    respond(markdown(game.checkInventory(player)), event);
                    
                    break;
                    
                case "loot":
                    
                    if (player == null) {
                        respond(noChar, event);
                        break;
                    }
                    
                    respondIn(markdown(game.loot(player, messageText.replaceFirst(command, "").trim().toLowerCase())), event);
                    
                    break;
                    
                case "equip":
                    if (player == null) {
                        respond(noChar, event);
                        break;
                    }
                    
                    respondIn(markdown(game.equip(player, messageText.replaceFirst(command, "").trim().toLowerCase())), event);
                    
                    break;
                    
                case "unequip":
                    if (player == null) {
                        respond(noChar, event);
                        break;
                    }
                    
                    respondIn(markdown(game.unequip(player, messageText.replaceFirst(command, "").trim().toLowerCase())), event);
                    
                    break;
                case "inspect":
                    if (player == null) {
                        respond(noChar, event);
                        break;
                    }
                    
                    respond(markdown(game.inspect(player, messageText.replaceFirst(command, "").trim().toLowerCase())), event);
                    
                    break;
                    
                    // ############### game commands above #######################

            }
        } else {
            if (!author.isBot()) {
                diceMatcher = dicePattern.matcher(messageText);
                if (diceMatcher.find() & !message.getAuthor().getName().trim().toLowerCase().contains("comrade")) {
                    String result = roll(messageText);
                    if (result != null) {
                        respond(markdown(result), event);
                    }
                }
            }
        }


    }

    //This one if it's private.
    public void onPrivateMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String messageText = message.getContent();
        User author = message.getAuthor();

        /*if (!author.isBot()) {
            printToConsole("Private message received from " + authorName + "(" + author.getId() + ")");
            printToConsole("Private message Content: " + messageText);
            if (messageText.contains("<3")) {
                respond("<3", event);
                return;
            }
        }*/

        if (messageText.startsWith(Constants.ZBotPrefix)) {
            String command = messageText.split(" ")[0];
            //printToConsole("Command: " + command);

            //Zayle's commands go here;
            if (senderIsAdmin(event) | author.getId().equals(Constants.ZBotID)) {
                switch (clean(command)) {
                    case "save":
                        if (saveGame()) {
                            respond(fix("Game saved!"), event);
                        } else {
                            respond("```diff\nYou broke it! Check your serializable ipmlementations, baka!```", event);
                        }
                        return;
                    case "stop":
                        if (saveGame()) {
                            respond(fix("Game saved, shutting down."), event);
                            respondAll(fix("Game saved, shutting down."));
                        } else {
                            respond("```diff\nYou broke it! Check your serializable ipmlementations, baka!```\n\n```fix\nShutting down```", event);
                            respondAll("```diff\nYou broke it! Check your serializable ipmlementations, baka!```\n\n```fix\nShutting down```");
                        }
                        System.exit(0);
                        return;
                    case "bsp":
                        event.getJDA().getPresence().setPresence(Game.playing(messageText.replaceFirst(command, "").trim()), true);
                        respond("Status set to: Playing " + messageText.replaceFirst(command, "").trim(), event);
                        return;
                    case "bsw":
                        event.getJDA().getPresence().setPresence(Game.watching(messageText.replaceFirst(command, "").trim()), true);
                        respond("Status set to: Watching " + messageText.replaceFirst(command, "").trim(), event);
                        return;
                    case "prime":
                        int privateFoundIndex = 0;
                        //Check each effect in the table for a match
                        for (int i = 0; i < wildMagic.effectList.size(); i++) {
                            if (wildMagic.effectList.get(i).toLowerCase().contains(messageText.replaceFirst(command, ""))) {
                                //List all matching results
                                privateFoundIndex = i;
                                respond(wildMagic.effectList.get(privateFoundIndex), event);
                            }
                        }

                        //If nothing was found, set custom message
                        if (privateFoundIndex - 1 == -1) {
                            primedPrivate = messageText.replaceFirst(command, "").trim();
                            respond(fix(primedPrivate + " [Effect Primed]"), event);
                        } else {
                            //If something was found, prime it
                            primedPrivate = wildMagic.effectList.get(privateFoundIndex);
                            respond(fix(primedPrivate + " [Effect Primed]"), event);
                            privateFoundIndex = 0;
                        }
                        break;
                    case "primepublic":
                        int foundIndex = 0;
                        //Check each effect in the table for a match
                        for (int i = 0; i < wildMagic.effectList.size(); i++) {
                            if (wildMagic.effectList.get(i).toLowerCase().contains(messageText.replaceFirst(command, ""))) {
                                //List all matching results
                                foundIndex = i;
                                respond(wildMagic.effectList.get(foundIndex), event);
                            }
                        }

                        //If nothing was found, set custom message
                        if (foundIndex - 1 == -1) {
                            primedPublic = messageText.replaceFirst(command, "").trim();
                            respond(fix(primedPublic + " [Effect Primed]"), event);
                        } else {
                            //If something was found, prime it
                            primedPublic = wildMagic.effectList.get(foundIndex);
                            respond(fix(primedPublic + " [Effect Primed]"), event);
                            foundIndex = 0;
                        }
                        break;
                    case "checkprime":
                        respond(fix("[Primed for you] " + primedPrivate + "\n[Primed for others] " + primedPublic), event);
                        break;
                    case "resetprime":
                        primedPublic = null;
                        primedPrivate = null;
                        primedPeople = new ArrayList();
                        respond("Primed effects reset.", event);
                        break;
                    case "primeperson":
                        String primeMessage = messageText.replaceFirst(command, "").trim();
                        Boolean found = false;
                        String foundEffect = "You dun goofed.";

                        ArrayList<String> chunk = new ArrayList();

                        //Add User ID to chunk
                        if (Main.mainFrame.getSelectedUserId() != null) {
                            chunk.add(Main.mainFrame.getSelectedUserId());
                        } else {
                            respond("```diff\n- Nobody selected!```", event);
                            break;
                        }


                        //Prepare the effect to prime
                        for (int i = 0; i < wildMagic.effectList.size(); i++) {
                            if (wildMagic.effectList.get(i).toLowerCase().contains(primeMessage.toLowerCase().trim())) {
                                foundEffect = wildMagic.effectList.get(i);
                                respond(markdown(foundEffect), event);
                                found = true;
                            }
                        }

                        if (!found) {
                            foundEffect = primeMessage;
                        }

                        //Adding effect to chunk
                        chunk.add(foundEffect);

                        //Adding chunk to list
                        primedPeople.add(chunk);

                        respond(fix("Primed \"" + chunk.get(1) + "\" to " + event.getJDA().getUserById(chunk.get(0)).getName() + " (" + chunk.get(0) + ")"), event);

                        break;
                        
                    case "tpto":
                        Player tptoTarget = game.getPlayerByName(messageText.replaceFirst(command, "").trim());
                        usingPlayer = game.getPlayerById(author.getId());
                        
                        if (tptoTarget != null & usingPlayer != null) {
                            game.teleportAToB(usingPlayer, tptoTarget);
                            respond(markdown(usingPlayer.name + " has teleported to " + tptoTarget.name), event);
                        } else {
                            respond("`Target not found, or you haven't spawned.`", event);
                        }
                        break;
                    case "poop":
                        usingPlayer = game.getPlayerById(author.getId());
                        
                        if (messageText.toLowerCase().contains("poop staff")) {
                            respondIn(markdown(game.spawnStaff(usingPlayer)), event);
                        }
                        break;
                            


                    //############################################################# Zayle's Commands go above here! #############################################################


                }
            }
            switch (clean(command)) {
                //Commands go here!


                //############################################################# Private Commands go above here! #############################################################
                default:
                    break;
            }
        }


    }

    //Responds directly to an event, replying in whichever single channel ZBot was interacted with from.
    public Message respond(String message, MessageReceivedEvent e) {
        switch (e.getChannelType()) {
            case TEXT:
                if (message == null) {
                    return null;
                }
                //printToConsole("Responding to public message.");
                return e.getTextChannel().sendMessage(message).complete();
                
            case PRIVATE:
                if (message == null) {
                    return null;
                }
                //printToConsole("Responding to private message.");
                return e.getPrivateChannel().sendMessage(message).complete();
                
            case GROUP:
                if (message == null) {
                    break;
                }
                //printToConsole("Responding to group message.");
                return e.getGroup().sendMessage(message).complete();
            default:
                printToConsole("Unknown Channel Type, could not respond!");
                return null;
        }
        return null;
    }
    public Message respond(MessageEmbed message, MessageReceivedEvent e) {
        switch (e.getChannelType()) {
            case TEXT:
                if (message == null) {
                    return null;
                }
                //printToConsole("Responding to public message.");
                return e.getTextChannel().sendMessage(message).complete();
                
            case PRIVATE:
                if (message == null) {
                    return null;
                }
                //printToConsole("Responding to private message.");
                return e.getPrivateChannel().sendMessage(message).complete();
                
            case GROUP:
                if (message == null) {
                    break;
                }
                //printToConsole("Responding to group message.");
                return e.getGroup().sendMessage(message).complete();
            default:
                printToConsole("Unknown Channel Type, could not respond!");
                return null;
        }
        return null;
    }

    //Responds in all designated channels (Just the channel in the ZBot testing grounds by default)
    public void respondAll(String message) {

        for (TextChannel c : DesignatedChannels) {
            c.sendMessage(message).queue();
        }

    }

    //RespondIn sends a message to all players in the area the triggerer is in.
    public void respondIn(String message, MessageReceivedEvent e) {
        Player player = game.getPlayerById(e.getAuthor().getId());
        
        for (Player p : player.currentRoom.roomPlayers) {
            whisper(message, e, p.playerID);
        }
    }
    public void notifyOthers(String message, MessageReceivedEvent e) {
        Player player = game.getPlayerById(e.getAuthor().getId());
        
        for (Player p : player.currentRoom.roomPlayers) {
            if (!p.playerID.equals(e.getAuthor().getId())) {
                whisper(message, e, p.playerID);
            }
        }
    }

    //Whisper sends a message to one specific person
    public void whisper(String message, MessageReceivedEvent e, String id) {
        User u = e.getJDA().getUserById(id);
        if (u == null) {
            return;
        }
        u.openPrivateChannel().complete().sendMessage(message).queue();
        Main.mainFrame.printToConsole("Whispering " + u.getName() + ": " + message);
    }

    //Saves the game, obviously.
    public boolean saveGame() {
        try {

            gameFile = new File("game.ser");

            if (!gameFile.exists()) {
                gameFile.createNewFile();
            }

            FileOutputStream fileOut = new FileOutputStream(gameFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(game);

            objectOut.close();
            fileOut.close();

            printToConsole("Game saved!");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        try {

            spellFile = new File("spells.list");

            if (!spellFile.exists()) {
                spellFile.createNewFile();
            }

            FileOutputStream fileOut = new FileOutputStream(spellFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(NoteList);

            objectOut.close();
            fileOut.close();

            printToConsole("Spell List saved!");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Cleans up a string (Makes it lower case, trims it, and removes the ZBot Prefix)
    public String clean(String string) {
        String result;
        result = string.replaceFirst(Constants.ZBotPrefix, "").toLowerCase().trim();
        return result;
    }

    //Puts a message into a markdown box (For general use)
    public String markdown(String string) {
        return "```md\n" + string + "```";
    }

    //Puts a message into a "fix" box (For quick notifications)
    public String fix(String string) {
        return "```fix\n" + string + "```";
    }

    public void printToConsole(String string) {
        System.out.println(string);
        Main.mainFrame.printToConsole(string);
    }

    public String roll(String rollText) {
        //printToConsole("rollText = " + rollText);
        rollText = rollText.replaceAll("a d", "1d");


        ArrayList<String> finalRolls = new ArrayList();

        //Getting the dice to roll
        //printToConsole("Getting dice...");
        //Finding the parts of the string that equate to dice
        diceMatcher = dicePattern.matcher(rollText);
        ArrayList<String> diceToRoll = new ArrayList();
        while (diceMatcher.find()) {
            String s = diceMatcher.group();
            rollText = rollText.replaceFirst(s, "");
            diceToRoll.add(s);
            //printToConsole(s);
        }
        //Closing dice to roll

        //Getting the modifiers
        //printToConsole("Getting modifiers...");
        Matcher modifiers = Pattern.compile("[-+]\\d+").matcher(rollText.replace(" ", ""));
        ArrayList<String> modifiersToAdd = new ArrayList();
        while (modifiers.find()) {
            String s = modifiers.group();
            modifiersToAdd.add(s);
            //printToConsole(s);
        }

        //Rolling dice
        //printToConsole("Rolling dice...");
        Random random = new Random();
        int diceTotal = 0;
        for (String s : diceToRoll) {

            //Getting the arguments to roll with
            String[] temp = s.split("d");


            printToConsole("\nRolling " + temp[0] + "d" + temp[1] + "...");
            for (int i = 0; i < Integer.valueOf(temp[0]); i++) {
                //Rolling the dice
                int result = random.nextInt(Integer.valueOf(temp[1])) + 1;

                //printToConsole("Roll = " + result);

                //Adding the result to the total and the result ArrayList
                diceTotal += result;
                finalRolls.add(String.valueOf(result));
            }
            //printToConsole("Dice total = " + diceTotal);
        }

        //Totalling modifiers
        int modifierTotal = 0;
        for (String s : modifiersToAdd) {
            //printToConsole("Adding " + Integer.valueOf(s) + " to the modifier total.");
            modifierTotal += Integer.valueOf(s);
        }

        //printToConsole("Modifier total = " + modifierTotal);

        int grandTotal = modifierTotal + diceTotal;
        //printToConsole("Grand total = " + grandTotal);

        String string = "";

        int subInt = 0;
        for (int i = 0; i < diceToRoll.size(); i++) {
            string += "[" + diceToRoll.get(i) + "](";
            for (int i2 = 0; i2 < Integer.valueOf(diceToRoll.get(i).split("d")[0]); i2++) {
                string += finalRolls.get(subInt) + ", ";
                subInt++;
            }
            string = string.substring(0, string.length() - 2);
            string += ")\n";
        }
        if (modifierTotal > 0) {
            string += "\n(+ " + modifierTotal + ")";
        } else if (modifierTotal < 0) {
            string += "\n(- " + modifierTotal * (-1) + ")";
        }
        string += "\n< Grand total = " + grandTotal + " >";

        printToConsole(string + "\n");
        return string;
    }
    
    public boolean senderIsAdmin(MessageReceivedEvent e) {
        for (String s :Constants.adminIds) {
            if (s.equals(e.getAuthor().getId())){
                return true;
            }
        }
        return false;
    }
}//closing the class
