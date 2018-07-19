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
    RoomGenerator roomGenerator = new RoomGenerator();


    public Room[][] generateFloor(int sizeX, int sizeY, int floor){

        Room[][] allRooms = new Room[sizeX][sizeY];

        //Initialise all rooms
        for (int i = 0; i < sizeX; i++){
            for (int k = 0; k < sizeY; k++){
                Room room = new Room(i,k, floor);
                room = populateRoom(room);
                allRooms[i][k] = room;
            }
        }

        Stack<Room> backtrackStack = new Stack<>();

        ArrayList<Room.Direction> dirsToVisit;
        Room currentRoom = allRooms[49][49];
        currentRoom.visitedByGenerator = true;

        //Backtracking Algorithm
        boolean isGenerating = true;
        while (isGenerating) {

            dirsToVisit = getUnvisitedDirections(currentRoom,allRooms);

            if (!dirsToVisit.isEmpty()) {

                backtrackStack.push(currentRoom);

                Random rand = new Random();
                Room.Direction visitingDir = dirsToVisit.get(rand.nextInt(dirsToVisit.size()));
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

        return allRooms;

    }


    private ArrayList<Room.Direction> getUnvisitedDirections(Room currentRoom, Room[][] allRooms){

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

    private Room.Direction getOpposite(Room.Direction dir){

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
    
    public void populateFloor(Floor floor, int Level){
        for (int x = 0; x < 100; x++){
            for (int y = 0; y < 100; y++){
                populateRoom(floor.getRoom(x, y)); //populate each room with the things it needs.
            }
        }
    }
    
    public Room populateRoom(Room room){
        switch (room.roomType) {
                        case LOOT:
                            return roomGenerator.generateLootRoom(room.xCoord, room.yCoord, room.floor);
                        case TRAP:
                            return roomGenerator.generateTrappedRoom(room.xCoord, room.yCoord, room.floor);
                        case TRAPPEDLOOT:
                            return roomGenerator.generateTrappedLootRoom(room.xCoord, room.yCoord, room.floor);
                        case GUARDEDLOOT:
                            return roomGenerator.generateGuardedLootRoom(room.xCoord, room.yCoord, room.floor);
                        case HOSTILE:
                            return roomGenerator.generateHostileRoom(room.xCoord, room.yCoord, room.floor);
                        case SANCTUARY:
                            return roomGenerator.generateSanctuary(room.xCoord, room.yCoord, room.floor);
                        case INFESTED:
                            return roomGenerator.generateInfestedRoom(room.xCoord, room.yCoord, room.floor);
                        case EMPTY:
                            return roomGenerator.generateEmptyRoom(room.xCoord, room.yCoord, room.floor);
                    }
        return room;
    }
}
