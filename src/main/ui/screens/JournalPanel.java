package ui.screens;

import ui.*;
import ui.smallComponents.ComicSansButton;
import model.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.datatransfer.FlavorListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class JournalPanel extends JPanel {
    private JournalAppGUI mainApp;
    private Journal currentJournal;
    private JLabel titleLabel;

    private CardLayout cardLayout;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel emptyStatePanel;
    private JPanel entriesTablePanel;

    private JTable entriesTable;
    private JButton actionsButton; // we only use it here once, otherwise, I would make it into a class
    private JPopupMenu actionsMenu;

    public JournalPanel(JournalAppGUI mainApp) {
        this.mainApp = mainApp;
        setupUI();
    }

    public void setupUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setOpaque(true);

        headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        createEmptyStatePanel();
        createEntriesTablePanel();

        contentPanel.add(emptyStatePanel, "EMPTY");
        contentPanel.add(entriesTablePanel, "TABLE");

        add(contentPanel, BorderLayout.CENTER);
    }

    public void updateView() {
        if (currentJournal == null || currentJournal.getAllEntries().isEmpty()) {
            cardLayout.show(getContentPanel(), "EMPTY");
        } else {
            cardLayout.show(getContentPanel(), "TABLE");
        }
    }

    private JPanel createHeaderPanel() {
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.PINK);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // TODO: this is hardcoded right now!! Need to put the actual data value in
        // here.
        titleLabel = new JLabel("placeHolderText");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // TODO: need to update the icon here the downward rectangle....
        actionsButton = new ComicSansButton("Actions ▼", Font.BOLD, 24);
        headerPanel.add(actionsButton, BorderLayout.EAST);

        // TODO: need to add icon here... but let's see if this one works first.
        // TODO: need to check if we can make the drop down width match the button width
        // :D
        // TODO: also update the font
        actionsMenu = new JPopupMenu();
        JMenuItem createItem = new JMenuItem("Create");
        JMenuItem deleteItem = new JMenuItem("Delete");

        JSeparator separator = new JSeparator();

        JMenuItem save = new JMenuItem("Save");
        JMenuItem quit = new JMenuItem("Quit");

        actionsMenu.add(createItem);
        actionsMenu.add(deleteItem);
        actionsMenu.add(separator);
        actionsMenu.add(save);
        actionsMenu.add(quit);

        createItem.addActionListener(e -> mainApp.showEntry(new JournalEntry(""), true));
        // TODO: add other event listeners

        // show menu when actions button is clicked?
        actionsButton.addActionListener(e -> {
            actionsMenu.show(actionsButton, 0, actionsButton.getHeight());
        });
        return headerPanel;
    }

    public void updateHeader() {
        if (currentJournal != null) {
            titleLabel.setText(currentJournal.getName());
        }
    }

    private void createEmptyStatePanel() {
        emptyStatePanel = new JPanel();
        emptyStatePanel.setLayout(new BoxLayout(emptyStatePanel, BoxLayout.Y_AXIS));
        emptyStatePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        emptyStatePanel.setBackground(Color.WHITE);

        // Add vertical space at the top
        emptyStatePanel.add(Box.createVerticalGlue());

        // Image
        ImageIcon rawEmptyImage = new ImageIcon("src/main/ui/screens/empty-journal.png");
        Image scaledImage = rawEmptyImage.getImage().getScaledInstance((int) (872 / 1.5), (int) (744 / 1.5),
                Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel emptyImage = new JLabel(resizedIcon);
        emptyImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyStatePanel.add(emptyImage);

        // Text
        JLabel emptyText = new JLabel(
                "No journal entries to show.... click Actions button on the top right to create a journal entry");
        emptyText.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        emptyText.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyStatePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        emptyStatePanel.add(emptyText);

        // Add vertical space at the bottom
        emptyStatePanel.add(Box.createVerticalGlue());
    }

    private void createEntriesTablePanel() {
        entriesTablePanel = new JPanel(new BorderLayout());
        entriesTablePanel.setBackground(Color.WHITE);

        entriesTable = new JTable(new JournalEntriesTableModel());
        entriesTable.setRowHeight(40);
        entriesTable.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        entriesTable.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 18));

        // add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(entriesTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        entriesTablePanel.add(scrollPane, BorderLayout.CENTER);

        if (currentJournal != null) {
            ((JournalEntriesTableModel) entriesTable.getModel()).refreshData();
        }

        // add listener for double-clicks on entries
        entriesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = entriesTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        JournalEntriesTableModel model = (JournalEntriesTableModel) entriesTable.getModel();
                        JournalEntry entry = model.entries.get(row);
                        System.out.print("entry" + entry.getContent());
                        mainApp.showEntry(entry, false);
                    }
                }
            }
        });
    }

    // Using private class here as we don't need this anywhere else
    private class JournalEntriesTableModel extends AbstractTableModel {
        private String[] columnNames = { "Created date", "Preview", "Last Updated" };
        private List<JournalEntry> entries = new ArrayList<>();

        public void refreshData() {
            if (currentJournal != null) {
                entries = new ArrayList<>(currentJournal.getAllEntries().values());
                System.out.println("Table model refreshed. Entry count: " + entries.size());
                // Thread.dumpStack();
            } else {
                entries.clear();
                System.out.println("Table model refreshed with NULL journal");
                // Thread.dumpStack();
            }
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return (currentJournal == null) ? 0 : entries.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (currentJournal == null || rowIndex >= entries.size()) {
                return null;
            }
            JournalEntry entry = entries.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return entry.getCreatedTime().toString();
                case 1:
                    return entry.getEntryPreview();
                case 2:
                    return entry.getLastUpdatedTime().toString();
                default:
                    return null;
            }
        }
    }

    public void setJournal(Journal journal) {
        this.currentJournal = journal;
        if (entriesTable != null) {
            JournalEntriesTableModel model = (JournalEntriesTableModel) entriesTable.getModel();
            model.refreshData();
        }
        updateHeader();
        updateView();
    }

    private JPanel getContentPanel() {
        return contentPanel;
    }

}
