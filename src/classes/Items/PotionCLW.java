package classes.Items;

import static classes.Core.Item.ItemRarity.*;
import static classes.Core.Item.ItemType.*;
import static classes.Core.Item.UseType.*;
import classes.Core.Monster;

/**
 *
 * @author Zayle
 */
public class PotionCLW extends classes.Core.Item{
    
    public PotionCLW() {
        this.setName("Healing Potion");
        this.setDesc("A slightly red vial of liquid. Probably a healing potion.");
        this.setGeneratorWeight(1000);
        this.itemRarity = UNCOMMON;
        this.itemType = MISC;
        this.useType = TARGETED;
        this.itemLevel = 0;
    }
    
    @Override
    public String use(Monster user) {
        if (useType == UseType.GENERIC) {
            return "Nothing happens.";
        } else if (useType == TARGETED){
            if (user.hp + Math.ceil(user.maxHp/4) > user.maxHp) {
                user.hp = user.maxHp;
            } else {
                user.hp += Math.ceil(user.maxHp/4);
            }
            return user.name + "drank a potion and regained " + Math.ceil(user.maxHp/4) + " HP!";
        } else {
            return "That's not how you use this item!";
        }
    }

    @Override
    public String useOn(Monster user, Monster target) {
        if (useType == UseType.TARGETED) {
            if (target.hp + Math.ceil(user.maxHp/4) > target.maxHp) {
                target.hp = target.maxHp;
            } else {
                target.hp += Math.ceil(target.maxHp/4);
            }
            return user.name + " force-fed " + target.name + " a potion, giving them " + Math.ceil(target.maxHp/4) + "HP!";
        } else {
            return "That's not how you use this item!";
        }
    }
    
}
