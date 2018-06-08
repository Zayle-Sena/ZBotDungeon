package classes.Core;

import static classes.Core.Item.ItemRarity.*;
import static classes.Core.Item.ItemType.*;
import static classes.Core.Item.UseType.*;
import java.util.Random;

/**
 * @author PC
 */
public class Item implements java.io.Serializable, Lootable {
    
    private String itemName = "Generic Item";
    public String getName(){
        return itemName;
    }
    public void setName(String name){
        itemName = name;
    }
    
    String itemDesc = "Generic Description";
    
    Random RNG = new Random();
    
    boolean equipped = false;
    boolean breakable = true;
    

    ItemRarity itemRarity = JUNK;
    ItemType itemType = ItemType.MISC;
    UseType useType = UseType.NONE;

    int pointValue = 0;
    boolean autoConvert = false;
    
    int itemLevel = 1;

    int itemAccBonus = 0;
    int itemEvaBonus = 0;
    int itemMaxDamageBonus = 0;
    int itemMinDamageBonus = 0;
    int itemInitBonus = 0;
    int itemArmourBonus = 0;
    int itemPerceptionBonus = 0;
    int itemSpellBonus = 0;
    int itemHpBonus = 0;
    int itemWisBonus = 0;
    int itemDexBonus = 0;
    int itemStrBonus = 0;
    int itemConBonus = 0;
    int itemIntBonus = 0;
    int itemChaBonus = 0;
    
    public String getStats(){
        String result = "";
        
        result += "############ " + getName() + " ############"
                + "\n\n" + this.itemDesc
                + "\n\nItem Level: " + itemLevel + "\n"
                + "\nPoint value: " + pointValue;
        
        if (itemAccBonus != 0) {
            result += "\nAccuracy Bonus: " + itemAccBonus;
        }if (itemEvaBonus != 0) {
            result += "\nEvasion Bonus: " + itemEvaBonus;
        }if (itemMinDamageBonus != 0) {
            result += "\nDamage Bonus: " + itemMinDamageBonus + " to " + itemMaxDamageBonus;
        }if (itemInitBonus != 0) {
            result += "\nInitiative Bonus: " + itemInitBonus;
        }if (itemArmourBonus != 0) {
            result += "\nArmour: " + itemArmourBonus;
        }if (itemPerceptionBonus != 0) {
            result += "\nPerception Bonus: " + itemPerceptionBonus;
        }if (itemSpellBonus != 0) {
            result += "\nSpellBonus: " + itemSpellBonus;
        }if (itemHpBonus != 0) {
            result += "\nHP Bonus: " + itemHpBonus;
        }if (itemStrBonus != 0) {
            result += "\nStrength Bonus: " + itemStrBonus;
        }if (itemDexBonus != 0) {
            result += "\nDexterity Bonus: " + itemDexBonus;
        }if (itemConBonus != 0) {
            result += "\nConstitution Bonus: " + itemConBonus;
        }if (itemIntBonus != 0) {
            result += "\nIntelligenceBonus: " + itemIntBonus;
        }if (itemWisBonus != 0) {
            result += "\nWisdom Bonus: " + itemWisBonus;
        }if (itemChaBonus != 0) {
            result += "\nCharisma Bonus: " + itemChaBonus;
        }
        
        
        return result;
    }
    
    public Item(){
        float rarityRoll = RNG.nextFloat() * 100;
        
        if (rarityRoll < UNIQUE.getChance()){
            itemRarity = UNIQUE;
            
        } else if (rarityRoll < LEGENDARY.getChance()) {
            itemRarity = LEGENDARY;
        } else if (rarityRoll < EPIC.getChance()) {
            itemRarity = EPIC;
        } else if (rarityRoll < RARE.getChance()) {
            itemRarity = RARE;
        } else if (rarityRoll < SCARCE.getChance()) {
            itemRarity = SCARCE;
        } else if (rarityRoll < UNCOMMON.getChance()) {
            itemRarity = UNCOMMON;
        } else if (rarityRoll < COMMON.getChance()) {
            itemRarity = COMMON;
        } else {
            itemRarity = JUNK;
        }
        
        int typeRoll = RNG.nextInt(6) + 1;
        
        switch (typeRoll) {
            case 1:
                itemType = MISC;
                break;
            case 2:
                itemType = WEAPON;
                useType = EQUIP;
                break;
            case 3:
                itemType = HAT;
                useType = EQUIP;
                break;
            case 4:
                itemType = ARMOUR;
                useType = EQUIP;
                break;
            case 5:
                itemType = PANTS;
                useType = EQUIP;
                break;
            case 6:
                itemType = BOOTS;
                useType = EQUIP;
                break;
        }
    }
    
