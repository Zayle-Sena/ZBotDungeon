package classes.Core;

import Bot.CommandListener;
import Bot.Main;
import classes.Core.Room.RoomType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Zayle Sena
 */
public class GameInstance implements java.io.Serializable {
    transient MapGenerator generator = new MapGenerator();
    public ArrayList<Floor> floors = new ArrayList();
    int initialFloors = 3;
    public ArrayList<Player> allPlayers = new ArrayList();
    
    Random RNG = new Random();

    DecimalFormat formatter = new DecimalFormat("#,###");

    transient CommandListener cl;

    public GameInstance(CommandListener cl) {

        for (int i = 0; i < initialFloors; i++){
            Floor newFloor = new Floor();
            newFloor.setFloor(generator.backtrackingGenerator(100,100,i));
            floors.add(newFloor);
        }


        this.cl = cl;
        int LOOT = 0, TRAP = 0, TRAPPEDLOOT = 0, GUARDEDLOOT = 0, HOSTILE = 0, SANCTUARY = 0, INFESTED = 0, EMPTY = 0;
        for (int y = 0; y < floors.size(); y++) {
            for (int z = 0; z < 100; z++) {
                for (int x = 0; x < 100; x++) {
                    Room room = floors.get(y).getRoom(x,z);
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
                    if (x == 0) {
                        room.exitW = false;
                    }
                    if (x == 99) {
                        room.exitE = false;
                    }
                    if (z == 0) {
                        room.exitN = false;
                    }
                    if (z == 99) {
                        room.exitS = false;
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

        public void spawnPlayer(String name, String id) {
            int x = RNG.nextInt(100);
            int y = RNG.nextInt(100);
            Room startingRoom = floors.get(0).roomArray[x][y]; //Pick the magic (starting) room
            
            while (startingRoom.roomType != RoomType.SANCTUARY) {
                x = RNG.nextInt(100);
                y = RNG.nextInt(100);
                startingRoom = floors.get(0).roomArray[x][y];
            }
            
            Player player = new Player(); //Create the player
            
            player.name = name;
            player.playerID = id;
            player.currentRoom = startingRoom; //Add the room to the player
            
            allPlayers.add(player); //Add the player to the global list of players
            startingRoom.roomPlayers.add(player); //And the player to the room
            System.out.println(name + " spawned at X: " + x + ", Y: " + y + " on floor one.");
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
        public void movePlayer(Player player, String direction){
            Room startingRoom = player.currentRoom;
            int startingX = startingRoom.xCoord;
            int startingY = startingRoom.yCoord;
            int startingFloor = startingRoom.floor;
            Room destination = startingRoom;
            
            switch (direction) {
                case "n": //north is y-1
                    destination = getRoom(startingX, startingY-1, startingFloor);
                    break;
                case "e": //east is x+1
                    destination = getRoom(startingX+1, startingY, startingFloor);
                    break;
                case "s": //south is y+1
                    destination = getRoom(startingX, startingY+1, startingFloor);
                    break;
                case "w": //west is x-1
                    destination = getRoom(startingX-1, startingY, startingFloor);
                    break;
            }
            player.currentRoom.roomPlayers.remove(player); //Leave the previous room
            
            player.currentRoom = destination; //Change the room reference in the player
            player.currentRoom.roomPlayers.add(player); //Add the player reference in the room
            
            Main.mainFrame.printToConsole(player.name + " moved (" + direction + " from X: " + startingRoom.xCoord + ", Y: " + startingRoom.yCoord + " to X: " + destination.xCoord + ", Y: " + destination.yCoord);
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
            result += "\n<Players:> ";
            for (Player p : player.currentRoom.roomPlayers) {
                result += "<" + p.name + ">";
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

}
