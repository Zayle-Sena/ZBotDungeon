package classes.Core;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * @author PC
 */
public class Player extends Monster implements java.io.Serializable {
    ArrayList<Item> Inventory = new ArrayList();
    
    public String playerID = "";
    
    public Room currentRoom = null;

    String playerClass = "None"; //This can wait
    boolean isDead = false;

    private int strength = 10; //Modifies damage done, and Saves
    public int getStrength(){
        int total = strength;
        
        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemStrBonus;
            }
        }
        return total;
    }
    
    private int dexterity = 10; //Modifies some skills, and Saves
    public int getDexterity(){
        int total = dexterity;
        
        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemDexBonus;
            }
        }
        return total;
    }
    
    private int constitution = 10; //Modifies HP limit, Armour, and Saves
    public int getConstitution(){
        int total = constitution;
        
        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemConBonus;
            }
        }
        return total;
    }
    
    private int intelligence = 10; //Modifies spells and some skills
    public int getIntelligence(){
        int total = intelligence;
        
        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemIntBonus;
            }
        }
        return total;
    }
    
    private int wisdom = 10; //Modifies perception, and Saves
    public int getWisdom(){
        int total = wisdom;
        
        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemWisBonus;
            }
        }
        return total;
    }
    
    private int chasrisma = 10; //Makes you more likely to be attacked, lel
    public int getChasrisma(){
        int total = chasrisma;
        
        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemChaBonus;
            }
        }
        return total;
    }
    
    Player(String name, String id){
        this.name = name;
        this.playerID = id;
        
        strength = RNG.nextInt(12) + 7;
        dexterity = RNG.nextInt(12) + 7;
        constitution = RNG.nextInt(12) + 7;
        intelligence = RNG.nextInt(12) + 7;
        wisdom = RNG.nextInt(12) + 7;
        chasrisma = RNG.nextInt(12) + 7;
    }

    @Override
    public int getInitiative() {
        int total = super.getInitiative() + ((getDexterity() - 10) * 2);
        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemInitBonus;
            }
        }
        return total;
    }
    
    @Override
    public int getMinDamage(){
        int bonusMin = 0;
        for (Item i : Inventory) {
            if (i.equipped) {
                bonusMin += i.itemMinDamageBonus;
            }
        }
        return super.getMinDamage() + bonusMin;
    }
    
    @Override
    public int getMaxDamage(){
        int bonusMax = 0;
        for (Item i : Inventory) {
            if (i.equipped) {
                bonusMax += i.itemMaxDamageBonus;
            }
        }
        return super.getMaxDamage() + bonusMax;
    }

    @Override
    public int getDamage() {
        int total = RNG.nextInt(getMaxDamage() - getMinDamage() + 1) + getMinDamage();
        
        total += Math.round((getStrength() - 10) / 2);
        
        return total;
    }

    @Override
    public int getPoints() {
        int total = super.getPoints();
        for (Item i : Inventory) {
            total += i.pointValue;
        }
        return total;
    }

    @Override
    public int getEvasion() {
        int total = super.getEvasion();

        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemEvaBonus;
            }
        }

        total += ((getDexterity() - 10) * 2);
        return total;
    }

    @Override
    public int getAccuracy() {
        int total = super.getAccuracy();

        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemAccBonus;
            }
        }

        total += ((getDexterity() - 10) + (getStrength() - 10)) * 2;
        return total;
    }

    @Override
    public int getArmour() {
        int total = super.getArmour();

        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemArmourBonus;
            }
        }

        total += Math.floor((getConstitution() - 10) / 2);

        return total;
    }

    public int getPerception() {
        int total = 0;

        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemPerceptionBonus;
            }
        }

        total += Math.floor((getWisdom() - 10) / 2);
        return total;
    }

    public int getSpellBonus() {
        int total = 0;

        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemSpellBonus;
            }
        }

        total += (getIntelligence() - 10) * 2;
        return total;
    }

    public int getMaxHp() {
        int total = maxHp;

        for (Item i : Inventory) {
            if (i.equipped) {
                total += i.itemHpBonus;
            }
        }

        total += (getConstitution() - 10) * 2;
        return total;
    }

    public int getSave(char save) {
        int total = 0;
        switch (save) {
            case 'S':
                total += Math.floor((getStrength() - 10) / 2);
                break;
            case 'D':
                total += Math.floor((getDexterity() - 10) / 2);
                break;
            case 'C':
                total += Math.floor((getConstitution() - 10) / 2);
                break;
            case 'W':
                total += Math.floor((getWisdom() - 10) / 2);
                break;
            case 'I':
                total += Math.floor((getIntelligence() - 10) / 2);
                break;
            case 'M':
                total += Math.floor((getChasrisma() - 10) / 2);
                break;
        }
        return total;
    }
    
    public String onAttack(Monster attacked, int damage) {
        String result = "";
        
        for (Item i : Inventory) {
            result += "\n" + i.onAttack(this, attacked, damage);
        }

        return result;
    }

    public String onAttacked(Monster attacked, int damage) {
        String result = "";
        
        for (Item i : Inventory) {
            result += "\n" + i.onAttacked(this, attacked, damage);
        }

        return result;
    }

    public String onDeath(Monster killer) {
        String result = "";
        
        setPoints(0);
        isDead = true;
        
        for (Item i : Inventory) {
            result += "\n" + i.onDeath(killer, this);
        }

        return result;
    }

    public String onKill(Monster killed, int damage) {
        String result = "";
        
        for (Item i : Inventory) {
            result += "\n" + i.onKill(this, killed, damage);
        }

        result += "\n\n" + name + " takes " + killed.name + "'s points! (" + killed.getPoints() + ")";
        addPoints(killed.getPoints());

        return result;
    }
    
    public String getStatBlock(){
        String result = "";
        
        NumberFormat number = new DecimalFormat("+#;-#");
        
        result += "Name: " + name + " (" + getPoints() + " points)";
        result += "\nHP: " + hp + "/" + getMaxHp();
        result += "\n\nStrength: " + getStrength() + " (" + number.format(getSave('S')) + ")";
        result += "\nDexterity: " + getDexterity() + " (" + number.format(getSave('D')) + ")";
        result += "\nConstitution: " + getConstitution() + " (" + number.format(getSave('C')) + ")";
        result += "\nIntelligence: " + getIntelligence() + " (" + number.format(getSave('I')) + ")";
        result += "\nWisdom: " + getWisdom() + " (" + number.format(getSave('W')) + ")";
        result += "\nCharisma: " + getChasrisma() + " (" + number.format(getSave('M')) + ")";
        
        result += "\n\nInitiative: " + getInitiative();
        result += "\nArmour: " + getArmour();
        result += "\nEvasion: " + getEvasion();
        result += "\nAccuracy: " + getAccuracy();
        
        result += "\n\nDamage: " + getMinDamage() + " to " + getMaxDamage();
        result += "\nSpell bonus: " + getSpellBonus();
        
        
        return result;
    }
}
