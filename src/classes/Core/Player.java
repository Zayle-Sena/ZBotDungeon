package classes.Core;

import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class Player extends Monster implements java.io.Serializable{
    ArrayList<Item> Inventory = new ArrayList();
    
    String playerClass = "None"; //This can wait
    
    int Strength = 10; //Modifies damage done, and Saves
    int Dexterity = 10; //Modifies some skills, and Saves
    int Constitution = 10; //Modifies HP limit, Armour, and Saves
    int Intelligence = 10; //Modifies spells and some skills
    int Wisdom = 10; //Modifies perception, and Saves
    int Chasrisma = 10; //Makes you more likely to be attacked, lel
        
    @Override
    public int getInitiative(){
        int total = super.getInitiative() + ((Dexterity - 10) * 2);
        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemInitBonus;
            }
        }
        return total;
    }
    
    @Override
    public int getDamage(){
        int total = RNG.nextInt(maxDamage - minDamage) + minDamage + 1;
        for (Item i : Inventory) {
            if (i.equipped){
                total += i.itemDamBonus;
            }
        }
        total += (Strength - 10) * 2;
        return total;
    }
    
    @Override
    public int getPoints() {
        int total  = super.getPoints();
        for (Item i : Inventory) {
            total += i.pointValue;
        }
        return total;
    }
    
    @Override
    public int getEvasion(){
        int total = super.getEvasion();
        
        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemEvaBonus;
            }
        }
        
        total += ((Dexterity - 10) * 2);
        return total;
    }
    
    @Override
    public int getAccuracy(){
        int total = super.getAccuracy();
        
        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemAccBonus;
            }
        }
        
        total += ((Dexterity - 10) + (Strength - 10)) * 2;
        return total;
    }
    
    @Override
    public int getArmour(){
        int total = super.getArmour();
        
        for (Item i : Inventory){
            if (i.equipped) {
                total +=  i.itemArmourBonus;
            }
        }
        
        total += Math.floor((Constitution - 10)/2);
        
        return total;
    }
    
    public int getPerception(){
        int total = 0;
        
        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemPerBonus;
            }
        }
        
        total += Math.floor((Wisdom - 10)/2);
        return total;
    }
    
    public int spellBonus(){
        int total = 0;
        
        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemSpellBonus;
            }
        }
        
        total += (Intelligence - 10) * 2;
        return total;
    }
    
    public int maxHp(){
        int total = hp;
        
        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemHpBonus;
            }
        }
        
        total += (Constitution - 10) * 10;
        return total;
    }
    
    public int getSave(char save) {
        int total = 0;
        switch (save){
            case 'S':
                total += StrSave;
                total += Math.floor((Strength - 10)/2);
                for (Item i : Inventory) {
                    if (i.equipped) {
                        total += i.itemStrSaveBonus;
                    }
                }
                break;
            case 'D':
                total += DexSave;
                total += Math.floor((Dexterity - 10)/2);
                for (Item i : Inventory) {
                    if (i.equipped) {
                        total += i.itemDexSaveBonus;
                    }
                }
                break;
            case 'C':
                total += ConSave;
                total += Math.floor((Constitution - 10)/2);
                for (Item i : Inventory) {
                    if (i.equipped) {
                        total += i.itemConSaveBonus;
                    }
                }
                break;
            case 'W':
                total += WillSave;
                total += Math.floor((Wisdom - 10)/2);
                for (Item i : Inventory) {
                    if (i.equipped) {
                        total += i.itemWillSaveBonus;
                    }
                }
                break;
        }
        return total;
    }
}
