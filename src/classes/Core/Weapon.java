package classes.Core;

/**
 *
 * @author PC
 */
public abstract class Weapon extends Item implements java.io.Serializable{
    
    Weapon(){
        useType = UseType.EQUIP;
        itemType = ItemType.WEAPON;
        itemAccBonus = 10;
    }
    
    
    
    
}