    public Item(ItemRarity rarity) {
        this.itemRarity = rarity;
        
        int typeRoll = RNG.nextInt(6) + 1;
        
        switch (typeRoll) {
            case 1:
                itemType = MISC;
                break;
            case 2:
                itemType = WEAPON;
                useType = EQUIP;
                break;
            case 3:
                itemType = HAT;
                useType = EQUIP;
                break;
            case 4:
                itemType = ARMOUR;
                useType = EQUIP;
                break;
            case 5:
                itemType = PANTS;
                useType = EQUIP;
                break;
            case 6:
                itemType = BOOTS;
                useType = EQUIP;
                break;
        }
    }
    public Item(ItemType type){
        this.itemType = type;
        
        float rarityRoll = RNG.nextFloat() * 100;
        
        if (rarityRoll < UNIQUE.getChance()){
            itemRarity = UNIQUE;
            
        } else if (rarityRoll < LEGENDARY.getChance()) {
            itemRarity = LEGENDARY;
        } else if (rarityRoll < EPIC.getChance()) {
            itemRarity = EPIC;
        } else if (rarityRoll < RARE.getChance()) {
            itemRarity = RARE;
        } else if (rarityRoll < SCARCE.getChance()) {
            itemRarity = SCARCE;
        } else if (rarityRoll < UNCOMMON.getChance()) {
            itemRarity = UNCOMMON;
        } else if (rarityRoll < COMMON.getChance()) {
            itemRarity = COMMON;
        } else {
            itemRarity = JUNK;
        }
    }
    public Item(ItemRarity rarity, ItemType type) {
        this.itemRarity = rarity;
        this.itemType = type;
    }
    
    
    public void setStats(int acc, int eva, int minDamage, int maxDamage, int init, int armour, int perception, int spell, int hp, int wis, int dex, int str, int con, int intelligence, int cha) {
            itemAccBonus = acc;
            itemEvaBonus = eva;
            itemMinDamageBonus = minDamage;
            itemMaxDamageBonus = maxDamage;
            itemInitBonus = init;
            itemArmourBonus = armour;
            itemPerceptionBonus = perception;
            itemSpellBonus = spell;
            itemHpBonus = hp;
            itemWisBonus = wis;
            itemDexBonus = dex;
            itemStrBonus = str;
            itemConBonus = con;
            itemIntBonus = intelligence;
            itemChaBonus = cha;
    }
    public void setStats(int[] stats) {
            itemAccBonus = stats[0];
            itemEvaBonus = stats[1];
            itemMinDamageBonus = stats[2];
            itemMaxDamageBonus = stats[3];
            itemInitBonus = stats[4];
            itemArmourBonus = stats[5];
            itemPerceptionBonus = stats[6];
            itemSpellBonus = stats[7];
            itemHpBonus = stats[8];
            itemWisBonus = stats[9];
            itemDexBonus = stats[10];
            itemStrBonus = stats[11];
            itemConBonus = stats[12];
            itemIntBonus = stats[13];
            itemChaBonus = stats[14];
    }

    public enum ItemRarity {

        JUNK(0), COMMON(85), UNCOMMON(20), SCARCE(10), RARE(5), EPIC(0.5), LEGENDARY(0.1), UNIQUE(0.01);

        private final double chance;

        ItemRarity(double rarity) {
            this.chance = rarity;
        }

        public double getChance() {
            return chance;
        }
    }

    public enum ItemType {
        MISC, WEAPON, HAT, ARMOUR, PANTS, BOOTS
    }

    public enum UseType {
        TARGETED, GENERIC, EQUIP, NONE
    }

    public String use() {
        if (useType != useType.GENERIC) {
            return "Nothing happens.";
        } else {
            return "That's not how you use this item!";
        }
    }

    public String useOn(Monster target) {
        if (useType != UseType.TARGETED) {
            return "Nothing happens.";
        } else {
            return "That's not how you use this item!";
        }
    }

    public String equip(Player equipper) {

        if (useType == UseType.EQUIP) {

            for (Item i : equipper.Inventory) {

                if (i.equipped & (i.itemType == this.itemType) & itemType != ItemType.MISC) {
                    return "You already have a " + i.itemType.toString() + " equipped";
                }

            }

            equipped = true;
            return itemName + " equipped (" + equipper.name + ")";

        } else {

            return itemName + " can't be equipped";

        }

    }
    public String unequip(Player player){
        this.equipped = false;
        return this.getName() + " unequipped! (" + player.name + ")";
    }

    public String loot(Player looter) {
        looter.currentRoom.roomLoot.remove(this);
        looter.Inventory.add(this);
        return itemName + " get! (" + looter.name + ")";
    }
    
    
    
    //These four methods should be overriden by items to give them special abilities.
    
    public String onDeath(Monster killer, Monster killed){return "";} //Runs when the equipper dies
    
    public String onKill(Monster killer, Monster killed, int damage){return "";} //Runs when the equipper kills something
    
    public String onAttack(Monster attacker, Monster attacked, int damage){return "";} //Runs when the equipper hits something
    
    public String onAttacked(Monster attacker, Monster attacked, int damage){return "";} //Runs when the equipper is hit by something
    
    
    //Runs when someone breaks the item !!does not determine if it's allowed to be broken or not!!
    public String onBreak(Player breaker) { 
        return breaker + " breaks a " + itemName + " into pieces that shimmer and fade into nothingness";
    } 
    


}
    
