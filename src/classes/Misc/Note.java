package classes.Misc;

/**
 * @author PC
 */
public class Note implements java.io.Serializable {
    public String noteName;
    public String noteDesc;

    public String getInfo() {
        String result = "";
        result += "########## " + noteName + " ##########\n\n";
        result += noteDesc;
        return result;
    }
}
