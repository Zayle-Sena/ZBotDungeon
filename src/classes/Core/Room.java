package classes.Core;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author PC
 */
public class Room implements java.io.Serializable {
    public RoomType roomType = RoomType.EMPTY;

    public String roomDesc = "This room is empty, far too empty! It wasn't generated correctly!";

    public enum Direction {
        NORTH, SOUTH, EAST, WEST;
    }

    public enum RoomType {
        LOOT(5), TRAP(5), EMPTY(0), INFESTED(0.005), HOSTILE(20), SANCTUARY(1), TRAPPEDLOOT(5), GUARDEDLOOT(5), BOSS(0);

        public double chance;

        RoomType(double chance) {
            this.chance = chance;
        }

    }

    public boolean exitN = false;
    public boolean exitE = false;
    public boolean exitS = false;
    public boolean exitW = false;
    public boolean exitU = false;
    public boolean exitD = false;

    public boolean visitedByGenerator = false;


    public ArrayList<Lootable> roomLoot = new ArrayList();
    public ArrayList<Enemy> roomEnemies = new ArrayList();
    public ArrayList<Player> roomPlayers = new ArrayList();

    public int xCoord, yCoord, floor;

    public Random RNG = new Random();

    public Room(int x, int y, int f) {

        xCoord = x;
        yCoord = y;
        floor = f;

        //Picking a room type
        if (RNG.nextDouble() * 100 < RoomType.SANCTUARY.chance) {
            roomType = RoomType.SANCTUARY;
        } else if (RNG.nextDouble() * 100 < RoomType.LOOT.chance) {
            roomType = RoomType.LOOT;
        } else if (RNG.nextDouble() * 100 < RoomType.INFESTED.chance) {
            roomType = RoomType.INFESTED;
        } else if (RNG.nextDouble() * 100 < RoomType.TRAPPEDLOOT.chance) {
            roomType = RoomType.TRAPPEDLOOT;
        } else if (RNG.nextDouble() * 100 < RoomType.TRAP.chance) {
            roomType = RoomType.TRAP;
        } else if (RNG.nextDouble() * 100 < RoomType.GUARDEDLOOT.chance) {
            roomType = RoomType.GUARDEDLOOT;
        } else if (RNG.nextDouble() * 100 < RoomType.HOSTILE.chance) {
            roomType = RoomType.HOSTILE;
        } else {
            roomType = RoomType.EMPTY;
        }
        //Finished picking a room type


    }


    public void setExit(Direction dir){

        switch (dir){

            case EAST:
                exitE = true;
                return;

            case WEST:
                exitW = true;
                return;

            case NORTH:
                exitN = true;
                return;

            case SOUTH:
                exitS = true;
                return;

        }

    }

}
