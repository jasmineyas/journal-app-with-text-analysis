package persistence;

import model.JournalEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

public class JsonTest {
    protected void checkJournalEntry(JournalEntry journalEntry, LocalDateTime lastUpdatedTime, String content) {
        assertEquals(lastUpdatedTime, journalEntry.getLastUpdatedTime());
        assertEquals(content, journalEntry.getContent());
    }

}
