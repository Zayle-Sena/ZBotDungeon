package classes.Core;

/**
 *
 * @author Alexander Ruston
 * Comment the second
 *
 */

public class Floor implements java.io.Serializable{

    public Room[][] roomArray;

    public void setFloor(Room[][] floor){
        roomArray = floor;
    }

    public Room getRoom(int x, int y){
        return roomArray[x][y];
    }

}
