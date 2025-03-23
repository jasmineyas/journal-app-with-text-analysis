package ui.screens;

import ui.*;
import ui.smallComponents.ComicSansButton;
import ui.smallComponents.ComicSansTextField;

import java.awt.*;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Journal;
import persistence.JsonReader;

public class BookshelfPanel extends JPanel {
    private JournalAppGUI mainApp;
    private CardLayout cardLayout;

    private JPanel mainContainer;

    private JPanel welcomePanel;
    private JPanel createJournalPanel;
    private JPanel loadJournalPanel;

    public BookshelfPanel(JournalAppGUI mainApp) {
        this.mainApp = mainApp;
        setupUI();

    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setOpaque(true);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        setOpaque(true);

        setupWelcomePanel();
        setupCreateJournalPanel();
        setupLoadJournalPanel();

        mainContainer.add(welcomePanel, "WELCOME");
        mainContainer.add(createJournalPanel, "CREATE");
        mainContainer.add(loadJournalPanel, "OPEN");

        add(mainContainer, BorderLayout.CENTER);

        cardLayout.show(mainContainer, "WELCOME");
    }

    private void setupWelcomePanel() {
        welcomePanel = new JPanel();
        welcomePanel.setBackground(Color.WHITE);
        welcomePanel.setOpaque(true);

        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add some spacing at the top
        welcomePanel.add(Box.createVerticalGlue());

        JLabel welcomeImage = new JLabel(new ImageIcon("src/main/ui/screens/welcome-screen.png"));
        welcomeImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(welcomeImage);

        welcomePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome to your journal bookshelf");
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(welcomeLabel);

        welcomePanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.WHITE); 
        buttonsPanel.setOpaque(true);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create New Journal button
        ComicSansButton createButton = new ComicSansButton("Create new journal", Font.PLAIN, 20);
        createButton.addActionListener(e -> cardLayout.show(mainContainer, "CREATE"));
        buttonsPanel.add(createButton);

        // Open Existing Journal button
        ComicSansButton openButton = new ComicSansButton("Open existing journal", Font.PLAIN, 20);
        openButton.addActionListener(e -> cardLayout.show(mainContainer, "OPEN"));
        buttonsPanel.add(openButton);

        welcomePanel.add(buttonsPanel);
        welcomePanel.add(Box.createVerticalGlue());
    }

    private void setupCreateJournalPanel() {
        createJournalPanel = new JPanel();
        createJournalPanel.setBackground(Color.WHITE);
        createJournalPanel.setOpaque(true);
        createJournalPanel.setLayout(new BoxLayout(createJournalPanel, BoxLayout.Y_AXIS));
        createJournalPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Back button
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        backButtonPanel.setBackground(Color.WHITE);
        backButtonPanel.setOpaque(true);

        JButton backButton = new JButton("< back");
        backButton.setFont(new Font("Comic Sans Ms", Font.PLAIN, 18));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setForeground(Color.GRAY);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        backButton.addActionListener(e -> cardLayout.show(mainContainer, "WELCOME"));
        backButtonPanel.add(backButton);

        createJournalPanel.add(backButtonPanel);

        // Add some spacing at the top
        createJournalPanel.add(Box.createVerticalGlue());

        ImageIcon rawNewJournalImage = new ImageIcon("src/main/ui/screens/new-journal.png");
        Image scaledImage = rawNewJournalImage.getImage().getScaledInstance(
                (int) (rawNewJournalImage.getIconWidth() / 1.2),
                (int) (rawNewJournalImage.getIconHeight() / 1.2),
                Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel emptyImage = new JLabel(resizedIcon);
        emptyImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        createJournalPanel.add(emptyImage);

        createJournalPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Welcome text
        JLabel createLabel = new JLabel("Enter the name for your new journal");
        createLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        createLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        createJournalPanel.add(createLabel);

        createJournalPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Input field and create button panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ComicSansTextField nameField = new ComicSansTextField(20, 18, 200, 40);
        inputPanel.add(nameField);

        ComicSansButton createButton = new ComicSansButton("Create", Font.PLAIN, 20);
        createButton.addActionListener(e -> {
            String journalName = nameField.getText().trim();
            if (!journalName.isEmpty()) {
                Journal newJournal = new Journal(journalName);
                mainApp.showJournal(newJournal);
            }
        });

        inputPanel.add(createButton);
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setOpaque(true);

        createJournalPanel.add(inputPanel);
        createJournalPanel.add(Box.createVerticalGlue());
        createJournalPanel.add(Box.createVerticalGlue());
        createJournalPanel.add(Box.createVerticalGlue());
    }

    private void setupLoadJournalPanel() {
        loadJournalPanel = new JPanel();
        loadJournalPanel.setOpaque(true);
        loadJournalPanel.setBackground(Color.WHITE);
        loadJournalPanel.setLayout(new BoxLayout(loadJournalPanel, BoxLayout.Y_AXIS));
        loadJournalPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Back button
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        backButtonPanel.setBackground(Color.WHITE);
        backButtonPanel.setOpaque(true);

        JButton backButton = new JButton("< back");
        backButton.setFont(new Font("Comic Sans Ms", Font.PLAIN, 18));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setForeground(Color.GRAY);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        backButton.addActionListener(e -> cardLayout.show(mainContainer, "WELCOME"));
        backButtonPanel.add(backButton);

        loadJournalPanel.add(backButtonPanel);

        loadJournalPanel.add(Box.createVerticalGlue());

        ImageIcon rawLoadJournalImage = new ImageIcon("src/main/ui/screens/load-journal.png");
        Image scaledImage = rawLoadJournalImage.getImage().getScaledInstance(
                (int) (rawLoadJournalImage.getIconWidth() / 1.4),
                (int) (rawLoadJournalImage.getIconHeight() / 1.4),
                Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel loadJournalImage = new JLabel(resizedIcon);
        loadJournalImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadJournalPanel.add(loadJournalImage);

        loadJournalPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Title
        JLabel titleLabel = new JLabel("Enter the journal name you want to load");
        titleLabel.setFont(new Font("Comic Sans Ms", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadJournalPanel.add(titleLabel);

        loadJournalPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Input field and load button panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ComicSansTextField nameField = new ComicSansTextField(20, 18, 200, 40);
        inputPanel.add(nameField);
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setOpaque(true);

        ComicSansButton loadButton = new ComicSansButton("Load", Font.PLAIN, 20);
        loadButton.addActionListener(e -> {
            String journalName = nameField.getText().trim();
            if (!journalName.isEmpty()) {
                String jsonStore = "data/userData/" + journalName + ".json";
                JsonReader jsonReader = new JsonReader(jsonStore);
                try {
                    Journal journal = jsonReader.read();
                    System.out.println("Loaded " + journal.getName() + "from" + jsonStore);
                    mainApp.showJournal(journal);
                } catch (IOException error) {
                    JOptionPane.showMessageDialog(null,
                            "File not found. Please check for typo or create a new file.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        inputPanel.add(loadButton);

        loadJournalPanel.add(inputPanel);
        loadJournalPanel.add(Box.createVerticalGlue());
        loadJournalPanel.add(Box.createVerticalGlue());
        loadJournalPanel.add(Box.createVerticalGlue());
    }
}
