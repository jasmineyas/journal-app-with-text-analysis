package persistence;

import model.JournalEntry;
import model.Journal;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for JsonWriter
 * Strategy: write data to a file and then use the reader to read it back in and
 * check that we read in a copy of what was written out
 * Code reference:
 * https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 * /src/test/persistence/JsonWriterTest.java
 */

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Journal journal = new Journal("My journal");
            JsonWriter writer = new JsonWriter("data/my\\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyJournal() {
        try {
            Journal journal = new Journal("My journal");
            JsonWriter writer = new JsonWriter("data/persistence/testWriterEmptyJournal.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("data/persistence/testWriterEmptyJournal.json");
            journal = reader.read();
            assertEquals("My jounral", journal.getName());
            assertEquals(0, journal.getNumberOfEntries());
        } catch (IOException e) {
            fail("Exception should not have been throw :(");
        }
    }

    @Test
    void testWriterGeneralJournal() {
        try {
            Journal journal = new Journal("My journal");
            JournalEntry entry1 = new JournalEntry("I am so happy today!! YAYYYYYYYYY!");
            LocalDateTime entry1Time = entry1.getCreatedTime();
            Thread.sleep(2000);
            JournalEntry entry2 = new JournalEntry("we ate so much food yesterday. I am not even hungry this morning.");
            LocalDateTime entry2Time = entry2.getCreatedTime();
            journal.createNewEntry(entry1);
            journal.createNewEntry(entry2);
            JsonWriter writer = new JsonWriter("data/persistence/testWriterGeneralJournal.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("data/persistence/testWriterGeneralJournal.json");
            Journal readJournal = reader.read();
            assertEquals("My journal", readJournal.getName());
            assertEquals(2, readJournal.getNumberOfEntries());
            Map<LocalDateTime, JournalEntry> expectedJournalEntries = journal.getAllEntries();
            Map<LocalDateTime, JournalEntry> readJournalEntries = readJournal.getAllEntries();
            assertEquals(expectedJournalEntries.keySet(), readJournalEntries.keySet());
            // we have passed the keys check so...we can use the entry1Time on
            // readJournalEntries
            checkJournalEntry(readJournalEntries.get(entry1Time), entry1.getLastUpdatedTime(), entry1.getContent());
            checkJournalEntry(readJournalEntries.get(entry2Time), entry2.getLastUpdatedTime(), entry2.getContent());
        } catch (IOException e) {
            fail("Exception should not have been thrown :(");
        } catch (InterruptedException e) {
            fail("Thread was interrupted :( ");
        }
    }
}
