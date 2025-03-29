package ui.screens;

import ui.*;
import ui.components.ComicSansButton;
import ui.components.ComicSansLabel;
import model.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class represents the panel that displays the journal entry, and it
 * contains two modes: view mode and edit mode.
 * In view mode, the user can see the content of the journal entry and some
 * insights about it.
 * In edit mode, the user can edit the content of the journal entry.
 */

public class EntryPanel extends JPanel {
    private JournalAppGUI mainApp;
    private Journal currentJournal;
    private JournalEntry currentEntry;
    private Boolean isNewEntry;

    private CardLayout cardLayout;
    private JPanel editPanel;
    private JPanel viewPanel;

    private JTextArea contentEditArea;
    private JTextArea contentDisplay;
    private ComicSansLabel moodLabel;
    private ComicSansLabel timeLabel;
    private ComicSansLabel senseLabel;
    private ComicSansLabel perspectiveLabel;

    private JLabel editModeHeader;
    private JLabel viewHeader;
    private ComicSansButton cancelEditButton;

    public EntryPanel(JournalAppGUI mainApp) {
        this.mainApp = mainApp;
        setupUI();
    }

    // EFFECTS: Create the UI for the panel
    // MODIFIES: this
    public void setupUI() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Create the two panels
        createEditPanel();
        createViewPanel();

