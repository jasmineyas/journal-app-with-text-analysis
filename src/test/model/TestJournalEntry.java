package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestJournalEntry {
    private JournalEntry journalEntry;

    @BeforeEach
    void runBefore() {
        journalEntry = new JournalEntry("Hello, world!");
    }

    @Test
    void testEditContent() throws InterruptedException {
        LocalDateTime beforeEdit = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Thread.sleep(1000);
        journalEntry.editContent("Hello, world! I am editing this content.");
        Thread.sleep(1000);
        LocalDateTime afterEdit = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        assertEquals("Hello, world! I am editing this content.", journalEntry.getContent());
        assertTrue(journalEntry.getLastUpdatedTime().isAfter(beforeEdit));
        assertTrue(journalEntry.getLastUpdatedTime().isBefore(afterEdit)
                || journalEntry.getLastUpdatedTime().isEqual(afterEdit));
    }

    @Test
    void testAnalyzeUpdatesAfterEdit() {
        journalEntry.editContent("I feel happy today!");
        assertEquals("happy", journalEntry.getOverallMood());

        journalEntry.editContent("I am sad and lonely.");
        assertEquals("sad", journalEntry.getOverallMood());
    }

    @Test
    void testGetCreatedTime() throws InterruptedException {
        LocalDateTime beforeCreation = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Thread.sleep(1000);

        journalEntry = new JournalEntry("New entry");
        Thread.sleep(1000);

        LocalDateTime afterCreation = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

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
        assertEquals("happy", journalEntry.getOverallMood());
    }

    @Test
    void testAnalyzeMoodSad() {
        journalEntry.editContent("I am sad today. I am not feeling good.");
        assertEquals("sad", journalEntry.getOverallMood());
    }

    @Test
    void testAnalyzeMoodNeutralS() {
        journalEntry.editContent("I am alright today. I am okay.");
        assertEquals("zen", journalEntry.getOverallMood());
    }

    @Test
    void testAnalyzeTestMoodNA() {
        assertEquals("n/a", journalEntry.getOverallMood());
    }

    @Test
    void testAnlayzeTimeOrientationPast() {
        journalEntry.editContent("Yesterday, I made a mistake and I am still thinking about that.");
        assertEquals("past", journalEntry.getTimeOrientation());
    }

    @Test
    void testAnlayzeTimeOrientationPresent() {
        journalEntry.editContent("Today, I went to the park.");
        assertEquals("present", journalEntry.getTimeOrientation());
    }

    @Test
    void testAnlayzeTimeOrientationFuture() {
        journalEntry.editContent("So looking forward to tomorrow's game!");
        assertEquals("future", journalEntry.getTimeOrientation());
    }

    @Test
    void testAnlayzeTimeOrientationNA() {
        assertEquals("n/a", journalEntry.getTimeOrientation());
    }

    @Test
    void testAnalyzePrimarySenseSightt() {
        journalEntry.editContent("I saw a beautiful sunset today.");
        assertEquals("sight", journalEntry.getPrimarySense());
    }

    @Test
    void testAnalyzePrimarySenseHearing() {
        journalEntry.editContent("I heard a beautiful song today.");
        assertEquals("hearing", journalEntry.getPrimarySense());
    }

    @Test
    void testAnalyzePrimarySenseTaste() {
        journalEntry.editContent("I ate a delicious cake today.");
        assertEquals("taste", journalEntry.getPrimarySense());
    }

    @Test
    void testAnalyzePrimarySenseTouch() {
        journalEntry.editContent("I touched a soft blanket today.");
        assertEquals("touch", journalEntry.getPrimarySense());
    }

    @Test
    void testAnalyzePrimarySenseNA() {
        assertEquals("n/a", journalEntry.getPrimarySense());
    }

    @Test
    void testAnalyzeUsAndThemUs() {
        journalEntry.editContent("We are going to have a great time.");
        assertEquals("us", journalEntry.getUsAndThem());
    }

    @Test
    void testAnalyzeUsAndThemThem() {
        journalEntry.editContent("They were really nice today.");
        assertEquals("them", journalEntry.getUsAndThem());
    }

    @Test
    void testAnalyzeUsandThemI() {
        journalEntry.editContent("I am going to the park today.");
        assertEquals("I", journalEntry.getUsAndThem());
    }

    @Test
    void testAnalyzeUsandThemYou() {
        journalEntry.editContent("You were really nice to me today. Thank you.");
        assertEquals("you", journalEntry.getUsAndThem());
    }

    @Test
    void testAnalyzeUsAndThemNA() {
        assertEquals("n/a", journalEntry.getUsAndThem());
    }

    @Test
    void testGetWordCount() {
        assertEquals(2, journalEntry.getWordCount());
    }

    @Test
    void testGetWordCountEmpty() {
        journalEntry = new JournalEntry("");
        assertEquals(0, journalEntry.getWordCount());
    }
}
