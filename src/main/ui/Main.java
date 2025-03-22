package ui;

import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.SwingUtilities;

/**
 * Simple journal app with text analysis that can run either with GUI or in
 * console.
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String appMode = appModeChooser(scanner);

        if (appMode.equals("GUI")) {
            SwingUtilities.invokeLater(() -> {
                new JournalAppGUI().setVisible(true);
            });
        } else if (appMode.equals("console")) {
            try {
                new JournalConsoleApp();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to run application: file not found");
            }
        }

        scanner.close();

    }

    public static String appModeChooser(Scanner scanner) {

        System.out.println("Select application mode for simple journal app:");
        System.out.println(" - GUI");
        System.out.println(" - console");

        while (true) {
            System.out.print("Enter your choice (GUI or Console): ");
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("gui")) {
                return "GUI";
            } else if (choice.equals("console")) {
                return "console";
            } else {
                System.out.println("Invalid choice. Please enter 'GUI' or 'Console'.");
            }
        }

    }

}