        // Add panels to the card layout
        add(editPanel, "EDIT");
        add(viewPanel, "VIEW");
    }

    // EFFECTS: Update the view panel and show the entry in view mode
    // MODIFIES: this
    public void showEntry(JournalEntry entry) {
        this.currentEntry = entry;
        updateViewPanel(); // Update the view panel with entry content
        cardLayout.show(this, "VIEW");
    }

    // EFFECTS: update the edit panel and show the entry in edit mode
    // MODIFIES: this
    public void editEntry() {
        updateEditPanel(); // Pre-fill the edit panel with entry content
        cardLayout.show(this, "EDIT");
    }

    // EFFECTS: Set the current entry and isNewEntry (passed on from other panel),
    // and decide which mode to show
    // MODIFIES: this
    public void setEntry(JournalEntry entry, Boolean isNewEntry) {
        this.isNewEntry = isNewEntry;
        this.currentEntry = entry;

        if (isNewEntry) {
            // If it's a new entry, update edit panel and show it
            updateEditPanel();
            cardLayout.show(this, "EDIT");
        } else {
            // If it's an existing entry, update view panel and show it
            updateViewPanel();
            cardLayout.show(this, "VIEW");
        }

    }

    // EFFECTS: Set the current journal
    // MODIFIES: this
    public void setJournal(Journal journal) {
        this.currentJournal = journal;
    }

    // Effects: Save the current entry and switch to view mode
    // Modifies: this
    public void saveEntry() {
        String content = contentEditArea.getText();

        if (isNewEntry) {
            // Now create the new entry with content
            JournalEntry newEntry = new JournalEntry(content);
            currentJournal.createNewEntry(newEntry);
            currentEntry = newEntry;
            isNewEntry = false;
        } else {
            // Update existing entry
            currentEntry.editContent(content);
        }

        updateViewPanel();
        cardLayout.show(this, "VIEW");
    }

    // EFFECTS: Create the edit panel
    // MODIFIES: this
    @SuppressWarnings("methodlength")
    private void createEditPanel() {
        editPanel = new JPanel();
        editPanel.setLayout(new BorderLayout());
        editPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        editPanel.setBackground(Color.WHITE);
        editPanel.setOpaque(true);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setOpaque(true);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        editModeHeader = new JLabel((isNewEntry == null || isNewEntry) ? "New journal entry" : "Edit journal entry");
        editModeHeader.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        editModeHeader.setBackground(Color.WHITE);
        editModeHeader.setOpaque(true);
        headerPanel.add(editModeHeader, BorderLayout.WEST);

        editPanel.add(headerPanel, BorderLayout.NORTH);

        contentEditArea = new JTextArea();
        contentEditArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        contentEditArea.setLineWrap(true);
        contentEditArea.setWrapStyleWord(true);
        contentEditArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(contentEditArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        editPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        buttonPanel.setBackground(Color.WHITE);
        ComicSansButton saveButton = new ComicSansButton("Save", Font.PLAIN, 20);
        saveButton.addActionListener(e -> saveEntry());

        cancelEditButton = new ComicSansButton("Cancel", Font.PLAIN, 20);
        buttonPanel.add(cancelEditButton);
        buttonPanel.add(saveButton);
        editPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void showViewPanel() {
        cardLayout.show(this, "VIEW");
    }

    // EFFECTS: Create the view panel
    // MODIFIES: this

    @SuppressWarnings("methodlength")
    private void createViewPanel() {
        viewPanel = new JPanel(new BorderLayout());
        viewPanel.setBackground(Color.WHITE);
        viewPanel.setOpaque(true);
        viewPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.setBackground(Color.WHITE);

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, -20, 0));
        backButtonPanel.setBackground(Color.WHITE);
        backButtonPanel.setOpaque(true);

        JButton backButton = new JButton("< back to journal");
        backButton.setFont(new Font("Comic Sans Ms", Font.PLAIN, 18));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setForeground(Color.GRAY);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        backButton.addActionListener(e -> mainApp.showJournal(currentJournal));
        backButtonPanel.add(backButton);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setOpaque(true);

        viewHeader = new JLabel(currentEntry == null ? "placeHolder" : currentEntry.getCreatedTime().toString());
        viewHeader.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        viewHeader.setForeground(new Color(68, 101, 233));
        headerPanel.add(viewHeader, BorderLayout.WEST);

        ComicSansButton editButton = new ComicSansButton("Edit", Font.BOLD, 20);
        editButton.addActionListener(e -> editEntry());
        headerPanel.add(editButton, BorderLayout.EAST);

        northPanel.add(backButtonPanel);
        northPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        northPanel.add(headerPanel);
        northPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        viewPanel.add(northPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.PINK);
        contentPanel.setOpaque(true);

        contentDisplay = new JTextArea(currentEntry == null ? "empty journal content" : currentEntry.getContent());
        contentDisplay.setEditable(false);
        contentDisplay.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        contentDisplay.setLineWrap(true);
        contentDisplay.setWrapStyleWord(true);
        contentDisplay.setOpaque(false); // make it blend with background
        contentDisplay.setFocusable(false); // no focus outline
        contentDisplay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(contentDisplay);
        Dimension fixedSize = new Dimension(600, 300);
        scrollPane.setPreferredSize(fixedSize);
        scrollPane.setMinimumSize(fixedSize);
        scrollPane.setMaximumSize(fixedSize);

        scrollPane.setBackground(Color.decode("#f5f7f9"));
        scrollPane.setBorder(null);
        scrollPane.setOpaque(true);
        scrollPane.getViewport().setOpaque(false);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        viewPanel.add(contentPanel, BorderLayout.CENTER);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(600, 150));

        ImageIcon backgroundImageIcon = new ImageIcon("src/main/ui/screens/insight-image.png");
        JLabel backgroundLabel = new JLabel(backgroundImageIcon);
        backgroundLabel.setBounds(50, 0, backgroundImageIcon.getIconWidth(), backgroundImageIcon.getIconHeight());

        JPanel insightPanel = new JPanel();
        insightPanel.setLayout(new BoxLayout(insightPanel, BoxLayout.Y_AXIS));
        insightPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        insightPanel.setOpaque(false);
        insightPanel.setBounds(0, 0, 600, 150);

        moodLabel = new ComicSansLabel("Mood: "
                + (currentEntry == null ? "placeHolder" : currentEntry.getOverallMood()),
                Font.PLAIN, 18);
        timeLabel = new ComicSansLabel("Time orientation: "
                + (currentEntry == null ? "placeHolder" : currentEntry.getTimeOrientation()),
                Font.PLAIN, 18);
        senseLabel = new ComicSansLabel("Primary Sense: "
                + (currentEntry == null ? "placeHolder" : currentEntry.getPrimarySense()),
                Font.PLAIN, 18);
        perspectiveLabel = new ComicSansLabel("Perspective: "
                + (currentEntry == null ? "placeHolder" : currentEntry.getUsAndThem()),
                Font.PLAIN, 18);

        insightPanel.add(moodLabel);
        insightPanel.add(timeLabel);
        insightPanel.add(senseLabel);
        insightPanel.add(perspectiveLabel);

        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(insightPanel, JLayeredPane.PALETTE_LAYER);

        viewPanel.add(layeredPane, BorderLayout.SOUTH);
    }

    // Effects: Update the view panel with current entry data
    // Modifies: this
    private void updateViewPanel() {
        if (currentEntry != null) {
            viewHeader.setText(currentEntry.getCreatedTime().toString());
            contentDisplay.setText(currentEntry.getContent());
            moodLabel.setText("Mood: " + currentEntry.getOverallMood());
            timeLabel.setText("Time orientation: " + currentEntry.getTimeOrientation());
            senseLabel.setText("Primary Sense: " + currentEntry.getPrimarySense());
            perspectiveLabel.setText("Perspective: " + currentEntry.getUsAndThem());
        }
    }

    // Effects: Update the edit panel with current entry data
    // Modifies: this
    private void updateEditPanel() {
        for (ActionListener al : cancelEditButton.getActionListeners()) {
            cancelEditButton.removeActionListener(al);
        }
        if (!isNewEntry && currentEntry != null) {
            editModeHeader.setText("Edit journal entry");
            contentEditArea.setText(currentEntry.getContent());
            cancelEditButton.addActionListener(e -> showViewPanel());
        } else {
            editModeHeader.setText("Create new entry");
            contentEditArea.setText("");
            cancelEditButton.addActionListener(e -> mainApp.showJournal(currentJournal));
        }
    }
}
