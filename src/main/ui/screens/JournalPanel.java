package ui.screens;

import ui.*;
import model.*;

import javax.swing.JPanel;

public class JournalPanel extends JPanel {

    private Journal currentJournal;

    public JournalPanel(JournalAppGUI journalAppGui) {

    }

    public void setJournal(Journal journal) {
        this.currentJournal = journal;
    }

}
