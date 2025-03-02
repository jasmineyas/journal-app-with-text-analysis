package ui;

import java.time.format.DateTimeParseException;
import java.util.*;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * JournalApp is a simple journal application with text analysis.
 * This is the console interface class.
 */

public class JournalApp {
    private Journal journal;
    private Scanner scanner;
    private String jsonStore;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private boolean isModified;

    public JournalApp() throws FileNotFoundException {
        this.scanner = new Scanner(System.in);
        this.journal = new Journal("Jasmine's journal");
        intiJournalApp();
        handleUserInput();
    }

    // EFFECTS: set up the journal file for the session
    private void intiJournalApp() {
        System.out.println("Welcome to the journal app!👋");
        loadOrNewJournal();
    }

    // EFFECTS: prompts user to load an existing journal or create a new journal
    private void loadOrNewJournal() {
        System.out.println("=====================================================");
        System.out.println("Setting up the journal app...");
        while (true) {
            System.out.println("\tload - load an existing journal file");
            System.out.println("\tnew - create a new journal file");
            System.out.println("\tquit - leave the application");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("load")) {
                System.out.println(
                        "Enter the name of the journal file to load (e.g., 'jasmine' → loads 'data/userData/jasmine.json')");
                String fileName = scanner.nextLine().trim();
                jsonStore = "data/userData/" + fileName + ".json";

                jsonReader = new JsonReader(jsonStore);
                jsonWriter = new JsonWriter(jsonStore);

                try {
                    journal = jsonReader.read();
                    System.out.println("Loaded " + journal.getName() + "from" + jsonStore);
                    break;
                } catch (IOException e) {
                    System.out.println("File not found. Please check for typo or create a new file.");
                }
            } else if (choice.equals("new")) {
                createNewJournal();
                break;
            } else if (choice.equals("quit")) {
                System.out.println("Exiting application. Goodbye!");
                System.exit(0);
            } else {
                System.out.println("Invalid input. Please enter 'load' to load or 'new' to create a new journal.");
            }
        }
        isModified = false;
    }

    // EFFECTS: prompts user to provide a new journal anme and creates a new journal
    private void createNewJournal() {
        while (true) {
            String name = getValidFileName();
            name = resolveFileConflict(name);

            if (name == null)
                continue; // Restart if user chooses to rename

            journal = new Journal(name);
            jsonStore = "data/userData/" + name + ".json";

            System.out.println("Your journal will be saved as '" + jsonStore + "'");
            jsonWriter = new JsonWriter(jsonStore);
            jsonReader = new JsonReader(jsonStore);
            System.out.println("Created new journal: " + name);
            break;
        }
    }

    // EFFECTS: Prompts for a valid filename
    private String getValidFileName() {
        while (true) {
            System.out.println("Enter a name for your new journal:");
            String name = scanner.nextLine().trim();

            if (isValidFileName(name)) {
                return name;
            }
            System.out.println("Invalid file name! Please avoid special characters.");
        }
    }

    // EFFECTS: Resolves conflicts if the file already exists
    private String resolveFileConflict(String name) {
        File file = new File("data/userData/" + name + ".json");

        while (file.exists()) {
            System.out.println("A journal with this name already exists. Choose an option:");
            System.out.println("\t1 - Keep both: create a new file with a number");
            System.out.println("\t2 - Rewrite: overwrite the existing file");
            System.out.println("\t3 - Rename: enter a new name");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    name = getUniqueFileName(name);
                    System.out.println("Creating a new file: " + name);
                    return name;
                case "2":
                    System.out.println("Overwriting existing file...");
                    return name;
                case "3":
                    return null; // Signal to restart journal creation with a new name
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
        return name;
    }

    // EFFECTS: append a number to an existing file name to make it unique
    private String getUniqueFileName(String fileName) {
        int counter = 0;
        File file;
        String newName;

        do {
            counter++;
            newName = fileName + "(" + counter + ")";
            file = new File("data/userData/" + newName + ".json");
        } while (file.exists());

        return newName;
    }

    // EFFECTS: checks if a filename is valid (mac os)
    private boolean isValidFileName(String fileName) {
        if (fileName.isEmpty() || fileName.contains("/")) {
            return false;
        } else {
            return true;
        }
    }

    // EFFECTS: display options on what the user can do - show available commands
    public void displayMenu() {
        System.out.println("\n================ 📝 Journal App Menu ================");
        System.out.println("You are currently in journal: " + journal.getName());
        System.out.println("Available commands:");
        System.out.println("\tcreate - Create a new journal entry");
        System.out.println("\tview <yyyy-MM-dd HH:mm:ss> - View an entry");
        System.out.println("\tedit <yyyy-MM-dd HH:mm:ss> - Edit an entry");
        System.out.println("\tdelete <yyyy-MM-dd HH:mm:ss> - Delete an entry");
        System.out.println("\tlist - List all entries");
        System.out.println("\tsave - Save the journal to file");
        System.out.println("\tquit - Exit the application");
        System.out.println("\tback - Go back to load or create new screen");
        System.out.println("=====================================================");
    }

    // EFFECTS: read user commands and call the right methods
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
        System.out.println("\nThank you for taking care of yourself!💖 ");
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
            case "save":
                handleSave();
                return true;
            case "quit":
                return handleQuitCommand(input);
            case "back":
                handleBackCommand();
                return true;
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

    // EFFECTS: display the journal entry and its text analysis specififed by the
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

    // EFFECTS: allow the user to edit the entry if it exists
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

        isModified = true;
    }

    // EFFECTS: create a new journal entry and ask user to write the new entry
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
            isModified = true;
        }
    }

    // EFFECTS: delete the entry user specified only if the entry exists in the
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
                isModified = true;
            } else {
                System.out.println("Deletion cancelled.");
            }
        }
    }

    // EFFECTS: check the input command
    private void handleListCommand(String input) {
        if (input.trim().equalsIgnoreCase("list")) {
            listEntries();
        } else {
            System.out.println("Invalid command. Please only type 'list' to list all entries.");
        }
    }

    // EFFECTS: loads and displays all the entries
    private void listEntries() {
        if (journal.getNumberOfEntries() == 0) {
            System.out.println("No entries found.");
            return;
        }

        System.out.println("\n==================== All Entries ====================");
        System.out.println(journal.formatAllEntries());
    }

    // EFFECTS: saves the journal to file
    private void handleSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            isModified = false;
            System.out.println("Saved" + " journal: " + journal.getName() + " to '" + jsonStore + "'");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + jsonStore);
        }
    }

    // EFFECTS: handle quit command
    private boolean handleQuitCommand(String input) {
        if (input.trim().equalsIgnoreCase("quit")) {
            if (isModified) {
                while (true) {
                    System.out.println("You have unsaved changes. Choose an option:");
                    System.out.println("\tSave: save and quit");
                    System.out.println("\tDon't save: discard changes and quit");
                    System.out.println("\tCancel: cancel and return to menu");
                    String response = scanner.nextLine().trim().toLowerCase();

                    switch (response) {
                        case "save":
                            handleSave();
                            return false;
                        case "don't save":
                            System.out.println("Discarding changes... quiting...");
                            return false;
                        case "cancel":
                            return true;
                        default:
                            System.out.println("Invalid selection. Please enter 'Save', 'Don't save', or 'Cancel'.");
                    }
                }
            }
            return false;
        } else {
            System.out.println("Invalid command. Did you intend to quit? please use 'quit' to exit.");
            return true;
        }
    }

    // EFFECTS: handle back command
    private void handleBackCommand() {
        if (isModified) {
            while (true) {
                System.out.println("You have unsaved changes. Choose an option:");
                System.out.println("\tSave: save and go back");
                System.out.println("\tDon't save: discard changes and go back");
                System.out.println("\tCancel: cancel and return to menu");
                String response = scanner.nextLine().trim().toLowerCase();

                switch (response) {
                    case "save":
                        handleSave();
                        loadOrNewJournal();
                        return;
                    case "don't save":
                        System.out.println("Discarding changes... going back to main menu.");
                        loadOrNewJournal();
                        return;
                    case "cancel":
                        return;
                    default:
                        System.out.println("Invalid selection. Please enter 'Save', 'Don't save', or 'Cancel'.");
                }
            }
        } else {
            loadOrNewJournal();
        }
    }

}
