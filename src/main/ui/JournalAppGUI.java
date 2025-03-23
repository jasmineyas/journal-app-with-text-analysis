package ui;

import model.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.screens.BookshelfPanel;
import ui.screens.EntryPanel;
import ui.screens.JournalPanel;

import java.awt.*;

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

    public void showBookshelf() {
        cardLayout.show(mainPanel, "BOOKSHELF");
    }

    public void showJournal(Journal journal) {
        this.currentJournal = journal;
        journalPanel.setJournal(journal);
        cardLayout.show(mainPanel, "JOURNAL");
    }

    public void showEntry(JournalEntry entry, Boolean isNewEntry) {
        entryPanel.setEntry(entry, isNewEntry);
        entryPanel.setJournal(this.currentJournal);
        cardLayout.show(mainPanel, "ENTRY");
    }

}
