package ui;

import model.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.screens.BookshelfPanel;
import ui.screens.EntryPanel;
import ui.screens.JournalPanel;

import java.awt.*;

/**
 * JournalAppGUI class is the main GUI class for the journal app. 
 */

public class JournalAppGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private BookshelfPanel bookshelfPanel;
    private JournalPanel journalPanel;
    private EntryPanel entryPanel;

    private Journal currentJournal;
    private JournalEntry currentEntry;

    public JournalAppGUI() {
        setTitle("Simple journal app"); // we can get creative here later
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        bookshelfPanel = new BookshelfPanel(this);
        journalPanel = new JournalPanel(this);
        entryPanel = new EntryPanel(this);

        mainPanel.add(bookshelfPanel, "BOOKSHELF");
        mainPanel.add(journalPanel, "JOURNAL");
        mainPanel.add(entryPanel, "ENTRY");

        this.add(mainPanel);
        showBookshelf();

    }

    // Effects: shows the bookshelf panel
    public void showBookshelf() {
        cardLayout.show(mainPanel, "BOOKSHELF");
    }
    
    // Effects: shows the journal panel and sets the current journal
    // Modifies: this
    public void showJournal(Journal journal) {
        this.currentJournal = journal;
        journalPanel.setJournal(journal);
        cardLayout.show(mainPanel, "JOURNAL");
    }

    // Effects: shows the entry panel and sets the current entry
    // Modifies: this
    public void showEntry(JournalEntry entry, Boolean isNewEntry) {
        entryPanel.setEntry(entry, isNewEntry);
        entryPanel.setJournal(this.currentJournal);
        cardLayout.show(mainPanel, "ENTRY");
    }

}
