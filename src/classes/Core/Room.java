package classes.Core;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author PC
 */
public class Room implements java.io.Serializable{
    public RoomType roomType = RoomType.EMPTY;
    public enum RoomType{
        LOOT(5), TRAP(15), EMPTY(33), INFESTED(5), HOSTILE(60), SANCTUARY(1), TRAPPEDLOOT(10), GUARDEDLOOT(15), BOSS(4);
        
        public int chance;
        RoomType(int chance) {
            this.chance = chance;
        }

    }
    
    public boolean exitN = false;
    public boolean exitE = false;
    public boolean exitS = false;
    public boolean exitW = false;
    public boolean exitU = false;
    public boolean exitD = false;
    
    
    public ArrayList<Lootable> roomLoot = new ArrayList();
    public ArrayList<Enemy> roomEnemies = new ArrayList();
    public ArrayList<Player> roomPlayers = new ArrayList();
    
    public int floor;
    
    public Random RNG = new Random();
    
    Room(){
        
        //Deciding starting exits
        if (RNG.nextInt(2) == 1) {
            exitN = true;
        }
        if (RNG.nextInt(2) == 1) {
            exitE = true;
        }
        if (RNG.nextInt(2) == 1) {
            exitS = true;
        }
        if (RNG.nextInt(2) == 1) {
            exitW = true;
        }
        
        //If there are no exits, make one or two
        if (exitN == false & exitE == false & exitS == false & exitW == false) {
            int wall = RNG.nextInt(4) + 1;
            switch (wall){
                case 1:
                    exitN = true;
                    break;
                case 2:
                    exitE = true;
                    break;
                case 3:
                    exitS = true;
                    break;
                case 4:
                    exitW = true;
                    break;
            }
            if (RNG.nextInt(4) + 1 == 4) {
                switch (wall){
                    case 1:
                        exitN = true;
                        break;
                    case 2:
                        exitE = true;
                        break;
                    case 3:
                        exitS = true;
                        break;
                    case 4:
                        exitW = true;
                        break;
                }
            }
        }
        //Finished deciding exits
        
        //Picking a room type
        if (RNG.nextInt(100) < RoomType.SANCTUARY.chance) {
            roomType = RoomType.SANCTUARY;
        } else if (RNG.nextInt(100) < RoomType.LOOT.chance) {
            roomType = RoomType.LOOT;
        } else if (RNG.nextInt(100) < RoomType.INFESTED.chance) {
            roomType = RoomType.INFESTED;
        } else if (RNG.nextInt(100) < RoomType.TRAPPEDLOOT.chance) {
            roomType = RoomType.TRAPPEDLOOT;
        } else if (RNG.nextInt(100) < RoomType.TRAP.chance) {
            roomType = RoomType.TRAP;
        } else if (RNG.nextInt(100) < RoomType.GUARDEDLOOT.chance) {
            roomType = RoomType.GUARDEDLOOT;
        } else if (RNG.nextInt(100) < RoomType.HOSTILE.chance) {
            roomType = RoomType.HOSTILE;
        } else {
            roomType = RoomType.EMPTY;
        }
        //Finished picking a room type
        
        
        
    }
    
}
