package classes.Core.Auras;

import classes.Core.*;

/**
 *
 * @author PC
 */
public class TestAura extends Aura {
    
    public TestAura(boolean beneficial){
        name = "Test Aura";
        this.beneficial = beneficial;
    }
    
    public String affect(Monster target){
        return name + " affects <" + target.name + "> (Beneficial: " + beneficial + ")";
    }
    
}
