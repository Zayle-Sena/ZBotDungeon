package classes.Core;

import Bot.CommandListener;
import classes.Core.Item.ItemRarity;
import classes.Core.Item.ItemType;
import classes.Core.Item.UseType;
import classes.Core.Room.RoomType;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Zayle Sena
 */
public class GameInstance implements java.io.Serializable {

    private transient GameRunner runner;

    transient MapGenerator generator = new MapGenerator();
    public ArrayList<Floor> floors = new ArrayList();

    int initialFloors = 3;
    public ArrayList<Player> allPlayers = new ArrayList();

    Random RNG = new Random();

    DecimalFormat formatter = new DecimalFormat("#,###");

    transient CommandListener cl;

    public GameInstance(CommandListener cl) {

        runner = new GameRunner(this);
        Thread gameThread = new Thread(runner);
        gameThread.start();

        for (int i = 0; i < initialFloors; i++) {
            Floor newFloor = new Floor();
            newFloor.setFloor(generator.generateFloor(100, 100, i));
            generator.populateFloor(newFloor, i + 1); //+1 so that the value is equal to the floor number, instead of starting from 0
            floors.add(newFloor);
        }


        this.cl = cl;
        int LOOT = 0, TRAP = 0, TRAPPEDLOOT = 0, GUARDEDLOOT = 0, HOSTILE = 0, SANCTUARY = 0, INFESTED = 0, EMPTY = 0;
        for (int y = 0; y < floors.size(); y++) {
            for (int z = 0; z < 100; z++) {
                for (int x = 0; x < 100; x++) {
                    Room room = floors.get(y).getRoom(x, z);
                    switch (room.roomType) {
                        case LOOT:
                            LOOT += 1;
                            break;
                        case TRAP:
                            TRAP += 1;
                            break;
                        case TRAPPEDLOOT:
                            TRAPPEDLOOT += 1;
                            break;
                        case GUARDEDLOOT:
                            GUARDEDLOOT += 1;
                            break;
                        case HOSTILE:
                            HOSTILE += 1;
                            break;
                        case SANCTUARY:
                            SANCTUARY += 1;
                            break;
                        case INFESTED:
                            INFESTED += 1;
                            break;
                        case EMPTY:
                            EMPTY += 1;
                            break;

                    }
                }
            }
        }


        String s = "";
        s += "Dungeon built! Room totals: ";
        s += "\n" + formatter.format(LOOT + TRAPPEDLOOT + GUARDEDLOOT) + " Loot rooms; " + formatter.format(LOOT) + " Plain, " + formatter.format(TRAPPEDLOOT) + " Trapped, " + formatter.format(GUARDEDLOOT) + " Guarded";
        s += "\n" + formatter.format(TRAP) + " Trap rooms";
        s += "\n" + formatter.format(HOSTILE) + " Hostile rooms and " + INFESTED + " Infested rooms";
        s += "\n" + formatter.format(EMPTY) + "Empty rooms";
        s += "\n" + formatter.format(SANCTUARY) + " Sanctuaries";
        s += "\nTOTAL ROOMS: " + formatter.format((LOOT + TRAPPEDLOOT + GUARDEDLOOT + TRAP + HOSTILE + INFESTED + EMPTY + SANCTUARY));
        cl.printToConsole(s);
    }

    public void gameUpdate() {
        
        for (Player p : this.allPlayers) {
            if (p.hp > p.getMaxHp()) {
                p.hp = p.getMaxHp();
            }
        }

        //PUT ALL TIME BASED UPDATES HERE:
        //System.out.println("Game tick!");

    }

    public void stopGame() {

        //May need to wait until the last tick to shutdown cleanly
        runner.isRunning = false;

    }

