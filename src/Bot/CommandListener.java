package Bot;
import classes.Core.*;
import classes.Misc.Note;
import classes.Misc.NoteList;
import classes.Misc.WildTable;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.*;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.events.message.*;
/**
 *
 * @author Zayle Sena
 */
public class CommandListener extends ListenerAdapter{
    public GameInstance game;
    ArrayList<TextChannel> DesignatedChannels = new ArrayList();
    
    File gameFile;
    File spellFile;
    FileInputStream fileIn;
    ObjectInputStream objectIn;
    
    public NoteList SL;
    public WildTable wildMagic;
    public String primedPrivate;
    public String primedPublic;
    public ArrayList<ArrayList<String>> primedPeople = new ArrayList();
    
    Random random = new Random();
    int wmChance = 15;
    String wme;
    
    boolean listConfirm = false;
    
    Pattern dicePattern = Pattern.compile("(\\d+|a\\s)[d]\\d+");
    Matcher diceMatcher;
    
    CommandListener() {
        boolean loadSuccess = false;
        SL = new NoteList();
        wildMagic = new WildTable();
        //Trying to load the game
        try {
            gameFile = new File("game.ser");
            
            //If there's a saved game
            if (gameFile.exists()) {
                //Load it
                fileIn = new FileInputStream(gameFile);
                objectIn = new ObjectInputStream(fileIn);
            
                printToConsole("Game found! Loading!");
                game = (GameInstance)objectIn.readObject();
            fileIn.close();
            objectIn.close();
            loadSuccess = true;
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
                SL = (NoteList)objectIn.readObject();
            fileIn.close();
            objectIn.close();
            loadSuccess = true;
            //If there isn't
            } else {
                //Start new game
                printToConsole("Notes not found, starting new instance.");
                SL = new NoteList();
            }  
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (game == null) {
            //This will run if the game is found to be incompatible
            printToConsole("Game save corrupted! Creating new game.");
            gameFile.delete();
            game = new GameInstance(this);
        }
        if (SL == null) {
            //This will run if the game is found to be incompatible
            printToConsole("Spell List corrupted! Creating new Spell List.");
            spellFile.delete();
            SL = new NoteList();
        }
        
        
    DesignatedChannels.add(Connection.discord.getGuildById("318490999556931584").getTextChannelsByName("zbot_channel", true).get(0));
    printToConsole("Default Channel added");
    printToConsole("Command Listener: Command Listener initialized");
        
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

            if (!author.getId().equals(Constants.ZBotID) | messageText.contains(Constants.ZBotPrefix)) {
                printToConsole("Message received from " + authorName + "(" + author.getId() + ")");
                printToConsole("Message Content: " + messageText);
                
                int count = 0;
                if (messageText.toLowerCase().contains("prefix")) {
                    count += 3;
                    printToConsole("\"prefix\" found, +3");
                }
                if (messageText.toLowerCase().contains("zbot")) {
                    count += 3;
                    printToConsole("\"zbot\" found, +3");
                } else if (messageText.toLowerCase().contains("bot")) {
                    count += 2;
                    printToConsole("\"bot\" found, +2");
                }
                if (messageText.toLowerCase().contains("what is") | messageText.toLowerCase().contains("what's")) {
                    count += 1;
                    printToConsole("\"what is\" found, +1");
                }
                if (messageText.toLowerCase().contains("command")) {
                    count += 2;
                    printToConsole("\"command\" found, +2");
                }
                if (messageText.toLowerCase().contains("?")) {
                    count += 1;
                    printToConsole("\"?\" found, +1");
                }
                if (messageText.toLowerCase().contains("tell")) {
                    count += 1;
                    printToConsole("\"tell\" found, +1");
                }
                if (count > 4) {
                    respond("The current prefix is \"" + Constants.ZBotPrefix + "\"", event);
                    return;
                }
                if (messageText.toLowerCase().contains("lewd")){
                    respond("^^^", event);
                }
                if ((messageText.toLowerCase().contains("shut up") 
                        |(messageText.toLowerCase().contains("quiet")
                        | messageText.toLowerCase().contains("shush"))) &
                        messageText.toLowerCase().contains("zbot")) {
                    respond("Sorry ;-;", event);
                    return;
                }
                if (messageText.toLowerCase().contains("cookie")) {
                    if (random.nextInt(3) == 0) {
                        respond("*Steals the cookie*", event);
                    }
                }
                if (messageText.toLowerCase().contains("thank") | messageText.toLowerCase().contains("ty")) {
                    if (event.getChannel().getHistoryBefore(event.getMessageId(), 1).complete().getRetrievedHistory().get(0).getAuthor().getId().equals(Constants.ZBotID)){
                        printToConsole(event.getChannel().getHistoryBefore(event.getMessageId(), 1).complete().getRetrievedHistory().get(0).getContent());
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
                    server =  "a private message";
                }
                whisper(author.getName() + " mentioned you in " + server + ".", event, Constants.ownerID);
            }
            if ((messageText.toLowerCase().contains("luvia") | messageText.toLowerCase().contains("undyne")) & !author.getId().equals(Constants.ZBotID)) {
                String server = null;
                try {
                    server = event.getChannel().getName() + " in " + event.getGuild().getName();
                } catch (Exception e) {
                }
                
                if (server == null) {
                    server =  "a private message";
                }
                whisper(author.getName() + " mentioned you in " + server + ".", event, "200522195644973057");
            }

            if (messageText.startsWith(Constants.ZBotPrefix)) {
                String command = messageText.split(" ")[0];
                printToConsole("Command: " + command);
                
                switch (clean(command)) {
                    case "storespell":
                    case "sspell":
                    case "ss":
                        SL.sortNotes();
                        Note spell = new Note();
                        spell.noteName = messageText.split("<desc>")[0].replaceFirst(command, "").trim();
                        spell.noteDesc = messageText.split("<desc>")[1].trim();
                        SL.StoredNotes.add(spell);
                        respond(markdown(spell.getInfo()), event);
                        printToConsole(spell.getInfo());
                        printToConsole("Stored new spell: " + spell.noteName);
                        break;
                    case "showspell":
                    case "spell":
                        for (Note foundSpell : SL.StoredNotes) {
                            if (foundSpell.noteName.toLowerCase().contains(messageText.replaceFirst(messageText.split(" ")[0], "").toLowerCase().trim())) {
                                respond(markdown(foundSpell.getInfo()), event);
                                printToConsole("Displaying spell: " + foundSpell.noteName);
                            } else {
                                printToConsole(foundSpell.noteName.trim() + " =/= " + messageText.replaceFirst(messageText.split(" ")[0].toLowerCase(), "").trim());
                            }
                        }
                        printToConsole("Finished displaying spells.");
                        break;
                    case "listspells":
                    case "lspells":
                    case "ls":
                    case "spelllist":
                    case "spelll":
                    case "sl":
                    case "spells":
                        SL.sortNotes();
                        String spellList = "########## List of Stored Spells ##########\n";
                        for (Note foundSpell : SL.StoredNotes) {
                                spellList += foundSpell.noteName + "\n";
                        }
                        respond(markdown(spellList), event);
                        printToConsole("Listing all Stored Spells");
                        break;
                    case "deletespell":
                    case "dspell":
                    case "ds":
                        printToConsole("Looking for spell");
                        for (int i = 0 ; i < SL.StoredNotes.size() ; i++) {
                            printToConsole("Testing " + i + " out of " + SL.StoredNotes.size());
                            if (SL.StoredNotes.get(i).noteName.trim().equals(messageText.replaceFirst(messageText.split(" ")[0].toLowerCase(), "").trim())) {
                                respond(fix("Deleted " + SL.StoredNotes.get(i).noteName), event);
                                printToConsole("Deleted " + SL.StoredNotes.get(i).noteName);
                                SL.StoredNotes.remove(i);
                                break;
                            } else {
                                printToConsole(SL.StoredNotes.get(i).noteName.trim() + " =/= " + messageText.replaceFirst(messageText.split(" ")[0].toLowerCase(), "").trim());
                            }
                        }
                        printToConsole("No spell with that name found.");
                        break;
                    case "save":
                            if (saveGame()){
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
                                + "\n2.   " + Constants.ZBotPrefix + "listspells/spelllist/spells\n Show a list of all the stored spells.\n"
                                + "\n3.   " + Constants.ZBotPrefix + "storespell [spell name] \"<desc>\" [spell description]\n Creates and stores a new spell.\n"
                                + "\n4.   " + Constants.ZBotPrefix + "deletespell [spell name]\n Delete a stored spell, must type out the entire name to avoid accidents.\n"
                                + "\n5.   " + Constants.ZBotPrefix + "showspell/spell [spell name]\n Displays the stored information on all spells matching the inputted name.\n"
                                + "\n6.   " + Constants.ZBotPrefix + "save\n Saves all changes made to the list of stored spells.\n"
                                + "\n7.   " + Constants.ZBotPrefix + "roll [expression]\n Rolls magical dice according the expression. Support addition and subtraction.\n"
                                + "\n8.   " + Constants.ZBotPrefix + "wildmagic [spell level]\n Rolls to see if you triggered a wild magic effect, rolls an effect if you did. Current chance is \n"
                                + "\n9.   " + Constants.ZBotPrefix + "forcewildmagic\n Displays a random wild magic effect from the table of " + wildMagic.effectList.size() + " effects\n"
                                + "\n\nChanges to the list of spells are ONLY saved when using the \"save\" command"
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
                            if (primedPrivate != null & author.getId().equals(Constants.ownerID)) {
                                wme = primedPrivate;
                                primedPrivate = null;
                                if (event.getChannel() instanceof PrivateChannel & !author.getId().equals(Constants.ownerID)) {
                                    whisper(author.getName() + " just rolled a successful wild magic! \n " + wme, event, Constants.ownerID);
                                }
                            } else if (primedPublic != null & !author.getId().equals(Constants.ownerID)) {
                                wme = primedPublic;
                                primedPublic = null;
                                if (event.getChannel() instanceof PrivateChannel & !author.getId().equals(Constants.ownerID)) {
                                    whisper(author.getName() + " just rolled a successful wild magic! \n " + wme, event, Constants.ownerID);
                                }
                            } else {
                                wme = wildMagic.getRandom();
                                if (event.getChannel() instanceof PrivateChannel & !author.getId().equals(Constants.ownerID)) {
                                    whisper(author.getName() + " just rolled a successful wild magic! \n " + wme, event, Constants.ownerID);
                                }
                            }
                            
                            for (int i = 0 ; i < primedPeople.size() ; i++) {
                                ArrayList<String> chunk = primedPeople.get(i);
                                if (event.getJDA().getUserById(chunk.get(0)) != null) {
                                    wme = chunk.get(1);
                                    whisper(author.getName() + " just rolled a successful wild magic! \n " + wme, event, Constants.ownerID);
                                    primedPeople.remove(i);
                                }
                            }
                            
                            respond(markdown("<" + wm + " " + Character.toChars(8805)[0] + " "  + (wmChance - level)+ ">\n" + wme), event);
                            printToConsole("<" + wm + " "  + Character.toChars(8805)[0] + " "  + (wmChance - level)+ ">\n" + wme);
                                
                        } else {
                            wme = null;
                            respond("```diff\n-[" + wm + " < " + (wmChance - level) + "]-\nNothing happens... This time.```", event);
                            printToConsole("-[" + wm + " < " + (wmChance - level) + "]-\nNothing happens... This time.```");
                        }
                        
                        break;
                    case "forcewildmagic":
                    case "forcewild":
                    case "fwm":
                        if (primedPrivate != null & author.getId().equals(Constants.ownerID)) {
                            wme = primedPrivate;
                            primedPrivate = null;
                        } else if (primedPublic != null & !author.getId().equals(Constants.ownerID)) {
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
                        
                }
            } else {
                if (!author.isBot()){
                    diceMatcher = dicePattern.matcher(messageText);
                    if (diceMatcher.find() ) {
                        String result = roll(messageText);
                        if (result != null) {
                            respond(markdown(result), event);
                        }
                    }
                }
            }
        
        
    }
    //This one if it's private.
    public void onPrivateMessageReceived(MessageReceivedEvent event){
        Message message = event.getMessage();
        String messageText = message.getContent();
        User author = message.getAuthor();
        String authorName = author.getName();
        
        if (!author.isBot()) {
            printToConsole("Private message received from " + authorName + "(" + author.getId() + ")");
            printToConsole("Private message Content: " + messageText);
            if (messageText.contains("<3")) {
                respond("<3", event);
                return;
            }
        }
        
        if (messageText.startsWith(Constants.ZBotPrefix)) {
            String command = messageText.split(" ")[0];
            printToConsole("Command: " + command);
            
            //Zayle's commands go here;
            if (author.getId().equals(Constants.ownerID) | author.getId().equals(Constants.ZBotID)) {
                switch(clean(command)) {
                    case "save":
                            if (saveGame()){
                                respond(fix("Game saved!"), event);
                            } else {
                                respond("```diff\nYou broke it! Check your serializable ipmlementations, baka!```", event);
                            }
                        return;
                    case "stop":
                            if (saveGame()){
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
                        for (int i = 0 ; i < wildMagic.effectList.size() ; i++) {
                            if (wildMagic.effectList.get(i).toLowerCase().contains(messageText.replaceFirst(command, ""))){
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
                        for (int i = 0 ; i < wildMagic.effectList.size() ; i++) {
                            if (wildMagic.effectList.get(i).toLowerCase().contains(messageText.replaceFirst(command, ""))){
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
                    case "say":
                        respond(messageText.replaceFirst(command, "").trim(), event);
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
                        for (int i = 0 ; i < wildMagic.effectList.size() ; i++) {
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
    public void respond(String message, MessageReceivedEvent e){
        switch (e.getChannelType()) {
            case TEXT:
                if (message == null){
                    break;
                }
                printToConsole("Responding to public message.");
                e.getTextChannel().sendMessage(message).queue();
                break;
            case PRIVATE:
                if (message == null){
                    break;
                }
                printToConsole("Responding to private message.");
                e.getPrivateChannel().sendMessage(message).queue();
                break;
            case GROUP:
                if (message == null){
                    break;
                }
                printToConsole("Responding to group message.");
                e.getGroup().sendMessage(message).queue();
                break;
            default:
                printToConsole("Unknown Channel Type!");
                break;
        }
    }
    
    //Responds in all designated channels (Just the channel in the ZBot testing grounds by default)
    public void respondAll(String message){
        
        for (TextChannel c : DesignatedChannels) {
                c.sendMessage(message).queue();
        }
        
    } 
    
    //RespondIn sends a message to all players in the area the triggerer is in.
    public void respondIn(String message, MessageReceivedEvent e) {    
    }
    
    //Whisper sends a message to one specific person
    public void whisper(String message, MessageReceivedEvent e, String id){
        User u = e.getJDA().getUserById(id);
        if (u == null) {
            return;
        }
        u.openPrivateChannel().complete().sendMessage(message).queue();
        Main.mainFrame.printToConsole("Whispering " + u.getName() + ": " + message);
    }
    
    //Saves the game, obviously.
    public boolean saveGame(){
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
                            
                objectOut.writeObject(SL);
                            
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
    public String clean(String string){
        String result;
        result = string.replaceFirst(Constants.ZBotPrefix, "").toLowerCase().trim();
        return result;
    }
    
    //Puts a message into a markdown box (For general use)
    public String markdown(String string){
        return "```md\n" + string + "```";
    }
    //Puts a message into a "fix" box (For quick notifications)
    public String fix(String string){
        return "```fix\n" + string + "```";
    }
    
    public String parse(String string) {
        return "";
    }
    
    public void printToConsole(String string){
        System.out.println(string);
        Main.mainFrame.printToConsole(string);
    }
    
    public String roll(String rollText){
        //printToConsole("rollText = " + rollText);
        rollText = rollText.replaceAll("a d", "1d");
        

        ArrayList<String> finalRolls = new ArrayList();

        //Getting the dice to roll
        //printToConsole("Getting dice...");
        //Finding the parts of the string that equate to dice
        diceMatcher = dicePattern.matcher(rollText);
        ArrayList<String> diceToRoll = new ArrayList();
        while (diceMatcher.find()){
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
            for (int i = 0 ; i < Integer.valueOf(temp[0]) ; i++) {
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
        for (int i = 0 ; i < diceToRoll.size() ; i++){
            string += "[" + diceToRoll.get(i) + "](";
            for (int i2 = 0 ; i2 < Integer.valueOf(diceToRoll.get(i).split("d")[0]) ; i2++){
                string += finalRolls.get(subInt) + ", ";
                subInt++;
            }
            string = string.substring(0, string.length()-2);
            string += ")\n";
        }
        if (modifierTotal > 0) {
            string += "\n(+ " + modifierTotal + ")";
        } else if (modifierTotal < 0) {
            string += "\n(- " + modifierTotal*(-1) + ")";
        }
        string += "\n< Grand total = " + grandTotal + " >";

        printToConsole(string + "\n");
        return string;
    }
}//closing the class
