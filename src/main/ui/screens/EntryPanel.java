package ui.screens;
import ui.*;
import model.*;

import javax.swing.JPanel;

public class EntryPanel extends JPanel{

    private JournalEntry currentEntry;
    
    public EntryPanel(JournalAppGUI journalAppGui){

    }

    public void setEntry(JournalEntry entry){
        this.currentEntry = entry;
    }
}