    public String spawnPlayer(String name, String id) {
        for (Player p : allPlayers) {
            if (p.playerID.equals(id)) {
                return "You already exist inside the dungeon!";
            }
        }
        
        int x = RNG.nextInt(100);
        int y = RNG.nextInt(100);
        Room startingRoom = floors.get(0).roomArray[x][y]; //Pick the magic (starting) room

        while (startingRoom.roomType != RoomType.SANCTUARY) {
            x = RNG.nextInt(100);
            y = RNG.nextInt(100);
            startingRoom = floors.get(0).roomArray[x][y];
        }

        Player player = new Player(name, id); //Create the player

        player.name = name;
        player.playerID = id;
        player.currentRoom = startingRoom; //Add the room to the player

        allPlayers.add(player); //Add the player to the global list of players
        startingRoom.roomPlayers.add(player); //And the player to the room
        player.hp = player.getMaxHp();
        System.out.println(name + " spawned at X: " + x + ", Y: " + y + " on floor one.");
        return "You've been placed into the dungeon with naught but the clothes on your back.\nGood luck!";
    }

    public Player getPlayerById(String playerId) {
        for (Player p : allPlayers) {
            if (p.playerID.equals(playerId)) {
                return p;
            }
        }
        return null;
    }

    public Player getPlayerByName(String playerName) {
        for (Player p : allPlayers) {
            if (p.name.toLowerCase().contains(playerName.toLowerCase())) {
                return p;
            }
        }
        return null;
    }

    public Room getRoom(int x, int y, int floor) {
        return floors.get(floor).getRoom(x, y);
    }

    public boolean checkMove(Player player, String direction) {
        switch (direction) {
            case "n": //north is y-1
                if (player.currentRoom.exitN) {
                    return true;
                }
                break;
            case "e": //east is x+1
                if (player.currentRoom.exitE) {
                    return true;
                }
                break;
            case "s": //south is y+1
                if (player.currentRoom.exitS) {
                    return true;
                }
                break;
            case "w": //west is x-1
                if (player.currentRoom.exitW) {
                    return true;
                }
                break;
        }
        return false;
    }

    public String movePlayer(Player player, String direction) {
        String output = "";
        
        Room startingRoom = player.currentRoom;
        int startingX = startingRoom.xCoord;
        int startingY = startingRoom.yCoord;
        int startingFloor = startingRoom.floor;
        Room destination = startingRoom;
        String cancelledMove = startingRoom.onExit(player);
        
        if (player.isDead){
            return "You're dead!\n"
                    + "Either wait around for somebody to help you (Which will probably never happen), or respawn with a new character using ..respawn";
        }
        
        if (cancelledMove != null) {
            return cancelledMove;
        }

        switch (direction) {
            case "n": //north is y-1
                destination = getRoom(startingX, startingY - 1, startingFloor);
                break;
            case "e": //east is x+1
                destination = getRoom(startingX + 1, startingY, startingFloor);
                break;
            case "s": //south is y+1
                destination = getRoom(startingX, startingY + 1, startingFloor);
                break;
            case "w": //west is x-1
                destination = getRoom(startingX - 1, startingY, startingFloor);
                break;
        }
        player.currentRoom.roomPlayers.remove(player); //Leave the previous room

        player.currentRoom = destination; //Change the room reference in the player
        player.currentRoom.roomPlayers.add(player); //Add the player reference in the room
        
        
        output += "\n" + lookAround(player);

        System.out.println(player.name + " moved (" + direction + " from X: " + startingRoom.xCoord + ", Y: " + startingRoom.yCoord + " to X: " + destination.xCoord + ", Y: " + destination.yCoord);
        
        return output;
    }
    
    public String respawn(Player player) {
        
        if (!player.isDead) {
            return "You can't respawn if you're not dead. Sorry!";
        }
        
        player = new Player(player.name, player.playerID);
        
        int x = RNG.nextInt(100);
        int y = RNG.nextInt(100);
        Room startingRoom = floors.get(0).roomArray[x][y]; //Pick the magic (starting) room

        while (startingRoom.roomType != RoomType.SANCTUARY) {
            x = RNG.nextInt(100);
            y = RNG.nextInt(100);
            startingRoom = floors.get(0).roomArray[x][y];
        }
        
        player.currentRoom = startingRoom; //Add the room to the player
        startingRoom.roomPlayers.add(player); //And the player to the room
        
        System.out.println(player.name + " spawned at X: " + x + ", Y: " + y + " on floor one.");
        return "You have successfully respawned! Good luck!";
    }

    public void teleportAToB(Player a, Player b) {
        a.currentRoom.roomPlayers.remove(a);
        a.currentRoom = b.currentRoom;
        b.currentRoom.roomPlayers.add(a);
    }

