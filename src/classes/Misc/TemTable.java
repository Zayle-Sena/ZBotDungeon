package classes.Misc;

import java.awt.Color;
import java.util.Random;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

/**
 *
 * @author PC
 */
public class TemTable {
    EmbedBuilder builder = new EmbedBuilder();
    Random RNG = new Random();
    
    public TemTable(){
    }
    
    public MessageEmbed getOne() {
        builder = new EmbedBuilder();
        float colour[] = {0,0,0};
        
        switch (RNG.nextInt(10)) {
            case 0:
                builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Acid_Splash");
                builder.addField("Acid Splash", "You hurl a bubble of acid."
                        + "\nChoose up to two creatures within range that are within 5 feet of each other."
                        + "\nA target takes 1d6 Acid damage on a failed Dex save.", true);
                builder.addField("At Higher Levels", "The damage increases by 1d6 at levels 5, 11, and 17.", true);
                colour = Color.RGBtoHSB(0, 150, 75, colour);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
                
        }
        
        builder.setColor(Color.getHSBColor(colour[0], colour[1], colour[2]));
        return builder.build();
    }
    
}
