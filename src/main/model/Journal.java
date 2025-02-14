package model;

import java.time.LocalDateTime;
import java.util.*;

public class Journal {
    private Map<LocalDateTime, JournalEntry> journalEntries;
    private Map<LocalDateTime, String> indexMap;

    public Journal() {
        journalEntries = new HashMap<>();
        indexMap = new HashMap<>();
        
        loadIndex();
    }

    // REQUIRE: the entry doesn't exist already 
    // MODIFY: this (journalEntries, indexMap, index file)
    // EFFECT: create an new entry, return True it is created   
    public boolean createNewEntry(JournalEntry entry) {
        return true;
    }

    // EFFECT: save each entry to its own file
    // still thinking about if this
    // is the right place to save it
    public void saveEntry(JournalEntry entry) {

    }

    // REQUIRE: the entry exists in the list
    // MODIFY: this (journalEntries, indexMap, index file)
    // EFFECT: update an existing entry
    public void updateEntry(JournalEntry entry) {
    }

    // REQUIRE: this entry exists in the journal
    // MODIFY: this
    // EFFECT: delete an entry and return the status of the delete 
    public boolean deleteEntry(LocalDateTime dateTime) {
        return true;
    }

    // REQUIRE: this entry is in the journal or data folder 
    // EFFECT: return the content of the journal along with its text analysis
    //         from the journal itself if it's in memory
    //         if not, load from file
    public JournalEntry getEntry(LocalDateTime dateTime) {
        return journalEntries.get(dateTime);
    }

    // REQUIRE: this entry is in the data folder 
    // EFFECT: load the entry from the file
    public JournalEntry loadEntry(LocalDateTime dateTime) {
        return journalEntries.get(dateTime); // stub
    }

    // MODIFY: this.indexMap
    // EFFECT: load index file into memory 
    public void loadIndex() {
        
    }

    // EFFECT: display all past entries 
    public void displayAllEntries() {

    }

    // MODIFY: this.indexMap, index file
    // EFFECT: update the index file when a new entry is created 
    //         or an entry is deleted or updated
    public void updateIndex(JournalEntry entry){

    }

    // MODIFY: this.indexMap, index file, this.journalEntries,
    // EFFECT: re run analyzer for all entries in the journal 
    public void reRunAnalyzer(){

    }

}
