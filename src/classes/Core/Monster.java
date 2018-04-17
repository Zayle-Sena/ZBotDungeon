package classes.Core;

import java.util.ArrayList;
import java.util.Random;


public class Monster implements java.io.Serializable {
    public Random RNG = new Random();

    public String name = "Not Defined";
    public String desc = "Not Defined";

    public int hp = 50;
    private int points = 0;

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

    public int minDamage = 5;
    public int maxDamage = 10;

    public int getDamage() {
        return RNG.nextInt(maxDamage - minDamage) + minDamage + 1;
    }

    public DamageTypes damageType = DamageTypes.PHYSICAL;

    private int accuracy = 75;

    public int getAccuracy() {
        return accuracy;
    }

    private int evasion = 0;

    public int getEvasion() {
        return evasion;
    }

    private int armour = 0;

    public int getArmour() {
        return armour;
    }

    private int initiative = 20;

    public int getInitiative() {
        return initiative;
    }

    int StrSave = 0;
    int DexSave = 0;
    int ConSave = 0;
    int WillSave = 0;

    public ArrayList<Aura> auras = new ArrayList();

    public String encounter(Monster target) {
        String result = "";

        for (Aura a : auras) {//Run the effect of each Aura and add to result text
            result += a.affect(target);
        }
        //Return what happened
        return result;
    }

    public String attack(Monster target) {
        String result = "";
        int attackRoll = RNG.nextInt(100) + 1;

        if (attackRoll < getAccuracy() - target.getEvasion()) { //If hit

            target.hp -= getDamage() - target.getArmour(); //Deal damage, add to result text
            result = name + " hit " + target.name + " for " + (getDamage() - target.getArmour()) + " damage!";
            result += "\n" + onAttack(target);

            if (target.hp <= 0) { //If the target died, add to result text, run target's onDeath
                target.hp = 0;
                result += "\n" + name + " has slain " + target.name + "!";
                result += "\n" + target.onDeath(this);
                result += "\n" + onKill(target);
            }

        } else { //Else if missed
            result = name + " attacked " + target.name + " but missed!";
        }
        //Return what happened
        return result;
    }

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
