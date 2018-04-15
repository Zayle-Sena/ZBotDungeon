package classes.Misc;

import java.util.ArrayList;
import java.util.Collections;

public class NoteList implements java.io.Serializable {
    public ArrayList<Note> StoredNotes = new ArrayList();

    public void sortNotes() {
        ArrayList<Note> NoteArray = StoredNotes;
        Collections.sort(NoteArray, (Note item, Note t1) -> {
            String s1 = item.noteName;
            String s2 = t1.noteName;
            return s1.compareToIgnoreCase(s2);
        });
    }
}
