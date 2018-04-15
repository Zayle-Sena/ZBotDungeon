package classes.Core;

/**
 *
 * @author PC
 */
public abstract class Item implements java.io.Serializable, Lootable{
    String itemName = "Generic Item";
    String itemDesc = "Generic Description";
    Boolean equipped = false;
    
    ItemRarity itemRarity = ItemRarity.JUNK;
    ItemType itemType = ItemType.MISC;
    UseType useType = UseType.NONE;
    
    int pointValue = 0;
    Boolean autoConvert = false;
    
    int itemAccBonus = 0;
    int itemEvaBonus = 0;
    int itemDamBonus = 0;
    int itemInitBonus = 0;
    int itemArmourBonus = 0;
    int itemPerBonus = 0;
    int itemSpellBonus = 0;
    int itemHpBonus = 0;
    int itemWillSaveBonus = 0;
    int itemDexSaveBonus = 0;
    int itemStrSaveBonus = 0;
    int itemConSaveBonus = 0;
    
    
    
    public enum ItemRarity{
        
        JUNK(20), COMMON(75), UNCOMMON(15), SCARCE(10), RARE(5), EPIC(1),  LEGENDARY(0.1), UNIQUE(0.01);
        
        private final double chance;
        ItemRarity(double rarity) {
            this.chance = rarity;
        }
        public double getChance(){
            return chance;
        }
    }
    public enum ItemType{
        MISC, WEAPON, HAT, ARMOUR, PANTS, BOOTS
    }
    
    public enum UseType{
        TARGETED, GENERIC, EQUIP, NONE
    }
    
    public String use(){
        if (useType != useType.GENERIC) {
            return "Nothing happens.";
        } else {
            return "That's not how you use this item!";
        }
    }
    public String useOn(Monster target){
        if (useType != UseType.TARGETED) {
            return "Nothing happens.";
        } else {
            return "That's not how you use this item!";
        }
    }
    public String equip(Player equipper){
        
        if (useType == UseType.EQUIP) {
            
            for (Item i : equipper.Inventory) {
                
                if (i.equipped & (i.itemType == this.itemType) & itemType != ItemType.MISC){
                    return "You already have a " + i.itemType.toString() + " equipped";
                }
                
            }
            
            equipped = true;
            return itemName + " equipped (" + equipper.name + ")";
            
        } else {
            
            return itemName + " can't be equipped";
            
        }
        
    }
    
    public String loot(Player looter){
        looter.Inventory.add(this);
        return itemName + " get (" + looter.name + ")";
    }
 
    
}
    
