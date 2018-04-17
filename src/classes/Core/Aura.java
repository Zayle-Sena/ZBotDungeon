package classes.Core;

/**
 * @author PC
 */
public abstract class Aura {
    
    public boolean beneficial = true;
    public String name = "Unnamed Aura";
    
    public abstract String affect(Monster target);
}
