
package classes.Items;

import static classes.Core.Item.ItemRarity.*;
import static classes.Core.Item.ItemType.*;
import static classes.Core.Item.UseType.*;
import classes.Core.Monster;

/**
 * @author Zayle
 */
public class ZaylesStaff extends classes.Core.Item{

    ZaylesStaff(){
        
        this.setName("Zayle's Staff");
        this.itemSpellBonus = 666;
        this.itemWisBonus = 100;
        this.itemInitBonus = 200;
        this.itemMinDamageBonus = 2000;
        this.itemMaxDamageBonus = 2500;
        this.itemLevel = 101;
        this.itemType = ItemType.WEAPON;
        this.useType = UseType.EQUIP;
        this.setDesc("A cool looking magical staff made of a pure white wood and embellished with silver fillament."
                + "\nOne of a kind.");
        this.itemRarity = ItemRarity.UNIQUE;
        this.breakable = false;
        this.pointValue = 0;
        this.itemAccBonus = 666;
        this.itemHpBonus = 200000;
        this.itemDexBonus = 100;
        this.itemChaBonus = -50;
        
    }   
    
    @Override
    public String use(Monster user) {
        if (useType == UseType.GENERIC) {
            if (user.name.equals("Zayle")) {
                return "Zayle bathes herself in light, fully healing all her injuries.";
            } else {
                user.hp = 1;
               
                return user.name + " tries to use something that isn't theirs, and pays the price.\n"
                        + user.damage(1, user);
            }
        } else if (useType == TARGETED){
            return useOn(user, user);
        } else {
            return "That's not how you use this item!";
        }
    }

    @Override
    public String useOn(Monster user, Monster target) {
        if (useType == UseType.TARGETED) {
            return "Nothing happens.";
        } else {
            return "That's not how you use this item!";
        }
    }
    
}