    public String lookAround(Player player) {
        String result;
        Room room = player.currentRoom;

        result = player.currentRoom.roomDesc;
        result += "\n\n<Players:> ";
        int count = player.currentRoom.roomPlayers.size() - 1;
        for (Player p : player.currentRoom.roomPlayers) {
            result += p.name;
            if (count != 0) {
                result += ", ";
            }
            count -= 1;
        }
        
        if (!player.currentRoom.roomLoot.isEmpty()){
            result += "\n\n<Loot:> ";
            count = player.currentRoom.roomLoot.size() - 1;
            for (Lootable l : player.currentRoom.roomLoot) {
                result += l.getName();
                if (count != 0) {
                    result += ", ";
                }
                count -= 1;
            }
        }

        result += "\n\n<Exits:>";

        if (room.exitN) {
            result += " NORTH";
        }
        if (room.exitE) {
            result += " EAST";
        }
        if (room.exitS) {
            result += " SOUTH";
        }
        if (room.exitW) {
            result += " WEST";
        }

        return result;
    }
    
    public String loot(Player looter, String lootableName) {
        String result = "Item not found.";
        for (int i = 0; i < looter.currentRoom.roomLoot.size(); i++ ) {
            Lootable l = looter.currentRoom.roomLoot.get(i);
            if (l.getName().toLowerCase().trim().contains(lootableName.toLowerCase().trim())) {
                result = l.loot(looter);
            }
        }
        
        return result;
    }
    
    public String checkInventory(Player player) {
        String result = "";
        
        for (Item i : player.Inventory) {
            result += i.getName() + "\n";
        }
        
        return result;
    }
    public String spawnStaff(Player player){
        Item staff = new Item();
        
        staff.setName("Zayle's Staff");
        staff.itemSpellBonus = 50;
        staff.itemWisBonus = 10;
        staff.itemInitBonus = 20;
        staff.itemMinDamageBonus = 200;
        staff.itemMaxDamageBonus = 250;
        staff.itemLevel = 1;
        staff.itemType = ItemType.WEAPON;
        staff.useType = UseType.EQUIP;
        staff.itemDesc = "A cool looking magical staff."
                + "\nOne of a kind.";
        staff.itemRarity = ItemRarity.UNIQUE;
        staff.breakable = false;
        staff.pointValue = 666;
        staff.itemAccBonus = 666;
        staff.itemHpBonus = 200;
        staff.itemDexBonus = 10;
        
        player.currentRoom.roomLoot.add(staff);
        return player.name + " poops out a fancy looking staff!";
    }
    
    public String equip(Player player, String itemName) {
        String result = "Item not found";
        for (Item i : player.Inventory) {
            if (i.getName().toLowerCase().trim().contains(itemName.toLowerCase().trim())) {
                 result = i.equip(player);
                }
            }
        return result;
    }
    public String unequip(Player player, String itemName) {
        String result = "Item not found";
        for (Item i : player.Inventory) {
            if (i.getName().toLowerCase().trim().contains(itemName.toLowerCase().trim())) {
                 result = i.unequip(player);
                }
            }
        return result;
    }
    
    public String getStatBlock(Player player){
        return player.getStatBlock();
    }
    
    public String inspect(Player player, String itemName) {
        String result = "Item not found.";
        for (Item i : player.Inventory) {
            if (i.getName().toLowerCase().trim().contains(itemName.toLowerCase().trim())) {
                 result = i.getStats();
                }
            }
        return result;
    }
    
}

class GameRunner implements Runnable, java.io.Serializable {

    private GameInstance main;
    public boolean isRunning = false;

    public GameRunner(GameInstance gameMain) {
        main = gameMain;
        isRunning = true;
    }

    @Override
    public void run() {

        long lastTime = System.currentTimeMillis();

        do {
            long nowTime = System.currentTimeMillis();
            long unprocessedTicks = (nowTime - lastTime) / 1000; //One unprocessed tick for every second

            if (unprocessedTicks >= 1) {
                main.gameUpdate();
                unprocessedTicks = 0;
                lastTime = nowTime;
            }
        } while (isRunning);
    }

}