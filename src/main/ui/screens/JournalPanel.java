package ui.screens;

import ui.*;
import ui.smallComponents.ComicSansButton;
import ui.smallComponents.ConfirmationDialog;
import model.*;
import persistence.JsonWriter;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

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
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, -20, 0));
        backButtonPanel.setBackground(Color.WHITE);
        backButtonPanel.setOpaque(true);

        JButton backButton = new JButton("< back");
        backButton.setFont(new Font("Comic Sans Ms", Font.PLAIN, 18));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setForeground(Color.GRAY);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        backButton.addActionListener(e -> mainApp.showBookshelf());
        backButtonPanel.add(backButton);

        headerPanel.add(backButtonPanel, BorderLayout.NORTH);

        titleLabel = new JLabel("placeHolderText");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titleLabel.setForeground(new Color(68, 101, 233));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        actionsButton = new ComicSansButton("Actions ▼", Font.BOLD, 24);
        headerPanel.add(actionsButton, BorderLayout.EAST);

        actionsMenu = new JPopupMenu();
        Font menuFont = new Font("Comic Sans MS", Font.PLAIN, 18);
        Dimension paddedSize = new Dimension(160, 30);
        JMenuItem createItem = new JMenuItem("Create");
        JMenuItem deleteItem = new JMenuItem("Delete");
        JSeparator separator = new JSeparator();
        JMenuItem save = new JMenuItem("Save");

        createItem.setFont(menuFont);
        deleteItem.setFont(menuFont);
        save.setFont(menuFont);

        createItem.setPreferredSize(paddedSize);
        deleteItem.setPreferredSize(paddedSize);
        save.setPreferredSize(paddedSize);

        actionsMenu.add(createItem);
        actionsMenu.add(deleteItem);
        actionsMenu.add(separator);
        actionsMenu.add(save);

        createItem.addActionListener(e -> mainApp.showEntry(new JournalEntry(""), true));
        deleteItem.addActionListener(e -> deleteSelectedEntry());
        save.addActionListener(e -> saveJournal());

        actionsButton.addActionListener(e -> {
            actionsMenu
                    .setPreferredSize(new Dimension(actionsButton.getWidth(), actionsMenu.getPreferredSize().height));
            actionsMenu.show(actionsButton, 0, actionsButton.getHeight());
            ;

            actionsButton.setText("Actions ▲");
        });

        actionsMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                actionsButton.setText("Actions ▼");
            }

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                actionsButton.setText("Actions ▼");
            }
        });

        return headerPanel;
    }

    private void deleteSelectedEntry() {
        // Check if there's a selection
        int selectedRow = entriesTable.getSelectedRow();
        if (selectedRow == -1) {
            // No selection, show a message
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an entry to delete.",
                    "No selection",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Get the selected entry
        JournalEntriesTableModel model = (JournalEntriesTableModel) entriesTable.getModel();
        JournalEntry selectedEntry = model.entries.get(selectedRow);

        // Show confirmation dialog
        ConfirmationDialog dialog = new ConfirmationDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                "Confirm deletion",
                "Are you sure you want to delete this journal entry?",
                "Delete",
                "Cancel");

        boolean confirmed = dialog.showDialog();

        if (confirmed) {
            // delete the entry
            currentJournal.deleteEntry(selectedEntry.getCreatedTime());

            // refresh the table
            ((JournalEntriesTableModel) entriesTable.getModel()).refreshData();

            // update the view (show empty state if needed)
            updateView();
        }
    }

    private void saveJournal() {
        try {

            String jsonStore = "data/userData/" + currentJournal.getName() + ".json";
            JsonWriter jsonWriter = new JsonWriter(jsonStore);
            jsonWriter.open();
            jsonWriter.write(currentJournal);
            jsonWriter.close();
            System.out.println("Saved" + " journal: " + currentJournal.getName() + " to '" + jsonStore + "'");
            JOptionPane.showMessageDialog(
                    this,
                    ("Saved" + " journal: " + currentJournal.getName() + " to '" + jsonStore
                            + "'"),
                    "Save complete",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "\"Unable to write to file: \" + jsonStore)",
                    "Path not found",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void updateHeader() {
        if (currentJournal != null) {
            titleLabel.setText(currentJournal.getName());
        }
    }

    private void createEmptyStatePanel() {
        emptyStatePanel = new JPanel();
        emptyStatePanel.setLayout(new BoxLayout(emptyStatePanel, BoxLayout.Y_AXIS));
        emptyStatePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 30, 20));
        emptyStatePanel.setBackground(Color.WHITE);

        // Add vertical space at the top
        emptyStatePanel.add(Box.createVerticalGlue());

        // Image
        ImageIcon rawEmptyImage = new ImageIcon("src/main/ui/screens/empty-journal.png");
        Image scaledImage = rawEmptyImage.getImage().getScaledInstance((int) (872 / 1.6), (int) (744 / 1.6),
                Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel emptyImage = new JLabel(resizedIcon);
        emptyImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyStatePanel.add(emptyImage);

        // Text
        JLabel emptyText = new JLabel(
                "No journal entries to show. Click Actions button on the top right to create a new journal entry.");
        emptyText.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        emptyText.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyStatePanel.add(Box.createRigidArea(new Dimension(0, 30)));
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

        TableColumnModel columnModel = entriesTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(400);
        columnModel.getColumn(2).setPreferredWidth(100);

        // add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(entriesTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        entriesTablePanel.add(scrollPane, BorderLayout.CENTER);

        entriesTable.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                label.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
                label.setHorizontalAlignment(CENTER);
                label.setOpaque(true);
                label.setForeground(new Color(68, 101, 233));
                label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 2, 0, (new Color(68, 101, 233))), // line
                        BorderFactory.createEmptyBorder(10, 5, 10, 5) // padding
                ));
                return label;
            }
        });
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
