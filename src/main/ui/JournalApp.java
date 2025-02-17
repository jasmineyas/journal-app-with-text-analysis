package ui;

import java.time.format.DateTimeParseException;
import java.util.*;
import model.*;

/**
 * JournalApp is a simple journal application with text analysis. 
 * This is the console interface class.  
 */

public class JournalApp {
    private Journal journal;
    private Scanner scanner;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public JournalApp() {
        this.journal = new Journal();
        this.scanner = new Scanner(System.in);
    }

    // EFFECT: display options on what the user can do - show available commands
    public void displayMenu() {
        System.out.println("\n=== 📝 Journal App Menu ===");
        System.out.println("Available commands:");
        System.out.println("1. create - Create a new journal entry");
        System.out.println("2. view <yyyy-MM-dd HH:mm:ss> - View an entry");
        System.out.println("3. edit <yyyy-MM-dd HH:mm:ss> - Edit an entry");
        System.out.println("4. delete <yyyy-MM-dd HH:mm:ss> - Delete an entry");
        System.out.println("5. list - List all entries");
        System.out.println("6. quit - Exit the application");
        System.out.println("===========================");
    }

    // EFFECT: read user commands and call the right methods
    public void handleUserInput() {
        boolean running = true;
        while (running) {
            displayMenu();
            System.out.print("Enter command: ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split(" ", 2);
            try {
                running = processCommand(input, parts);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use: " + DATE_FORMAT + ")");
            }
        }

    }

    // below are all the helper functions for the handleUserInput

    private boolean processCommand(String input, String[] parts) {
        String command = parts[0].toLowerCase();

        switch (command) {
            case "create":
                if (parts.length > 1) {
                    System.out.println("Invalid command. Please only type 'create' to create a new entry.");
                } else {
                    createEntry();
                }
                return true;
            case "view":
            case "edit":
            case "delete":
                handleDateBasedCommand(parts, command);
                return true;
            case "list":
                handleListCommand(input);
                return true;
            case "quit":
                return handleQuitCommand(input);
            default:
                System.out.println("Invalid command. Please try again.");
                return true;
        }
    }

    // EFFECTS: handles commands that require a date parameter, displays error if
    // date is missing
    private void handleDateBasedCommand(String[] parts, String command) {
        if (parts.length < 2) {
            System.out.println("Please provide a date (format: " + DATE_FORMAT + ")");
        } else {
            switch (command) {
                case "view":
                    viewEntry(parts[1]);
                    break;
                case "edit":
                    editEntry(parts[1]);
                    break;
                case "delete":
                    deleteEntry(parts[1]);
                    break;
            }
        }
    }

    // EFFECT: display the journal entry and its text analysis specififed by the
    // user specificed date
    private void viewEntry(String dateString) {
        JournalEntry entry = journal.getEntry(dateString);
        if (entry == null) {
            System.out.println("Entry not found.");
            return;
        }

        System.out.println("\n=== Journal Entry ===");
        System.out.println("Created: " + journal.formatDateTime(entry.getCreatedTime()));
        System.out.println("Last Updated: " + journal.formatDateTime(entry.getLastUpdatedTime()));
        System.out.println("\n💭 Content:");
        System.out.println(entry.getContent());
        System.out.println("\n💡 Analysis:");
        System.out.println("Word Count: " + entry.getWordCount());
        System.out.println("Overall Mood: " + entry.getOverallMood());
        System.out.println("Time Orientation: " + entry.getTimeOrientation());
        System.out.println("Primary Sense: " + entry.getPrimarySense());
        System.out.println("Perspective: " + entry.getUsAndThem());
    }

    // EFFECT: allow the user to edit the entry if it exists
    private void editEntry(String dateString) {
        JournalEntry entry = journal.getEntry(dateString);
        if (entry == null) {
            System.out.println("Entry not found.");
            return;
        }

        System.out.println("Current content:");
        System.out.println(entry.getContent());
        System.out.println("\nEnter new content (type ':wq' on a new line to save and exit):");

        StringBuilder newContent = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equals(":wq")) {
            newContent.append(line).append("\n");
        }

        journal.updateEntry(entry, newContent.toString().trim());
        System.out.println("\nEntry updated successfully.");
        System.out.println("\n🆕 Updated analysis:");
        System.out.println("Word Count: " + entry.getWordCount());
        System.out.println("Overall Mood: " + entry.getOverallMood());
        System.out.println("Time Orientation: " + entry.getTimeOrientation());
        System.out.println("Primary Sense: " + entry.getPrimarySense());
        System.out.println("Perspective: " + entry.getUsAndThem());
    }

    // EFFECT: create a new journal entry and ask user to write the new entry
    private void createEntry() {
        System.out.println("Enter your journal entry (type ':wq' on a new line to save and exit):");
        StringBuilder content = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equals(":wq")) {
            content.append(line).append("\n");
        }

        JournalEntry newEntry = new JournalEntry(content.toString().trim());
        if (journal.createNewEntry(newEntry)) {
            System.out.println("Entry created successfully.");
        }
    }

    // EFFECT: delete the entry user specified only if the entry exists in the
    // journal
    private void deleteEntry(String dateString) {
        JournalEntry entry = journal.getEntry(dateString);
        if (entry == null) {
            System.out.println("Entry not found.");
            return;
        }
        System.out.print("Are you sure you want to delete this entry? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("y")) {
            if (journal.deleteEntry(dateString)) {
                System.out.println("Entry deleted successfully.");
            } else {
                System.out.println("Deletion cancelled.");
            }
        }
    }

    // EFFECT: check the input command
    private void handleListCommand(String input) {
        if (input.trim().equalsIgnoreCase("list")) {
            listEntries();
        } else {
            System.out.println("Invalid command. Please only type 'list' to list all entries.");
        }
    }

    // EFFECT: loads and displays all the entries
    private void listEntries() {
        if (journal.getNumberOfEntries() == 0) {
            System.out.println("No entries found.");
            return;
        }

        System.out.println("\n=== All Entries ===");
        System.out.println(journal.formatAllEntries());

    }

    // EFFECT: handle quit command
    private boolean handleQuitCommand(String input) {
        if (input.trim().equalsIgnoreCase("quit")) {
            return false;
        } else {
            System.out.println("Invalid command. Did you intend to quit? please use 'quit' to exit.");
            return true;
        }
    }

    public static void main(String[] args) throws Exception {
        JournalApp app = new JournalApp();
        app.handleUserInput();
        System.out.println("\nThank you for taking care of yourself!💖 ");
    }

}
