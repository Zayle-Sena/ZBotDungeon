package classes.Core;

import Bot.CommandListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author Zayle Sena
 */
public class GameInstance implements java.io.Serializable {
    MapGenerator generator = new MapGenerator();
    public ArrayList<Floor> floors = new ArrayList<>();
    int initialFloors = 3;


    DecimalFormat formatter = new DecimalFormat("#,###");

    transient CommandListener cl;

    public GameInstance(CommandListener cl) {

        for (int i = 0; i < initialFloors; i++){
            Floor newFloor = new Floor();
            newFloor.setFloor(generator.backtrackingGenerator(100,100));
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


}
