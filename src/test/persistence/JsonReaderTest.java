package persistence;

import model.Journal;
import model.JournalEntry;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for JsonReader
 * Code reference:
 * https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 * /src/test/persistence/JsonReaderTest.java
 */
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("data/noSuchFile.json");
        try {
            Journal journal = reader.read();
            fail("IOException expected :( )");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyJournal() {

        JsonReader reader = new JsonReader("data/testData/persistence/testReaderEmptyJournal.json");
        try {
            Journal journal = reader.read();
            assertEquals("My journal", journal.getName());
            assertEquals(0, journal.getNumberOfEntries());
        } catch (IOException e) {
            fail("Couldn't read from file :( ");
        }
    }

    @Test
    void testReaderGeneralJournal() {
        JsonReader reader = new JsonReader("data/testData/persistence/testReaderGeneralJournal.json");
        try {
            Journal journal = reader.read();
            assertEquals("Jasmine's journal", journal.getName());
            Map<LocalDateTime, JournalEntry> journalEntries = journal.getAllEntries();
            assertEquals(2, journalEntries.size());
            Set<LocalDateTime> expectedKeys = Set.of(
                    LocalDateTime.parse("2025-03-01T21:16:32"),
                    LocalDateTime.parse("2025-01-01T21:16:32"));
            assertEquals(expectedKeys, journalEntries.keySet());
            checkJournalEntry(
                    journalEntries.get(LocalDateTime.parse("2025-03-01T21:16:32")),
                    LocalDateTime.parse("2025-03-04T18:00:00"),
                    "i had a really nice day today! I am really happy that I get to eat the food with my mom!");
            checkJournalEntry(
                    journalEntries.get(LocalDateTime.parse("2025-01-01T21:16:32")),
                    LocalDateTime.parse("2025-02-04T18:00:00"),
                    "Feeling tired today, but still managed to get some work done. Hoping for better sleep tonight.");
        } catch (IOException e) {
            fail("Couldn't read from file :(");
        }
    }

}
