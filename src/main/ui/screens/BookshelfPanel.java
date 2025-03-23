package ui.screens;

import ui.*;
import ui.smallComponents.SimpleButton;
import ui.smallComponents.SimpleTextField;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Journal;

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
        // TODO: this is not working - setting background color
        setBackground(Color.WHITE);
        setOpaque(true);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

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
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create New Journal button
        SimpleButton createButton = new SimpleButton("Create new journal", Font.PLAIN, 20);
        // TODO: we'll do button class later!! Focus on functionality first.
        // createButton.setForeground(new Color(76, 176, 94));
        // createButton.setBackground(new Color(195, 214, 200));
        // createButton.setOpaque(true);
        // createButton.setBorder(new LineBorder(new Color(76, 176, 94), 3, true));
        // createButton.setMargin(new Insets(15, 15, 15, 15));
        createButton.addActionListener(e -> cardLayout.show(mainContainer, "CREATE"));
        buttonsPanel.add(createButton);

        // Open Existing Journal button
        SimpleButton openButton = new SimpleButton("Open existing journal", Font.PLAIN, 20);
        // openButton.setBackground(new Color(255, 182, 193)); // Light pink
        openButton.addActionListener(e -> cardLayout.show(mainContainer, "OPEN"));
        buttonsPanel.add(openButton);

        welcomePanel.add(buttonsPanel);
        welcomePanel.add(Box.createVerticalGlue());
    }

    private void setupCreateJournalPanel() {
        createJournalPanel = new JPanel();
        createJournalPanel.setLayout(new BoxLayout(createJournalPanel, BoxLayout.Y_AXIS));
        createJournalPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Back button
        JButton backButton = new JButton("< back to bookshelf");
        backButton.setFont(new Font("Comic Sans Ms", Font.PLAIN, 15));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setForeground(Color.GRAY);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.addActionListener(e -> cardLayout.show(mainContainer, "WELCOME"));
        createJournalPanel.add(backButton);

        // Add some spacing at the top
        createJournalPanel.add(Box.createVerticalGlue());

        // TODO: replace image
        JLabel welcomeImage = new JLabel(new ImageIcon("src/main/ui/screens/welcome-screen.png"));
        welcomeImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        createJournalPanel.add(welcomeImage);

        createJournalPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Welcome text
        JLabel createLabel = new JLabel("Enter the name for your new journal");
        createLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        createLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        createJournalPanel.add(createLabel);

        createJournalPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Input field and create button panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        SimpleTextField nameField = new SimpleTextField(20, 18, 200, 40);
        inputPanel.add(nameField);

        SimpleButton createButton = new SimpleButton("Create", Font.PLAIN, 20);
        createButton.addActionListener(e -> {
            String journalName = nameField.getText().trim();
            if (!journalName.isEmpty()) {
                // TODO: create new journal + that screen
                Journal newJournal = new Journal(journalName);
                mainApp.showJournal(newJournal);
            }
        });

        inputPanel.add(createButton);

        createJournalPanel.add(inputPanel);
        createJournalPanel.add(Box.createVerticalGlue());
    }

    private void setupLoadJournalPanel() {
        loadJournalPanel = new JPanel();
        loadJournalPanel.setLayout(new BoxLayout(loadJournalPanel, BoxLayout.Y_AXIS));
        loadJournalPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Back button
        JButton backButton = new JButton("< back to bookshelf");
        backButton.setFont(new Font("Comic Sans Ms", Font.PLAIN, 15));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setForeground(Color.GRAY);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.addActionListener(e -> cardLayout.show(mainContainer, "WELCOME"));
        loadJournalPanel.add(backButton);

        loadJournalPanel.add(Box.createVerticalGlue());

        // TODO: replace image
        JLabel welcomeImage = new JLabel(new ImageIcon("src/main/ui/screens/welcome-screen.png"));
        welcomeImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadJournalPanel.add(welcomeImage);

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

        SimpleTextField nameField = new SimpleTextField(20, 18, 200, 40);
        inputPanel.add(nameField);

        SimpleButton loadButton = new SimpleButton("Load", Font.PLAIN, 20);
        loadButton.addActionListener(e -> {
            String journalName = nameField.getText().trim();
            // if (!journalName.isEmpty()) {
            // TODO: load the journal from data
            // Journal journal = new Journal(journalName);
            // mainApp.showJournal(journal);
            // }
        });
        inputPanel.add(loadButton);

        loadJournalPanel.add(inputPanel);
        loadJournalPanel.add(Box.createVerticalGlue());
    }

}
