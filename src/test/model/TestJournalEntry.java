package model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestJournalEntry {
    JournalEntry journalEntry;

    @BeforeEach
    void runBefore() {
        journalEntry = new JournalEntry("Hello, world!");
    }

    @Test
    void editContentTest() {
        journalEntry.editContent("Hello, world! I am editing this content.");
        assertTrue(journalEntry.getContent().equals("Hello, world! I am editing this content."));
    }

    @Test
    void createEntryPreviewTestShort() {
        assertTrue(journalEntry.createEntryPreview().equals("Hello, world!"));
    }

    @Test
    void createEntryPreviewTestLong() {
        journalEntry = new JournalEntry("Hello, world!");
        journalEntry.editContent("Today I am feeling really happy. I will be going to the park with my friends. " +
                "We are going to have a great time. I am a bit worried about the test tomorrow. I hope I will do well.");
        assertTrue(journalEntry.createEntryPreview().equals("Today I am feeling really happy. I will be going..."));
    }

    // ANALYZE TESTS - MAYBE THEY SHOULD SIT IN THE ANALYZE TEST FILE

    // TODO: will need to test for complex for all these tests
    // where most of the words are happy
    // and some are sad, and we will want to return happy
    @Test
    void analyzeTestMoodHappy() {
        journalEntry.editContent("I am happy today. I am feeling good.");
        journalEntry.analyze();
        assertTrue(journalEntry.getOverallMood().equals("Happy"));
    }

    @Test
    void analyzeTestMoodSad() {
        journalEntry.editContent("I am sad today. I am not feeling good.");
        journalEntry.analyze();
        assertTrue(journalEntry.getOverallMood().equals("Sad"));
    }

    @Test
    void analyzeTestMoodNeutralS() {
        journalEntry.editContent("I am alright today. I am okay.");
        journalEntry.analyze();
        assertTrue(journalEntry.getOverallMood().equals("Zen"));
    }

    @Test
    void analyzeTestMoodNA() {
        journalEntry.analyze();
        assertTrue(journalEntry.getOverallMood().equals("N/A"));
    }

    @Test
    void anlayzeTestTimeOrientationPast() {
        journalEntry.editContent("Yesterday, I made a mistake and I am still thinking about that.");
        journalEntry.analyze();
        assertTrue(journalEntry.getTimeOrientation().equals("Past"));
    }

    @Test
    void anlayzeTestTimeOrientationPresent() {
        journalEntry.editContent("Today, I went to the park.");
        journalEntry.analyze();
        assertTrue(journalEntry.getTimeOrientation().equals("Present"));
    }

    @Test
    void anlayzeTestTimeOrientationFuture() {
        journalEntry.editContent("So looking forward to tomorrow's game!");
        journalEntry.analyze();
        assertTrue(journalEntry.getTimeOrientation().equals("Future"));
    }

    @Test
    void anlayzeTestTimeOrientationNA() {
        journalEntry.analyze();
        assertTrue(journalEntry.getTimeOrientation().equals("N/A"));
    }

    @Test
    void analyzeTestPrimarySenseSightt() {
        journalEntry.editContent("I saw a beautiful sunset today.");
        journalEntry.analyze();
        assertTrue(journalEntry.getPrimarySense().equals("Sight"));
    }

    @Test
    void analyzeTestPrimarySenseHearing() {
        journalEntry.editContent("I heard a beautiful song today.");
        journalEntry.analyze();
        assertTrue(journalEntry.getPrimarySense().equals("Hearing"));
    }

    @Test
    void analyzeTestPrimarySenseTaste() {
        journalEntry.editContent("I ate a delicious cake today.");
        journalEntry.analyze();
        assertTrue(journalEntry.getPrimarySense().equals("Taste"));
    }

    @Test
    void analyzeTestPrimarySenseTouch() {
        journalEntry.editContent("I touched a soft blanket today.");
        journalEntry.analyze();
        assertTrue(journalEntry.getPrimarySense().equals("Touch"));
    }

    @Test
    void analyzeTestPrimarySenseNA() {
        journalEntry.analyze();
        assertTrue(journalEntry.getPrimarySense().equals("N/A"));
    }

    @Test
    void analyzeTestUsAndThemUs() {
        journalEntry.editContent("I am going to the park with my friends. We are going to have a great time.");
        journalEntry.analyze();
        assertTrue(journalEntry.getUsAndThem().equals("Us"));
    }

    @Test
    void analyzeTestUsAndThemThem() {
        journalEntry.editContent("My friends were really nice to me today.");
        journalEntry.analyze();
        assertTrue(journalEntry.getUsAndThem().equals("Them"));
    }

    @Test
    void analyzeTestUsandThemI() {
        journalEntry.editContent("I am going to the park today.");
        journalEntry.analyze();
        assertTrue(journalEntry.getUsAndThem().equals("I"));
    }

    @Test
    void analyzeTestUsandThemYou() {
        journalEntry.editContent("You were really nice to me today, babe.");
        journalEntry.analyze();
        assertTrue(journalEntry.getUsAndThem().equals("You"));
    }

    @Test
    void analyzeTestUsAndThemNA() {
        journalEntry.analyze();
        assertTrue(journalEntry.getUsAndThem().equals("N/A"));
    }

}
