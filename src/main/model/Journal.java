package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Journal {
    private Map<LocalDateTime, JournalEntry> journalEntries;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


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
    // EFFECT: update an existing entry content and last updated date 
    public void updateEntry(JournalEntry entry, String newContent) {
    }

    // REQUIRE: this entry exists in the journal
    // MODIFY: this
    // EFFECT: delete an entry and return the status of the delete
    public boolean deleteEntry(LocalDateTime dateTime) {
        return true;
    }

    // REQUIRE: this entry exists in the journal
    // MODIFY: this
    // EFFECTS: delete an entry by its date time string
    public boolean deleteEntry(String dateTimeStr){
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DATE_FORMATTER);
        return deleteEntry(dateTime);
    }

    // REQUIRES: this entry is in the journal or data folder
    // EFFECTS: get the entry by date time 
    public JournalEntry getEntry(LocalDateTime dateTime) {
        return journalEntries.get(dateTime);
    }

    // REQUIRES: this entry is in the journal or data folder
    // EFFECTS: get the entry by date time string
    public JournalEntry getEntry(String dateTimeStr) {
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DATE_FORMATTER);
        return getEntry(dateTime);
    }

    // EFFECTS: Returns the formatted date time string of a journal entry
    public String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }

    // TODO: should I display last updated time as well?
    // EFFECTS: Returns all entries formatted
    public List<String> getAllEntriesFormatted() {
        return List.of("2021-01-01 12:00:00 - Hello, world!",
                "2021-01-02 12:00:00 - Today I am feeling really happy. I will be going...");
    }

    // EFFECTS: Returns the number of entries in the journal
    public int getNumberOfEntries() {
        return journalEntries.size();
    }

}
