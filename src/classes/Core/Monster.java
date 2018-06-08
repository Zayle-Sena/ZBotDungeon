package classes.Core;

import java.util.Random;


public class Monster implements java.io.Serializable {
    public Random RNG = new Random();

    public String name = "Not Defined";
    public String desc = "Not Defined";

    public int hp = 10;
    public int maxHp = 10;
    private int points = 0;

    //Overwritten in Player.java!
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void losePoints(int points) {
        this.points -= points;
    }

    private int minDamage = 1;
    //Overwritten in Player.java!
    public int getMinDamage(){
        return minDamage;
    }
    
    private int maxDamage = 1;
    //Overwritten in Player.java!
    public int getMaxDamage(){
        return maxDamage;
    }

    //Overwritten in Player.java!
    public int getDamage() {
        return RNG.nextInt(getMaxDamage() - getMinDamage() + 1) + minDamage;
    }

    public DamageTypes damageType = DamageTypes.PHYSICAL;

    private int accuracy = 75;

    //Overwritten in Player.java!
    public int getAccuracy() {
        return accuracy;
    }

    private int evasion = 0;

    //Overwritten in Player.java!
    public int getEvasion() {
        return evasion;
    }

    private int armour = 0;

    //Overwritten in Player.java!
    public int getArmour() {
        return armour;
    }

    private int initiative = 20;

    //Overwritten in Player.java!
    public int getInitiative() {
        return initiative;
    }

    //Players do not use these save values. To get a player's save use the .getSave(char) method in Player.java
    int StrSave = 0;
    int DexSave = 0;
    int ConSave = 0;
    int WillSave = 0;

    //Should be overridden for monsters with unique encounter effects
    public String encounter(Monster target) {
        String result = "";
        return result;
    }

    public String attack(Monster target) {
        String result = "";
        int attackRoll = RNG.nextInt(100) + 1;

        if (attackRoll < getAccuracy() - target.getEvasion()) { //If hit
            int modifiedDamage = getDamage() - RNG.nextInt(target.getArmour() + 1);
            
            if (modifiedDamage < 0){
                modifiedDamage = 0;
            }
            
            target.hp -= modifiedDamage; //Deal damage, add to result text
            result = name + " hit " + target.name + " for " + (modifiedDamage) + " damage!";
            result += "\n" + onAttack(target);

            if (target.hp <= 0) { //If the target died, add to result text, run target's onDeath
                target.hp = 0;
                result += "\n" + name + " has slain " + target.name + "!";
                result += "\n" + target.onDeath(this);
                result += "\n" + onKill(target);
            }

        } else { //Else if missed
            result = name + " missed " + target.name + " with an attack!";
        }
        //Return what happened
        return result;
    }
    
    
    //!!!! The four methods below are overriden in Player.java to take the player's inventory into account !!!!
    public String onAttack(Monster target) {
        String result = "";

        return result;
    }

    public String onAttacked(Monster attacker) {
        String result = "";

        return result;
    }

    public String onDeath(Monster killer) {
        String result = "";

        points = 0;
        return result;
    }

    public String onKill(Monster killed) {
        String result = "";

        result = name + " takes " + killed.name + "'s points! (" + killed.getPoints() + ")";
        points += killed.getPoints();

        return result;
    }
}
