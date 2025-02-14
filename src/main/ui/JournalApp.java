package ui;

import java.time.LocalDateTime;
import java.util.*;

import model.Analyzer;
import model.JournalEntry;

public class JournalApp {
    private JournalApp journalApp;
    private JournalEntry currentEntry;
    private Scanner scanner;

    public JournalApp() {
        this.scanner = scanner;
    }

    // EFFECT: display options on what the user can do - show available commands
    // if there's no entry yet, prompt the user to create a new entry
    // if there are entries, user will see a list of entries
    // user can view past journal entry and stats, create a new entry
    // delete a journal entry...
    // display instruction as well
    // user can use prompt "create new entry" to go to the next step
    // user can use prompt "delete entry xxxx-xx-xx" to delete
    // user can use prompt "view entry xxxx-xx-xx" to view
    // user can use prompt "edit entry xxxx-xx-xx" to edit
    public void displayMenu() {

    }

    // EFFECT: read user commands and call the right methods
    // if user types "create new entry"... user can create a new entry
    // question - how to save it? ....
    // if user types "delete entry xxxx-xx-xx" user can delete the entry
    // with a confirmation window
    // user can use prompt "view entry xxxx-xx-xx" to view
    // user can use prompt "edit entry xxxx-xx-xx" to enter the write mode and edit
    // maybe we need to look into how to exit it out of a writing mode and then save
    // look into vim?
    // for each user input - we need to have a verification process
    // maybe we need a currentJournal thing like the currentPuppy...
    public void handleUserInput() {

    }

    // below are all the helper functions for the handleUserInput

    // EFFECT: interact with the user
    // display the entry the user wants to view
    private void viewEntry(String dateString) {

    }

    // EFFECT: interact with the user
    // allow the user to edit the entry
    private void editEntry(String dateString) {

    }

    // EFFECT: interacct with the user create
    // create and ask user to write the new entry
    private void createEntry(String dataString) {

    }

    // EFFECT: interacct with the user create
    // delete the entry user specified
    private void deleteEntry(String dataString) {

    }

    // EFFECT: loads and displays all the entries
    private void loadAllEntries() {

    }

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to my project!");
    }
    
}
