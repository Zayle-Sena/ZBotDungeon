package classes.Core;

import static classes.Core.Item.ItemType.*;
import java.util.Random;

/**
 *
 * @author PC
 */
public class ItemGenerator {
    
    Random RNG;
    ItemList PregenItems = new ItemList();
    
    public ItemGenerator(){
        RNG = new Random();
    }
    
    public Item randomItem(int floor, boolean isContainer){
        Item item = new Item();
        item.itemLevel = floor;
        
        
        if (RNG.nextInt(PregenItems.size() + 8) > PregenItems.size()) {
            if (isContainer) {
                for (int i = 0; i < item.itemRarity.getTier(); i++) {
                    item.contents.add(randomItem(floor, false));
                    item.setName("Treasure Chest");
                }
                item.itemType = MISC;
            } else {
                switch (item.itemType) {
                    case WEAPON:
                        item.setName("Weapon");
                        generateWeaponDamage(item, floor);
                    break;
                    case ARMOUR:
                        item.setName("Armour");
                        generateArmourStats(item, floor);
                        break;
                    case PANTS:
                        item.setName("Pants");
                        generateArmourStats(item, floor);
                        break;
                    case HAT:
                        item.setName("Hat");
                        generateHatStats(item, floor);
                        break;
                    case BOOTS:
                        item.setName("Boots");
                        generateBootStats(item, floor);
                        break;
                    default:
                        item.setName("Unidentifiable debris");

                }
            }
        } else {
            item = PregenItems.getRandom();
        }
        
        return item;
        
    }
    
    public void generateWeaponDamage(Item item, int level){
        item.itemMinDamageBonus = (RNG.nextInt(level) + RNG.nextInt(3) + level * item.itemRarity.getTier());
        item.itemMaxDamageBonus = item.itemMinDamageBonus + RNG.nextInt(level);
    }
    
    public void generateArmourStats(Item item, int level) {        
        item.itemArmourBonus = RNG.nextInt(level) + RNG.nextInt(3) + level * item.itemRarity.getTier();
        item.itemEvaBonus = RNG.nextInt(level) + RNG.nextInt(3) + level * item.itemRarity.getTier() - RNG.nextInt(level) + RNG.nextInt(3) + level * item.itemRarity.getTier();
        item.itemHpBonus = RNG.nextInt(level * 10);
        
        if (item.itemEvaBonus < 0) {
            item.itemArmourBonus -= item.itemEvaBonus;
        }
    }
    
    public void generateBootStats(Item item, int level){
        item.itemEvaBonus = RNG.nextInt(level) + RNG.nextInt(3) + level * item.itemRarity.getTier();
        item.itemDexBonus = statNumber(item);
        item.itemInitBonus = statNumber(item);
    }
    
    public void generateHatStats(Item item, int level){
        item.itemIntBonus = statNumber(item);
        item.itemChaBonus = statNumber(item);
        item.itemWisBonus = statNumber(item);
        item.itemArmourBonus = RNG.nextInt(level) + RNG.nextInt(3) * item.itemRarity.getTier();
        item.itemEvaBonus = (RNG.nextInt(level) + RNG.nextInt(3) + level * item.itemRarity.getTier()) - item.itemArmourBonus;
        if (item.itemArmourBonus > item.itemEvaBonus) {
            item.itemPerceptionBonus = statNumber(item) - 5;
        } else {
            item.itemPerceptionBonus = statNumber(item);
        }
    }
    public void generateMiscStats(Item item, int level){
        int statOne = RNG.nextInt(14);
        int statTwo = RNG.nextInt(14);
        
        switch (statOne) {
            case 0:
                item.itemAccBonus += statNumber(item);
                break;
            case 1:
                item.itemArmourBonus += statNumber(item);
                break;
            case 2:
                item.itemChaBonus += statNumber(item);
                break;
            case 3:
                item.itemConBonus += statNumber(item);
                break;
            case 4:
                item.itemDexBonus += statNumber(item);
                break;
            case 5:
                item.itemEvaBonus += statNumber(item);
                break;
            case 6:
                item.itemHpBonus += statNumber(item) * 3;
                break;
            case 7:
                item.itemInitBonus += statNumber(item);
                break;
            case 8:
                item.itemIntBonus += statNumber(item);
                break;
            case 9:
                if (RNG.nextInt(2) == 0) {
                    item.itemMinDamageBonus += statNumber(item);
                } else {
                    item.itemMaxDamageBonus += statNumber(item);
                }
                break;
            case 10:
                item.itemPerceptionBonus += statNumber(item);
                break;
            case 11:
                item.itemSpellBonus += statNumber(item);
                break;
            case 12:
                item.itemStrBonus += statNumber(item);
                break;
            case 13:
                item.itemWisBonus += statNumber(item);
                break;
            
        }
        switch (statTwo) {
            case 0:
                item.itemAccBonus += statNumber(item);
                break;
            case 1:
                item.itemArmourBonus += statNumber(item);
                break;
            case 2:
                item.itemChaBonus += statNumber(item);
                break;
            case 3:
                item.itemConBonus += statNumber(item);
                break;
            case 4:
                item.itemDexBonus += statNumber(item);
                break;
            case 5:
                item.itemEvaBonus += statNumber(item);
                break;
            case 6:
                item.itemHpBonus += statNumber(item) * 3;
                break;
            case 7:
                item.itemInitBonus += statNumber(item);
                break;
            case 8:
                item.itemIntBonus += statNumber(item);
                break;
            case 9:
                if (RNG.nextInt(2) == 0) {
                    item.itemMinDamageBonus += statNumber(item);
                } else {
                    item.itemMaxDamageBonus += statNumber(item);
                }
                break;
            case 10:
                item.itemPerceptionBonus += statNumber(item);
                break;
            case 11:
                item.itemSpellBonus += statNumber(item);
                break;
            case 12:
                item.itemStrBonus += statNumber(item);
                break;
            case 13:
                item.itemWisBonus += statNumber(item);
                break;
            
        }
    }
    
    private int statNumber(Item item){
        int i = item.itemRarity.getTier() + 1 - RNG.nextInt(7);
        
        if (i < 0){ 
            i = 0;
        }
        
        return i;
    }
}
