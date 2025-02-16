package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestJournalEntry {
    private JournalEntry journalEntry;

    @BeforeEach
    void runBefore() {
        journalEntry = new JournalEntry("Hello, world!");
    }

    @Test
    void testDditContent() {
        LocalDateTime beforeEdit = LocalDateTime.now();
        journalEntry.editContent("Hello, world! I am editing this content.");
        LocalDateTime afterEdit = LocalDateTime.now();

        assertEquals("Hello, world! I am editing this content.", journalEntry.getContent());
        assertTrue(journalEntry.getLastUpdatedTime().isAfter(beforeEdit));
        assertTrue(journalEntry.getLastUpdatedTime().isBefore(afterEdit)
                || journalEntry.getLastUpdatedTime().isEqual(afterEdit));
    }

    @Test
    void testAnalyzeUpdatesAfterEdit() {
        journalEntry.editContent("I feel happy today!");
        assertEquals("Happy", journalEntry.getOverallMood());

        journalEntry.editContent("I am sad and lonely.");
        assertEquals("Sad", journalEntry.getOverallMood());
    }

    @Test
    void testGetCreatedTime() {
        LocalDateTime beforeCreation = LocalDateTime.now();
        journalEntry = new JournalEntry("New entry");
        LocalDateTime afterCreation = LocalDateTime.now();

        assertTrue(journalEntry.getCreatedTime().isAfter(beforeCreation));
        assertTrue(journalEntry.getCreatedTime().isBefore(afterCreation)
                || journalEntry.getCreatedTime().isEqual(afterCreation));
    }

    @Test
    void testCreateEntryPreviewTestShort() {
        journalEntry = new JournalEntry("One two three four five six seven eight nine");
        assertTrue(journalEntry.createEntryPreview().equals("One two three four five six seven eight nine"));
    }

    @Test
    void testCreateEntryPreviewTestLong() {
        String longContent = "Today I am feeling really happy. I will be going to the park with my friends. "
                + "We are going to have a great time. I am a bit worried about the test tomorrow. I hope I will do well.";
        journalEntry = new JournalEntry(longContent);
        String preview = journalEntry.getEntryPreview();

        assertEquals("Today I am feeling really happy. I will be going...", preview);
        assertTrue(preview.endsWith("..."));
        assertEquals(10, preview.split("\\s+").length); 

    }

    @Test
    void testCreateEntryPreviewTestVeryLong() {
        String longContent = "This is a very long journal entry that contains more than "
                + "fifty words so we can test how the preview handles long content. We want "
                + "to make sure it properly truncates and adds ellipsis without breaking or "
                + "creating any issues with the preview functionality while maintaining good "
                + "performance and correct behavior in all cases including edge cases.";

        JournalEntry longEntry = new JournalEntry(longContent);
        String preview = longEntry.getEntryPreview();

        assertEquals("This is a very long journal entry that contains more...", preview);
        assertTrue(preview.endsWith("..."));
        assertEquals(10, preview.split("\\s+").length); 
    }

    // ANALYZE TESTS - MAYBE THEY SHOULD SIT IN THE ANALYZE TEST FILE

    // TODO: will need to test for complex for all these tests
    // where most of the words are happy
    // and some are sad, and we will want to return happy
    // should test after a content is updated - analyzer was ran again
    // and the analysis is different

    @Test
    void testAnalyzeMoodHappy() {
        journalEntry.editContent("I am happy today. I am feeling good.");
        journalEntry.analyze();
        assertTrue(journalEntry.getOverallMood().equals("Happy"));
    }

    @Test
    void testAnalyzeMoodSad() {
        journalEntry.editContent("I am sad today. I am not feeling good.");
        journalEntry.analyze();
        assertTrue(journalEntry.getOverallMood().equals("Sad"));
    }

    @Test
    void testAnalyzeMoodNeutralS() {
        journalEntry.editContent("I am alright today. I am okay.");
        journalEntry.analyze();
        assertTrue(journalEntry.getOverallMood().equals("Zen"));
    }

    @Test
    void testAnalyzeTestMoodNA() {
        journalEntry.analyze();
        assertTrue(journalEntry.getOverallMood().equals("N/A"));
    }

    @Test
    void testAnlayzeTimeOrientationPast() {
        journalEntry.editContent("Yesterday, I made a mistake and I am still thinking about that.");
        journalEntry.analyze();
        assertTrue(journalEntry.getTimeOrientation().equals("Past"));
    }

    @Test
    void testAnlayzeTimeOrientationPresent() {
        journalEntry.editContent("Today, I went to the park.");
        journalEntry.analyze();
        assertTrue(journalEntry.getTimeOrientation().equals("Present"));
    }

    @Test
    void testAnlayzeTimeOrientationFuture() {
        journalEntry.editContent("So looking forward to tomorrow's game!");
        journalEntry.analyze();
        assertTrue(journalEntry.getTimeOrientation().equals("Future"));
    }

    @Test
    void testAnlayzeTimeOrientationNA() {
        journalEntry.analyze();
        assertTrue(journalEntry.getTimeOrientation().equals("N/A"));
    }

    @Test
    void testAnalyzePrimarySenseSightt() {
        journalEntry.editContent("I saw a beautiful sunset today.");
        journalEntry.analyze();
        assertTrue(journalEntry.getPrimarySense().equals("Sight"));
    }

    @Test
    void testAnalyzePrimarySenseHearing() {
        journalEntry.editContent("I heard a beautiful song today.");
        journalEntry.analyze();
        assertTrue(journalEntry.getPrimarySense().equals("Hearing"));
    }

    @Test
    void testAnalyzePrimarySenseTaste() {
        journalEntry.editContent("I ate a delicious cake today.");
        journalEntry.analyze();
        assertTrue(journalEntry.getPrimarySense().equals("Taste"));
    }

    @Test
    void testAnalyzePrimarySenseTouch() {
        journalEntry.editContent("I touched a soft blanket today.");
        journalEntry.analyze();
        assertTrue(journalEntry.getPrimarySense().equals("Touch"));
    }

    @Test
    void testAnalyzePrimarySenseNA() {
        journalEntry.analyze();
        assertTrue(journalEntry.getPrimarySense().equals("N/A"));
    }

    @Test
    void testAnalyzeUsAndThemUs() {
        journalEntry.editContent("I am going to the park with my friends. We are going to have a great time.");
        journalEntry.analyze();
        assertTrue(journalEntry.getUsAndThem().equals("Us"));
    }

    @Test
    void testAnalyzeUsAndThemThem() {
        journalEntry.editContent("My friends were really nice to me today.");
        journalEntry.analyze();
        assertTrue(journalEntry.getUsAndThem().equals("Them"));
    }

    @Test
    void testAnalyzeUsandThemI() {
        journalEntry.editContent("I am going to the park today.");
        journalEntry.analyze();
        assertTrue(journalEntry.getUsAndThem().equals("I"));
    }

    @Test
    void testAnalyzeUsandThemYou() {
        journalEntry.editContent("You were really nice to me today, babe.");
        journalEntry.analyze();
        assertTrue(journalEntry.getUsAndThem().equals("You"));
    }

    @Test
    void testAnalyzeUsAndThemNA() {
        journalEntry.analyze();
        assertTrue(journalEntry.getUsAndThem().equals("N/A"));
    }

}
