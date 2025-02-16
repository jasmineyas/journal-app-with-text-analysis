package model;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * Utility class: Analyzer processes journal entries and calculates various
 * metrics based on keywords in the entries from predefined categories.
 */

public class Analyzer {
    /**
     * Directory structure for keyword files:
     * data/
     *   mood/
     *     happy.txt     - contains words indicating happiness
     *     sad.txt       - contains words indicating sadness
     *     zen.txt       - contains words indicating neutral/calm state
     *   primary_sense/
     *     sight.txt     - visual perception words
     *     hearing.txt   - auditory perception words
     *     touch.txt     - tactile perception words
     *     taste.txt     - taste-related words
     *   time_orientation/
     *     past.txt      - past-tense indicators
     *     present.txt   - present-tense indicators
     *     future.txt    - future-tense indicators
     *   us_and_them/
     *     i.txt         - self-referential words
     *     us.txt        - collective first person words
     *     you.txt       - second person words
     *     them.txt      - third person words
     */

    private static final String MOOD_DIR = "data/mood/";
    private static final String SENSE_DIR = "data/primary_sense/";
    private static final String TIME_DIR = "data/time_orientation/";
    private static final String US_THEM_DIR = "data/us_and_them/";

    private Map<String, Set<String>> moodKeywords;
    private Map<String, Set<String>> timeKeywords;
    private Map<String, Set<String>> senseKeywords;
    private Map<String, Set<String>> usThemKeywords;

    // Production constructor
    public Analyzer() {
        moodKeywords = loadKeywordsMap(MOOD_DIR);
        timeKeywords = loadKeywordsMap(TIME_DIR);
        senseKeywords = loadKeywordsMap(SENSE_DIR);
        usThemKeywords = loadKeywordsMap(US_THEM_DIR);
    }

    // Testing contructor: for testing purpose only 
    public Analyzer(String testDir1){
        moodKeywords = loadKeywordsMap(testDir1);
        timeKeywords = new HashMap<>();
        senseKeywords = new HashMap<>();
        usThemKeywords = new HashMap<>();
    }

    // REQUIRES: directoryPath is a valid directory and contains valid keyword files 
    // EFFECTS: load keywords from a directory and return a map of keywords where 
    //          each key is a category and each value is a set of keywords
    // TODO:    if directoryPath is invalid, throws IOException
    public Map<String, Set<String>> loadKeywordsMap(String directoryPath) {
        Map<String, Set<String>> keywordMap = new HashMap<>();
        return keywordMap;
    }

    // REQUIRES: filePath is a valid file path
    // EFFECTS: load keywords from a file and return a set of keywords
    // TODO:   if filePath is invalid, throws IOException
    public Set<String> readKeywordsFromFile(String filePath) {
        Set<String> keywords = new HashSet<>();
        return keywords;
    }

    // EFFECTS: counts occurences of keywords across all categories in a journal entry
    //          and returns a map where each key is a category from keywords map 
    //          and each value is the count of keywords in the entry
    //          matching is case-insensitive
    //          if no mathces found, returns map with zero counts 
    public Map<String, Integer> countKeywordOccurrences(String content, Map<String, Set<String>> keywords) {
        Map<String, Integer> keywordCount = new HashMap<>();
        return keywordCount;
    }

    // EFFECTS: find the dominant category of a journal entry by absolute count
    //          return category with the highest count
    //          if tie, return the first category
    //          if all counts are 0, returns "n/a"  
    public String findDominantCategoryByCount(Map<String, Integer> counts, int totalWords) {
        return "happy";
    }

    public Map<String, Set<String>> getMoodCategories() {
        return moodKeywords;
    }

    public Map<String, Set<String>> getTimeCategories() {
        return timeKeywords;
    }

    public Map<String, Set<String>> getSenseCategories() {
        return senseKeywords;
    }

    public Map<String, Set<String>> getUsThemCategories() {
        return usThemKeywords;
    }
}

// // EFFECTS: calculate the mindset stats while writing given a journal entry
// public List<String> calculateMindSetWhileWriting(JournalEntry entry) {
// return List.of("Introvert", "positive", "uncertain", "thinking");
// }
