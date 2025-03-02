package persistence;

import model.JournalEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

/**
 * Test class for persistence
 * Code reference:
 * https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 * /src/test/persistence/JsonTest.java
 */

public class JsonTest {
    protected void checkJournalEntry(JournalEntry journalEntryToCheck, LocalDateTime expectedLastUpdatedTime,
            String expectedContent) {
        assertEquals(expectedLastUpdatedTime, journalEntryToCheck.getLastUpdatedTime());
        assertEquals(expectedContent, journalEntryToCheck.getContent());
    }

}
