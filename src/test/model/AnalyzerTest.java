package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Analyzer
 */

public class AnalyzerTest {
    private Analyzer analyzer;
    private static final String TEST_MOOD_DIR = "data/testdata/mood";
    private static final String INVALID_DIR = "src/test/invalid/dir";
    private static final String INVALID_FILE = "src/test/invalid/file.txt";

    private Map<String, Set<String>> testHappyKeywords;
    private Map<String, Set<String>> testSadKeywords;

    @BeforeEach
    void runBefore() {
        analyzer = new Analyzer(TEST_MOOD_DIR);
        testHappyKeywords = new HashMap<>();
        testHappyKeywords.put("happy", new HashSet<>(Arrays.asList("happy", "excited", "joy")));
        testSadKeywords = new HashMap<>();
        testSadKeywords.put("sad", new HashSet<>(Arrays.asList("sad", "lonely", "depressed")));
    }

    @Test
    void testLoadKeywordsMapInvalidDirectory() {
        Map<String, Set<String>> result = analyzer.loadKeywordsMap(INVALID_DIR);
        assertTrue(result.isEmpty());
    }

    @Test
    void testReadKeywordsFileInvalidFile() {
        Set<String> result = analyzer.readKeywordsFromFile(INVALID_FILE);
        assertTrue(result.isEmpty());
    }

    @Test
    void testLoadKeywordsMap() {
        Map<String, Set<String>> moodKeywords = analyzer.getMoodCategories();
        assertEquals(2, moodKeywords.size());

        assertTrue(moodKeywords.containsKey("happy"));
        assertTrue(moodKeywords.get("happy").contains("joy"));
        assertTrue(moodKeywords.get("happy").contains("excited"));
        assertTrue(moodKeywords.get("happy").contains("happy"));

        assertTrue(moodKeywords.containsKey("sad"));
        assertTrue(moodKeywords.get("sad").contains("sad"));
        assertTrue(moodKeywords.get("sad").contains("lonely"));
        assertTrue(moodKeywords.get("sad").contains("depressed"));
    }

    @Test
    void testReadKeywordsFromFile() {
        Set<String> keywords = analyzer.readKeywordsFromFile(TEST_MOOD_DIR + "/happy.txt");
        assertEquals(3, keywords.size());
        assertTrue(keywords.contains("joy"));
        assertTrue(keywords.contains("excited"));
        assertTrue(keywords.contains("happy"));
    }

    @Test
    void testCountKeywordOccurrences() {
        Map<String, Integer> keywordCount = analyzer.countKeywordOccurrences("I am so happy and excited today!",
                testHappyKeywords);
        assertEquals(2, keywordCount.get("happy"));
    }

    @Test
    void testCountKeywordOccurrencesNoMatch() {
        Map<String, Integer> keywordCount = analyzer.countKeywordOccurrences("I am so sad and lonely today!",
                testHappyKeywords);
        assertEquals(0, keywordCount.get("happy"));
    }

    @Test
    void testCountKeywordCaseInsensitive() {
        Map<String, Integer> counts = analyzer.countKeywordOccurrences(
                "I am HAPPY and feeling joy, so Excited!", testHappyKeywords);
        assertEquals(3, counts.get("happy"));
    }

    @Test
    void testFindDominantCategoryByCount() {
        Map<String, Integer> keywordCount = new HashMap<>();
        keywordCount.put("happy", 3);
        keywordCount.put("sad", 1);
        assertEquals("happy", analyzer.findDominantCategoryByCount(keywordCount, 4));
    }

    @Test
    void testFindDominantCategoryByCountTie() {
        Map<String, Integer> keywordCount = new HashMap<>();
        keywordCount.put("happy", 2);
        keywordCount.put("sad", 2);
        assertEquals("happy", analyzer.findDominantCategoryByCount(keywordCount, 4));
    }

    @Test
    void testFindDominantCategoryByCountNoMatch() {
        Map<String, Integer> keywordCount = new HashMap<>();
        keywordCount.put("happy", 0);
        keywordCount.put("sad", 0);
        assertEquals("n/a", analyzer.findDominantCategoryByCount(keywordCount, 0));
    }

    @Test
    void testProcessKeywords() {
        List<String> testLines = List.of(
                "happy",
                "   ",
                "",
                "  cheerful  ",
                "\t",
                "excited");

        Set<String> keywords = analyzer.processKeywords(testLines);

        assertEquals(3, keywords.size());
        assertTrue(keywords.contains("happy"));
        assertTrue(keywords.contains("cheerful"));
        assertTrue(keywords.contains("excited"));
    }

}
