package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.json.JSONObject;

import persistence.Writable;

/**
 * Journal class represents a collection of journal entries.
 * It allows CRUD operations on journal entries.
 */

public class Journal implements Writable {

    private String name;
    private Map<LocalDateTime, JournalEntry> journalEntries;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Journal(String name) {
        this.name = name;
        journalEntries = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    // REQUIRE: the entry doesn't exist already
    // MODIFY: this
    // EFFECTS: create an new entry, return True it is created,
    // returns False if it already exists
    public boolean createNewEntry(JournalEntry entry) {
        if (journalEntries.containsKey(entry.getCreatedTime())) {
            return false;
        }
        journalEntries.put(entry.getCreatedTime(), entry);
        return true;
    }

    // REQUIRE: the entry exists in the list and newContent is not empty
    // MODIFY: this
    // EFFECTS: update an existing entry content
    public void updateEntry(JournalEntry entry, String newContent) {
        journalEntries.get(entry.getCreatedTime()).editContent(newContent);
    }

    // REQUIRE: this entry exists in the journal
    // MODIFY: this
    // EFFECTS: delete an entry with a provided datetime
    // return True if it is deleted, False if it doesn't exist
    public boolean deleteEntry(LocalDateTime dateTime) {
        if (!journalEntries.containsKey(dateTime)) {
            return false;
        }
        journalEntries.remove(dateTime);
        return true;
    }

    // REQUIRE: this entry exists in the journal
    // MODIFY: this
    // EFFECTS: delete an entry by its date time string
    public boolean deleteEntry(String dateTimeStr) {
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DATE_FORMATTER);
        return deleteEntry(dateTime);
    }

    // (I don't care about last updated date yet - could add in the future - added in GUI not console) 
    // EFFECTS: Returns all entries formatted in date time - entry preview format
    public String formatAllEntries() {
        StringBuilder entries = new StringBuilder();

        List<LocalDateTime> sortedDates = new ArrayList<>(journalEntries.keySet());
        Collections.sort(sortedDates, Collections.reverseOrder());

        for (LocalDateTime dateTime : sortedDates) {
            JournalEntry entry = journalEntries.get(dateTime);
            String formattedDateTime = formatDateTime(dateTime);
            entries.append(formattedDateTime);
            entries.append(" - ");
            entries.append(entry.getEntryPreview());
            entries.append("\n");
        }
        return entries.toString();
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

    // EFFECTS: Returns the number of entries in the journal
    public int getNumberOfEntries() {
        return journalEntries.size();
    }

    public Map<LocalDateTime, JournalEntry> getAllEntries() {
        return journalEntries;
    }

    @Override
    public JSONObject toJson() {
        JSONObject journalJson = new JSONObject();
        journalJson.put("name", this.name);

        JSONObject journalEntriesJson = new JSONObject();

        for (Map.Entry<LocalDateTime, JournalEntry> entry : journalEntries.entrySet()) {
            String createdTimeString = entry.getKey().toString();
            journalEntriesJson.put(createdTimeString, entry.getValue().toJson());
        }

        journalJson.put("journalEntries", journalEntriesJson);
        return journalJson;

    }

}
