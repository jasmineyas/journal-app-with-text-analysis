package model;

import java.time.LocalDateTime;
import java.util.*;

public class Journal {
    private Map<LocalDateTime, JournalEntry> journalEntries;

    public Journal() {
        journalEntries = new HashMap<>();
    }

    // REQUIRE: the entry doesn't exist already
    // MODIFY: this
    // EFFECT: create an new entry, return True it is created
    public boolean createNewEntry(JournalEntry entry) {
        return true;
    }

    // REQUIRE: the entry exists in the list
    // MODIFY: this
    // EFFECT: update an existing entry
    public void updateEntry(JournalEntry entry) {
    }

    // REQUIRE: this entry exists in the journal
    // MODIFY: this
    // EFFECT: delete an entry and return the status of the delete
    public boolean deleteEntry(LocalDateTime dateTime) {
        return true;
    }

    // REQUIRES: this entry is in the journal or data folder
    // EFFECTS: return the content of the journal along with its text analysis
    public JournalEntry getEntry(LocalDateTime dateTime) {
        return journalEntries.get(dateTime);
    }

    // EFFECTS: display all past entries with dates and summary
    public void displayAllEntries() {

    }

}
