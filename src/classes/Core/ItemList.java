/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.Core;

import classes.Items.*;
import java.util.Random;

/**
 *
 * @author Zayle
 */
public class ItemList extends java.util.ArrayList<Item>{
    
    private Random RNG = new Random();
    
    public ItemList(){
        //Add each Pregen item to this object here.
        this.add(new PotionCLW());
    }
    
    
    
    
    
    
    public Item getRandom() {
        if (this.size() > 0) {
            return this.get(RNG.nextInt(this.size()));
        }
        System.out.println("List not populated, returning null.");
        return null;
    }
}
