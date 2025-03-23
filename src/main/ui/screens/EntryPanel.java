package ui.screens;

import ui.*;
import ui.smallComponents.ComicSansButton;
import ui.smallComponents.ComicSansLabel;
import model.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

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
    private JLabel moodLabel;
    private JLabel timeLabel;
    private JLabel senseLabel;
    private JLabel perspectiveLabel;

    private JLabel editModeHeader;
    private JLabel viewHeader;

    public EntryPanel(JournalAppGUI mainApp) {
        this.mainApp = mainApp;
        setupUI();
    }

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

    public void showEntry(JournalEntry entry) {
        this.currentEntry = entry;
        updateViewPanel(); // Update the view panel with entry content
        cardLayout.show(this, "VIEW");
    }

    public void editEntry() {
        updateEditPanel(); // Pre-fill the edit panel with entry content
        cardLayout.show(this, "EDIT");
    }

    // passed on from other panel;
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

    public void setJournal(Journal journal) {
        this.currentJournal = journal;
    }

    // Save the current entry and switch to view mode
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

        contentEditArea = new JTextArea(600, 350);
        contentEditArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        contentEditArea.setLineWrap(true);
        contentEditArea.setWrapStyleWord(true);
        contentEditArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(contentEditArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        editPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        buttonPanel.setBackground(Color.WHITE);
        ComicSansButton saveButton = new ComicSansButton("Save", Font.PLAIN, 20);
        saveButton.addActionListener(e -> saveEntry());
        buttonPanel.add(saveButton, BorderLayout.EAST);
        editPanel.add(buttonPanel, BorderLayout.SOUTH);

    }

    private void createViewPanel() {
        viewPanel = new JPanel();
        viewPanel.setBackground(Color.WHITE);
        viewPanel.setOpaque(true);

        JButton backButton = new JButton("< back to journal");
        backButton.setFont(new Font("Comic Sans Ms", Font.PLAIN, 15));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setForeground(Color.GRAY);
        backButton.addActionListener(e -> mainApp.showJournal(currentJournal));
        viewPanel.add(backButton);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.RED);
        headerPanel.setOpaque(true);

        viewHeader = new JLabel(currentEntry == null ? "placeHolder" : currentEntry.getCreatedTime().toString());
        viewHeader.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        headerPanel.add(viewHeader, BorderLayout.WEST);

        ComicSansButton editButton = new ComicSansButton("Edit", Font.BOLD, 20);
        editButton.addActionListener(e -> editEntry());
        headerPanel.add(editButton, BorderLayout.EAST);

        viewPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setOpaque(true);

        contentDisplay = new JTextArea(currentEntry == null ? "placeHolder" : currentEntry.getContent());
        contentDisplay.setEditable(false);
        contentDisplay.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        contentDisplay.setLineWrap(true);
        contentDisplay.setWrapStyleWord(true);
        contentDisplay.setOpaque(false); // make it blend with background
        contentDisplay.setFocusable(false); // no focus outline
        contentDisplay.setBorder(null); // no border

        JScrollPane scrollPane = new JScrollPane(contentDisplay);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        viewPanel.add(contentPanel, BorderLayout.CENTER);

        ImageIcon cloudIcon = new ImageIcon("src/main/ui/screens/test.png");
        JLabel backgroundLabel = new JLabel(cloudIcon);
        backgroundLabel.setLayout(new BoxLayout(backgroundLabel, BoxLayout.Y_AXIS)); // allow stacking text
        backgroundLabel.setPreferredSize(new Dimension(cloudIcon.getIconWidth(), cloudIcon.getIconHeight()));

        moodLabel = new ComicSansLabel("Mood: " +
                (currentEntry == null ? "placeHolder" : currentEntry.getOverallMood()),
                Font.PLAIN, 15);
        timeLabel = new ComicSansLabel("Time orientation: " +
                (currentEntry == null ? "placeHolder" : currentEntry.getTimeOrientation()),
                Font.PLAIN, 15);
        senseLabel = new ComicSansLabel("Primary Sense: " +
                (currentEntry == null ? "placeHolder" : currentEntry.getPrimarySense()),
                Font.PLAIN, 15);
        perspectiveLabel = new ComicSansLabel("Perspective: " +
                (currentEntry == null ? "placeHolder" : currentEntry.getUsAndThem()),
                Font.PLAIN, 15);

        backgroundLabel.add(moodLabel);
        backgroundLabel.add(timeLabel);
        backgroundLabel.add(senseLabel);
        backgroundLabel.add(perspectiveLabel);

        contentPanel.add(backgroundLabel, BorderLayout.SOUTH);
    }

    // Update the view panel with current entry data
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

    // Update the edit panel with current entry data
    private void updateEditPanel() {
        if (!isNewEntry && currentEntry != null) {
            editModeHeader.setText("Edit journal entry");
            contentEditArea.setText(currentEntry.getContent());
        } else {
            editModeHeader.setText("Create new entry");
            contentEditArea.setText("");
        }
    }

}
