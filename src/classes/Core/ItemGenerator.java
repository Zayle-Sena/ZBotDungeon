package classes.Core;

import classes.Core.Item.ItemRarity;
import static classes.Core.Item.ItemType.*;
import java.util.Random;

/**
 *
 * @author PC
 */
public class ItemGenerator {
    
    Random RNG;
    
    public ItemGenerator(){
        RNG = new Random();
    }
    
    public Item newItem(int floor, boolean isContainer){
        Item item = new Item();
        item.itemLevel = floor;
        
        int[] armourValues = {0,0};
        int[] damageValues = {0,0};
        int[] bootStats = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] hatStats = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        
        switch (item.itemType) {
            case WEAPON:
                item.setName("Weapon");
                damageValues = generateWeaponDamage(item.itemRarity, floor);
                item.itemMinDamageBonus = damageValues[0];
                item.itemMaxDamageBonus = damageValues[1];
            break;
            case ARMOUR:
                item.setName("Armour");
                armourValues= generateArmourValue(item.itemRarity, floor);
                item.itemArmourBonus = armourValues[0];
                item.itemEvaBonus = armourValues[1];
                item.itemHpBonus = armourValues[2];
                break;
            case PANTS:
                item.setName("Pants");
                armourValues= generateArmourValue(item.itemRarity, floor);
                item.itemArmourBonus = armourValues[0];
                item.itemEvaBonus = armourValues[1];
                break;
            case HAT:
                item.setName("Hat");
                hatStats = generateHatStats(item.itemRarity, floor);
                item.setStats(hatStats);
                
                armourValues = generateArmourValue(item.itemRarity, floor);
                item.itemArmourBonus = (int)Math.ceil(armourValues[0]/2);
                item.itemEvaBonus = (int)Math.ceil(armourValues[1]/4);
                break;
            case BOOTS:
                item.setName("Boots");
                bootStats = generateBootStats(item.itemRarity, floor);
                item.setStats(bootStats);
                
                armourValues = generateArmourValue(item.itemRarity, floor);
                item.itemArmourBonus = (int)Math.ceil(armourValues[0]/4);
                item.itemEvaBonus = (int)Math.ceil(armourValues[1]/2);
                item.itemHpBonus = (int)Math.round(armourValues[2]/4);
                break;
            case MISC:
                item.setName("Misc item");
                break;
                
        }
        
