package ui.smallComponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConfirmationDialog extends JDialog {

    private boolean confirmed = false;

    public ConfirmationDialog(
            JFrame parent,
            String title,
            String message,
            String primaryButtonText,
            String secondaryButtonText) {

        super(parent, title, true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel messageLabel = new ComicSansLabel(message, Font.BOLD, 16);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ComicSansButton secondaryButton = new ComicSansButton(secondaryButtonText, Font.PLAIN, 16);
        secondaryButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        ComicSansButton primaryButton = new ComicSansButton(primaryButtonText, Font.BOLD, 16);
        primaryButton.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        buttonsPanel.add(secondaryButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        buttonsPanel.add(primaryButton);

        panel.add(buttonsPanel);

        setContentPane(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(parent);
    }

    public boolean showDialog() {
        setVisible(true);
        return confirmed;
    }

}
