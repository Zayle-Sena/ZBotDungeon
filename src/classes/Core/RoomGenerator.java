package classes.Core;

import Bot.Main;
import static classes.Core.Room.RoomType.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author PC
 */
public class RoomGenerator {
    Random RNG;
    ItemGenerator itemGenerator = new ItemGenerator();
    
    ArrayList lootRoomDescriptions = new ArrayList();
    ArrayList lootRoomTrappedDescriptions = new ArrayList();
    ArrayList lootRoomGuardedDescriptions = new ArrayList();
    ArrayList sanctuaryDescriptions = new ArrayList();
    ArrayList emptyDescriptions = new ArrayList();
    ArrayList hostileDescriptions = new ArrayList();
    ArrayList infestedDescriptions = new ArrayList();
    ArrayList trapDescriptions = new ArrayList();
    ArrayList bossDescriptions = new ArrayList();
    
    public RoomGenerator(){
        RNG = new Random();
        populateLists();
    }
    
    private void populateLists(){
        
        
        Scanner reader;
        File roomDescLoot = new File("roomDescLoot.txt");
        File roomDescLootTrapped = new File("roomDescLootTrapped.txt");
        File roomDescLootGuarded = new File("roomDescLootGuarded.txt");
        File roomDescSanctuary = new File("roomDescSanctuary.txt");
        File roomDescEmpty = new File("roomDescEmpty.txt");
        File roomDescHostile = new File("roomDescHostile.txt");
        File roomDescInfested = new File("roomDescInfested.txt");
        File roomDescTrap = new File("roomDescTrap.txt");
        File roomDescBoss = new File("roomDescBoss.txt");
        
        try {
            //Start roomDescLoot list
            if (!roomDescLoot.exists()) {
                roomDescLoot.createNewFile();

            } else {
                    reader = new Scanner(roomDescLoot);

                    int lrdc = 0;

                    while (reader.hasNextLine()) {
                        lootRoomDescriptions.add(reader.nextLine());
                        lrdc++;
                    }
                    Main.mainFrame.printToConsole("Loot Room Descriptions Added: " + lrdc);
            }
            //End roomDescLoot list

            //Start roomDescLootTrapped list
            if (!roomDescLootTrapped.exists()) {
                roomDescLootTrapped.createNewFile();

            } else {
                    reader = new Scanner(roomDescLootTrapped);

                    int lrdc = 0;

                    while (reader.hasNextLine()) {
                        lootRoomTrappedDescriptions.add(reader.nextLine());
                        lrdc++;
                    }
                    Main.mainFrame.printToConsole("Trapped Loot Room Descriptions Added: " + lrdc);
            }
            //End roomDescLootTrapped list

            //Start roomDescLootGuarded list
            if (!roomDescLootGuarded.exists()) {
                roomDescLootGuarded.createNewFile();        
            } else {

                    reader = new Scanner(roomDescLootGuarded);

                    int lrdc = 0;

                    while (reader.hasNextLine()) {
                        lootRoomGuardedDescriptions.add(reader.nextLine());
                        lrdc++;
                    }
                    Main.mainFrame.printToConsole("Guarded Loot Room Descriptions Added: " + lrdc);
            }
            //End roomDescLootGuarded list

            //Start roomDescSanctuary list
            if (!roomDescSanctuary.exists()) {
                roomDescSanctuary.createNewFile();

            } else {
                    reader = new Scanner(roomDescSanctuary);

                    int lrdc = 0;

                    while (reader.hasNextLine()) {
                        sanctuaryDescriptions.add(reader.nextLine());
                        lrdc++;
                    }
                    Main.mainFrame.printToConsole("Sanctuary Descriptions Added: " + lrdc);
            }
            //End roomDescSanctuary list
            
            //Start roomDescEmpty list
            if (!roomDescEmpty.exists()) {
                roomDescEmpty.createNewFile();

            } else {
                    reader = new Scanner(roomDescEmpty);

                    int lrdc = 0;

                    while (reader.hasNextLine()) {
                        emptyDescriptions.add(reader.nextLine());
                        lrdc++;
                    }
                    Main.mainFrame.printToConsole("Empty Room Descriptions Added: " + lrdc);
            }
            //End roomDescEmpty list
            
            //Start roomDescHostile list
            if (!roomDescHostile.exists()) {
                roomDescHostile.createNewFile();

            } else {
                    reader = new Scanner(roomDescHostile);

                    int lrdc = 0;

                    while (reader.hasNextLine()) {
                        hostileDescriptions.add(reader.nextLine());
                        lrdc++;
                    }
                    Main.mainFrame.printToConsole("Hostile Room Descriptions Added: " + lrdc);
            }
            //End roomDescHostile list
            
            //Start roomDescInfested list
            if (!roomDescInfested.exists()) {
                roomDescInfested.createNewFile();

            } else {
                    reader = new Scanner(roomDescInfested);

                    int lrdc = 0;

                    while (reader.hasNextLine()) {
                        infestedDescriptions.add(reader.nextLine());
                        lrdc++;
                    }
                    Main.mainFrame.printToConsole("Infested Room Descriptions Added: " + lrdc);
            }
            //End roomDescInfested list
            
            //Start roomDescTrap list
            if (!roomDescTrap.exists()) {
                roomDescTrap.createNewFile();

            } else {
                    reader = new Scanner(roomDescTrap);

                    int lrdc = 0;

                    while (reader.hasNextLine()) {
                        trapDescriptions.add(reader.nextLine());
                        lrdc++;
                    }
                    Main.mainFrame.printToConsole("Trap Room Descriptions Added: " + lrdc);
            }
            //End roomDescTrap list
            
            //Start roomDescBoss list
            if (!roomDescBoss.exists()) {
                roomDescBoss.createNewFile();

            } else {
                    reader = new Scanner(roomDescBoss);

                    int lrdc = 0;

                    while (reader.hasNextLine()) {
                        bossDescriptions.add(reader.nextLine());
                        lrdc++;
                    }
                    Main.mainFrame.printToConsole("Boss Room Descriptions Added: " + lrdc);
            }
            //End roomDescBoss list

        } catch (Exception e) {}
    }
    
    public Room generateLootRoom(int x, int y, int floor){
        Room room = new Room(x, y, floor, LOOT);
        
        int itemCount = RNG.nextInt(3) + (int)Math.ceil(floor/10);
        room.roomDesc = "Loot room with " + itemCount + " items.";
        
        for (int i = 0; i < itemCount; i++) {
            if (RNG.nextInt(100/(floor + 1)) == 0) {
                room.roomLoot.add(itemGenerator.newItem(floor + 1, true));
            } else {
                room.roomLoot.add(itemGenerator.newItem(floor + 1, false));
            }
        }
        
        return room;
    }
}
