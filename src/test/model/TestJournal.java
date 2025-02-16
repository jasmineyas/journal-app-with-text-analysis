package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestJournal {
    private Journal journal;
    private JournalEntry entry;
    private JournalEntry entry2;
    private JournalEntry entry3;

    @BeforeEach
    void runBefore() throws InterruptedException {
        journal = new Journal();
        entry = new JournalEntry("Hello, world!");
        Thread.sleep(1000);
        entry2 = new JournalEntry("Hello, new world!");
        Thread.sleep(1000);
        entry3 = new JournalEntry("Today I am feeling really happy."+  
                                "I will be going to the park with my friends. " +
                                "We are going to have a great time. I am a bit"+
                                " worried about the test tomorrow. I hope I will do well.");
    }

    @Test
    void testCreateNewEntryOnce() {
        assertTrue(journal.createNewEntry(entry));
        assertEquals(1, journal.getNumberOfEntries());
    }

    @Test
    void testCreateNewEntryTwice() {
        assertTrue(journal.createNewEntry(entry));
        assertTrue(journal.createNewEntry(entry2));
        assertEquals(2, journal.getNumberOfEntries());

    }

    @Test 
    void testUpdateEntry() {
        journal.createNewEntry(entry);
        journal.updateEntry(entry, "Hello, world! I am editing this content.");
        assertEquals("Hello, world! I am editing this content.", entry.getContent());
    }

    @Test 
    void testDeleteEntryByDateTime() {
        assertTrue(journal.createNewEntry(entry));
        assertTrue(journal.createNewEntry(entry2));
        assertTrue(journal.deleteEntry(entry.getCreatedTime()));
        assertEquals(1, journal.getNumberOfEntries());
    }

    @Test
    void testDeleteEntryByDateTimeString() {
        journal.createNewEntry(entry);
        assertTrue(journal.deleteEntry(journal.formatDateTime(entry.getCreatedTime())));
        assertEquals(0, journal.getNumberOfEntries());
    }

    @Test
    void testDeleteNonExistentEntry() {
        assertFalse(journal.deleteEntry(LocalDateTime.now()));
    }

    @Test
    void testDeleteNonExistentEntryString() {
        assertFalse(journal.deleteEntry(journal.formatDateTime(LocalDateTime.now())));
    }

    @Test
    void testGetEntryByDateTime() {
        journal.createNewEntry(entry);
        assertEquals(entry, journal.getEntry(entry.getCreatedTime()));
    }

    @Test
    void testGetEntryByDateTimeStringTest() {
        journal.createNewEntry(entry);
        String formattedTime = journal.formatDateTime(entry.getCreatedTime());
        System.out.println("Formatted time: " + formattedTime);
        System.out.println("ORiginal: " + entry.getCreatedTime());
        JournalEntry retrievedEntry = journal.getEntry(formattedTime);
        assertEquals(entry, retrievedEntry);
    }

    @Test 
    void testFormatDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 2, 16, 10, 00, 00);
        assertEquals(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), journal.formatDateTime(dateTime));
    }

    @Test 
    void testGetAllEntriesFormatted() {
        journal.createNewEntry(entry);
        journal.createNewEntry(entry2);
        LocalDateTime dateTime1 = entry.getCreatedTime();
        LocalDateTime dateTime2 = entry2.getCreatedTime();
        String dateTime1Formatted = journal.formatDateTime(dateTime1);
        String dateTime2Formatted = journal.formatDateTime(dateTime2);
        String expected = dateTime1Formatted + " - " + entry.getEntryPreview() + "\n" +
                          dateTime2Formatted + " - " + entry2.getEntryPreview() + "\n";
        assertEquals(expected, journal.formatAllEntries());
    }


}
