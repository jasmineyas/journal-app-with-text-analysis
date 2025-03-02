package persistence;

import model.JournalEntry;
import model.Journal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import org.json.*;

// represents a reader that reads workroom from JSON data stroed in file 
// TODO: add proper code source reference: json serialization demo 

public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads journal from file and returns it;
    // throws IOEXception if an error occurs reading data from file
    public Journal read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseJournal(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String soruce) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses journal from JSON object and returns it
    private Journal parseJournal(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Journal journal = new Journal(name);
        addJournalEntries(journal, jsonObject);
        return journal;
    }

    // MODIFIDES: journal
    // EFFECTS: parses journalEntries from JSON object and adds them to Journal
    private void addJournalEntries(Journal journal, JSONObject jsonObject) {
        JSONObject journalEntriesJson = jsonObject.getJSONObject("journalEntries");
        for (String createdTimeString : journalEntriesJson.keySet()) {
            JSONObject journalEntryJsonObject = journalEntriesJson.getJSONObject(createdTimeString);
            addJournalEntry(journal, createdTimeString, journalEntryJsonObject);
        }

    }

    // MODIFIES: journal
    // EFFECTS: parses journalEntry from JSON object and adds it to Journal
    private void addJournalEntry(Journal journal, String createdTimeString, JSONObject journalEntryJsonObject) {
        LocalDateTime createdTime = LocalDateTime.parse(createdTimeString);
        String content = journalEntryJsonObject.getString("content");
        LocalDateTime lastupdatedTime = LocalDateTime.parse(journalEntryJsonObject.getString("lastUpdatedTime"));

        JournalEntry entry = new JournalEntry(content);
        entry.setCreatedTime(createdTime);
        entry.setLastUpdatedTime(lastupdatedTime);
        journal.createNewEntry(entry);
    }
}
