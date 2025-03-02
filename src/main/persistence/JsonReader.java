package persistence;

import model.JournalEntry;
import model.Journal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        return new Journal("lala");
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String soruce) throws IOException {
        return "la";
    }

    // TODO: what's the difference betweeen thtis one and the previous one?
    // EFFECTS: parses journal from JSON object and returns it
    private Journal parseJournal(JSONObject jsonobject) {
        return new Journal("lala");
    }

    // MODIFIDES: journal
    // EFFECTS: parses journalEntries from JSON object and adds them to Journal
    private void addJournalEntries(Journal journal, JSONObject jsonObject) {

    }

    // MODIFIES: journal
    // EFFECTS: parses journalEntry from JSON object and adds it to Journal
    private void addJournalEntry(Journal jounral, JSONObject jsonObject) {

    }

}
