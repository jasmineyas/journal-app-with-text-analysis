package model;

import java.time.LocalDateTime;
import java.util.*;

public class Journal {
    private List<JournalEntry> journalEntries;

    public Journal() {
        journalEntries = new ArrayList<JournalEntry>();
    }

    // REQUIRE: entry does not already exist in the journal
    // MODIFY: this
    // EFFECT: add a new entry to the existing journal
    public void addEntry(JournalEntry entry) {
    }

    // REQUIRE: journal is not empty && this entry is in the journal
    // MODIFY: this
    // EFFECT: delete an entry
    public void deleteEntry(LocalDateTime dateTime) {
    }

    // REQUIRE: journal is not empty && this entry is in the journal
    // EFFECT: return the content of the journal along with its text analysis
    public JournalEntry getEntry(LocalDateTime dateTime) {
        return new JournalEntry(dateTime, "");
    }

    // EFFECT: return the preview of all journal entries in the journal
    public List<String> getAllEntries(Journal journal) {
        return new ArrayList<>();
    }

}
