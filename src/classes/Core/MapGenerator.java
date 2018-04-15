package classes.Core;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 *
 * @author Alexander Ruston
 *
 */
public class MapGenerator {


    public Room[][] backtrackingGenerator(int sizeX, int sizeY){

        int startX, startY;
        Room[][] allRooms = new Room[sizeX][sizeY];

        //Initialise all rooms
        for (int i = 0; i < sizeX; i++){
            for (int k = 0; k < sizeY; k++){
                allRooms[i][k] = new Room(i,k);
            }
        }

        Stack<Room> backtrackStack = new Stack<>();

        ArrayList<Room.Direction> dirsToVisit = new ArrayList<>();
        Room currentRoom = allRooms[49][49];
        currentRoom.visitedByGenerator = true;

        boolean isGenerating = true;

        while (isGenerating) {

            //System.out.println("Current room coords: " + currentRoom.xCoord + " , " + currentRoom.yCoord);

            dirsToVisit = getUnvisitedDirections(currentRoom,allRooms);

            if (!dirsToVisit.isEmpty()) {

                backtrackStack.push(currentRoom);

                Random rand = new Random();
                Room.Direction visitingDir = dirsToVisit.get(rand.nextInt(dirsToVisit.size()));
                System.out.println("Carving a path: " + visitingDir.toString());
                currentRoom.setExit(visitingDir);

                Room visitngRoom = getRoomAt(currentRoom,visitingDir,allRooms);
                visitngRoom.setExit(getOpposite(visitingDir));
                visitngRoom.visitedByGenerator = true;

                currentRoom = visitngRoom;

            } else if (!backtrackStack.empty()) {
                currentRoom = backtrackStack.pop();
            } else {
                isGenerating = false;
            }
        }

        Room firstRoom = allRooms[0][0];

        if(firstRoom.exitS){
            System.out.println("OPEN SOUTH");
        }
        if(firstRoom.exitN){
            System.out.println("OPEN NORTH");
        }if(firstRoom.exitE){
            System.out.println("OPEN EARTH");
        }
        if(firstRoom.exitW){
            System.out.println("OPEN WEST");
        }



        return allRooms;

    }


    public ArrayList<Room.Direction> getUnvisitedDirections(Room currentRoom, Room[][] allRooms){

        ArrayList<Room.Direction> univisitedRooms = new ArrayList<>();

        for(Room.Direction d : Room.Direction.values()){

            Room checkedRoom = getRoomAt(currentRoom, d, allRooms);
            if (checkedRoom == null){
                continue;
            }

            if (!checkedRoom.visitedByGenerator){
                univisitedRooms.add(d);
            }

        }

        return univisitedRooms;
    }

    public Room getRoomAt(Room currentRoom, Room.Direction dir, Room[][] allRooms){

        int currentRoomX = currentRoom.xCoord;
        int currentRoomY = currentRoom.yCoord;

        switch (dir){

            case NORTH:
                if((currentRoomY - 1) >= 0) {
                    return allRooms[currentRoomX][currentRoomY - 1];
                }
                break;

            case SOUTH:
                if((currentRoomY + 1) < allRooms[0].length){
                    return allRooms[currentRoomX][currentRoomY + 1];
                }
                break;
            case EAST:
                if((currentRoomX + 1) < allRooms.length){
                    return allRooms[currentRoomX + 1][currentRoomY];
                }
                break;
            case WEST:
                if((currentRoomX - 1) >= 0){
                    return allRooms[currentRoomX - 1][currentRoomY];
                }
                break;

        }

        return null;
    }

    public Room.Direction getOpposite(Room.Direction dir){

        switch (dir){

            case EAST:
                return Room.Direction.WEST;

            case WEST:
                return Room.Direction.EAST;

            case NORTH:
                return Room.Direction.SOUTH;

            case SOUTH:
                return Room.Direction.NORTH;

        }

        return null;

    }

}