        return item;
        
    }
    
    public int[] generateWeaponDamage(ItemRarity rarity, int level){
        int damages[] = {0, 0};
        
        switch(rarity){
            case JUNK:
                damages[0] = 0;
                damages[1] = RNG.nextInt(2);
                break;
            case COMMON:
                damages[0] = RNG.nextInt((int)Math.ceil(level/5) + 1);
                damages[1] = damages[0] + RNG.nextInt((int)Math.ceil(level/5) + 1);
                break;
            case UNCOMMON:
                damages[0] = RNG.nextInt((int)Math.ceil(level/3) + 1);
                damages[1] = damages[0] + RNG.nextInt((int)Math.ceil(level/3) + 1);
                break;
            case SCARCE:
                damages[0] = RNG.nextInt((int)Math.ceil(level/2) + 1);
                damages[1] = damages[0] + RNG.nextInt((int)Math.ceil(level/2) + 1);
                break;
            case RARE:
                damages[0] = RNG.nextInt((int)Math.ceil(level));
                damages[1] = damages[0] + RNG.nextInt((int)Math.ceil(level + 1) + 1);
                break;
            case EPIC:
                damages[0] = RNG.nextInt((int)Math.ceil(level + level) + 1);
                damages[1] = damages[0] + RNG.nextInt((int)Math.ceil(level + level + 1) + 1);
                break;
            case LEGENDARY:
                damages[0] = RNG.nextInt((int)Math.ceil(level + (level * 2) + 5) + level);
                damages[1] = damages[0] + RNG.nextInt((int)Math.ceil((level * 2) + 5) + level);
                break;
            case UNIQUE:
                damages[0] = RNG.nextInt((int)Math.ceil(level + (level * 2) + 5) + (level * 3));
                damages[1] = damages[0] + RNG.nextInt((int)Math.ceil((level * RNG.nextInt(3) + 1) + 5));
                break;
        }
        
        return damages;
    }
    
    public int[] generateArmourValue(ItemRarity rarity, int level) {
        int values[] = {0, 0, 0};
        
        switch (rarity){
            case JUNK:
                values[0] = 0;
                values[1] = RNG.nextInt(1);
                values [2] = 0;
                break;
            case COMMON:
                values[0] = RNG.nextInt((int)Math.ceil(level/5) + 1) + RNG.nextInt((int)Math.ceil(level/5) + 1);
                values[1] = RNG.nextInt((int)Math.ceil(level)/5 + 1) + RNG.nextInt((int)Math.ceil(level)/5 + 1) - values[0];
                values [2] = RNG.nextInt((int)Math.ceil(level/4) + 1);
                break;
            case UNCOMMON:
                values[0] = RNG.nextInt((int)Math.ceil(level/2) + 1) + RNG.nextInt((int)Math.ceil(level/2) + 1) + (int)Math.ceil(level/2);
                values[1] = RNG.nextInt(level * 2) + RNG.nextInt(level * 2) - (int)Math.ceil(values[0]/2);
                values [2] = RNG.nextInt((int)Math.ceil(level/2) + 1);
                break;
            case SCARCE:
                values[0] = RNG.nextInt(level * 3) + level - (int)Math.ceil(level/4);
                values[1] = RNG.nextInt((int)Math.ceil(level/2 + 1) + 19) + RNG.nextInt(level * 2) - (int)Math.ceil(values[0]/4);
                values [2] = RNG.nextInt(level);
                break;
            case RARE:
                values[0] = RNG.nextInt(level * 5) + level - (int)Math.ceil(level/2);
                values[1] = RNG.nextInt((int)Math.ceil(level/2) + 20) + RNG.nextInt(level * 2);
                values [2] = RNG.nextInt(level) + (int)Math.ceil(level/2);
                break;
            case EPIC:
                values[0] = RNG.nextInt(level * 10) + (level * 2) - (int)Math.ceil(level/2);
                values[1] = RNG.nextInt(level * 8) + 20 - RNG.nextInt(values[0] * 3);
                values [2] = RNG.nextInt(level) + level/2 + 20;
                if (values[1] < 0) {
                    values[0] += values[1] * -1;
                }
                if (values[0] < level * 2) {
                    values[1] += values[0];
                }
                break;
            case LEGENDARY:
                values[0] = RNG.nextInt(level * 20) + (level * 2) - (int)Math.ceil(level/2);
                values[1] = RNG.nextInt(level * 12) + 20 - RNG.nextInt(values[0] * 3);
                values [2] = RNG.nextInt(level) + level + 20;
                if (values[1] < 0) {
                    values[0] += values[1] * -2;
                }
                if (values[0] < level * 3) {
                    values[1] += values[0];
                }
                break;
            case UNIQUE:
                values[0] = RNG.nextInt(level * 30) + (level * 2) - (int)Math.ceil(level * 15);
                values[1] = RNG.nextInt(level * 20) + 20 - RNG.nextInt(values[0] * 10);
                values [2] = RNG.nextInt(level) + level*2 + 50;
                if (values[1] < 0) {
                    values[0] += values[1] * -4;
                }
                if (values[0] < level * 3) {
                    values[1] += values[0] * 3;
                }
                break;
        }
        
        return values;
    }
    
    public int[] generateBootStats(ItemRarity rarity, int level){
        int[] values = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        
        switch(rarity) {
            case JUNK:
                break;
            case COMMON:
                values[4] = RNG.nextInt((int)Math.ceil(level/10) + 1); //Initiative
                break;
            case UNCOMMON:
                values[0] = RNG.nextInt((int)Math.ceil(level/10) + 1); //Accuracy
                values[4] = RNG.nextInt((int)Math.ceil(level/4) + 1); //Initiative
                values[10] = RNG.nextInt((int)Math.ceil(level/10) + 1); //Dex
                break;
            case SCARCE:
                values[0] = RNG.nextInt((int)Math.ceil(level/6) + 1); //Accuracy
                values[4] = RNG.nextInt(2) + 1; //Initiative
                values[10] = RNG.nextInt((int)Math.ceil(level/4) + 2); //Dex
                values[11] = RNG.nextInt((int)Math.ceil(level/6) + 1); //Str
                break;
            case RARE:
                values[0] = RNG.nextInt((int)Math.ceil(level/4) + 2); //Accuracy
                values[4] = RNG.nextInt(2) + 2; //Initiative
                values[10] = RNG.nextInt((int)Math.ceil(level/2) + 2); //Dex
                values[11] = RNG.nextInt((int)Math.ceil(level/4) + 1); //Str
                values[12] = RNG.nextInt((int)Math.ceil(level/6) + 1); //Con
                break;
            case EPIC:
                values[0] = RNG.nextInt((int)Math.ceil(level/2) + 5); //Accuracy
                values[4] = RNG.nextInt(3) + 3; //Initiative
                values[10] = RNG.nextInt((int)Math.ceil(level/2) + 5); //Dex
                values[11] = RNG.nextInt((int)Math.ceil(level/4) + 3); //Str
                values[12] = RNG.nextInt((int)Math.ceil(level/6) + 3); //Con
                break;
            case LEGENDARY:
                values[0] = RNG.nextInt((int)Math.ceil(level/2) + 15); //Accuracy
                values[4] = RNG.nextInt(4) + 4; //Initiative
                values[10] = RNG.nextInt((int)Math.ceil(level/2) + 10); //Dex
                values[11] = RNG.nextInt((int)Math.ceil(level/4) + 6); //Str
                values[12] = RNG.nextInt((int)Math.ceil(level/6) + 6); //Con
                break;
            case UNIQUE:
                values[0] = RNG.nextInt(level + 15); //Accuracy
                values[4] = RNG.nextInt(5) + 5; //Initiative
                values[10] = RNG.nextInt(level + 10); //Dex
                values[11] = RNG.nextInt(level + 6); //Str
                values[12] = RNG.nextInt(level + 6); //Con
        }
        
        return values;
    }
    
    public int[] generateHatStats(ItemRarity rarity, int level){
        int[] values = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        
        switch(rarity) {
            case JUNK:
                break;
            case COMMON:
                values[4] = RNG.nextInt((int)Math.ceil(level/10) + 1); //Initiative
                break;
            case UNCOMMON:
                values[0] = RNG.nextInt((int)Math.ceil(level/10) + 1); //Accuracy
                values[4] = RNG.nextInt((int)Math.ceil(level/4) + 1); //Initiative
                values[13] = RNG.nextInt((int)Math.ceil(level/10) + 1); //Int
                values[14] = RNG.nextInt((int)Math.ceil(level/2) + 1); //Cha
                break;
            case SCARCE:
                values[0] = RNG.nextInt((int)Math.ceil(level/6) + 1); //Accuracy
                values[4] = RNG.nextInt(2); //Initiative
                values[14] = RNG.nextInt((int)Math.ceil(level/2) + 2); //Cha
                values[13] = RNG.nextInt((int)Math.ceil(level/6) + 1); //Int
                break;
            case RARE:
                values[0] = RNG.nextInt((int)Math.ceil(level/4) + 2); //Accuracy
                values[4] = RNG.nextInt(2) + 1; //Initiative
                values[14] = RNG.nextInt((int)Math.ceil(level/4) + 2); //Cha
                values[13] = RNG.nextInt((int)Math.ceil(level/4) + 1); //Int
                break;
            case EPIC:
                values[0] = RNG.nextInt((int)Math.ceil(level/2) + 5); //Accuracy
                values[4] = RNG.nextInt(3) + 1; //Initiative
                values[13] = RNG.nextInt((int)Math.ceil(level/2) + 5); //Int
                values[8] = RNG.nextInt((int)Math.ceil(level/4) + 1); //HP
                values[14] = RNG.nextInt((int)Math.ceil(level/6) + 2); //Cha
                break;
            case LEGENDARY:
                values[0] = RNG.nextInt((int)Math.ceil(level/2) + 15); //Accuracy
                values[4] = RNG.nextInt(3) + 2; //Initiative
                values[13] = RNG.nextInt((int)Math.ceil(level/2) + 10); //Int
                values[8] = RNG.nextInt((int)Math.ceil(level/4) + 6); //HP
                values[14] = RNG.nextInt((int)Math.ceil(level/8) + 1); //Cha
                break;
            case UNIQUE:
                values[0] = RNG.nextInt(level + 15); //Accuracy
                values[4] = RNG.nextInt(5) + 1; //Initiative
                values[13] = RNG.nextInt(level + 10); //Int
                values[8] = RNG.nextInt((int)Math.ceil(level/4) + 6); //HP
                values[14] = RNG.nextInt((int)Math.ceil(level/2) + 10); //Cha
        }
        
        return values;
    }
}
