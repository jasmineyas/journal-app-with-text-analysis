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
     * mood/
     * happy.txt - contains words indicating happiness
     * sad.txt - contains words indicating sadness
     * zen.txt - contains words indicating neutral/calm state
     * primary_sense/
     * sight.txt - visual perception words
     * hearing.txt - auditory perception words
     * touch.txt - tactile perception words
     * taste.txt - taste-related words
     * time_orientation/
     * past.txt - past-tense indicators
     * present.txt - present-tense indicators
     * future.txt - future-tense indicators
     * us_and_them/
     * i.txt - self-referential words
     * us.txt - collective first person words
     * you.txt - second person words
     * them.txt - third person words
     */

    private static final String MOOD_DIR = "src/main/data/mood";
    private static final String SENSE_DIR = "src/main/data/primary_sense";
    private static final String TIME_DIR = "src/main/data/time_orientation";
    private static final String US_THEM_DIR = "src/main/data/us_and_them";

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
    public Analyzer(String testDir1) {
        moodKeywords = loadKeywordsMap(testDir1);
        timeKeywords = new HashMap<>();
        senseKeywords = new HashMap<>();
        usThemKeywords = new HashMap<>();
    }

    // REQUIRES: directoryPath is a valid directory and contains valid keyword files
    // EFFECTS: load keywords from a directory and return a map of keywords where
    // each key is a category and each value is a set of keywords
    public Map<String, Set<String>> loadKeywordsMap(String directoryPath) {
        Map<String, Set<String>> keywordMap = new HashMap<>();

        try {
            Files.list(Paths.get(directoryPath)).forEach(filePath -> {

                String fileName = filePath.getFileName().toString();
                String category = fileName.substring(0, fileName.lastIndexOf('.'));

                try {
                    Set<String> keywords = readKeywordsFromFile(filePath.toString());
                    keywordMap.put(category, keywords);
                } catch (Exception e) {
                    throw new RuntimeException("Error reading file: " + filePath);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keywordMap;
    }

    // REQUIRES: filePath is a valid file path
    // EFFECTS: load keywords from a file and return a set of keywords
    public Set<String> readKeywordsFromFile(String filePath) {
        Set<String> keywords = new HashSet<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    keywords.add(line.trim().toLowerCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keywords;
    }

    // EFFECTS: counts occurences of keywords across all categories in a journal
    // entry
    // and returns a map where each key is a category from keywords map
    // and each value is the count of keywords in the entry
    // matching is case-insensitive
    // if no mathces found, returns map with zero counts
    public Map<String, Integer> countKeywordOccurrences(String content, Map<String, Set<String>> keywords) {
        String[] contentWords = content.toLowerCase().split("\\W+");

        Map<String, Integer> keywordCount = new HashMap<>();

        for (String category : keywords.keySet()) {
            keywordCount.put(category, 0);
        }

        for (Map.Entry<String, Set<String>> entry : keywords.entrySet()) {
            String category = entry.getKey();
            Set<String> categoryKeywords = entry.getValue();

            for (String word : contentWords) {
                if (categoryKeywords.contains(word.toLowerCase())) {
                    keywordCount.put(category, keywordCount.get(category) + 1);
                }
            }
        }

        return keywordCount;
    }

    // EFFECTS: find the dominant category of a journal entry by absolute count
    // return category with the highest count
    // if tie, return the first category
    // if all counts are 0, returns "n/a"
    public String findDominantCategoryByCount(Map<String, Integer> counts, int totalWords) {
        boolean allZeros = true;
        for (int count : counts.values()) {
            if (count > 0) {
                allZeros = false;
                break;
            }
        }
        if (allZeros) {
            return "n/a";
        }

        String dominantCategory = "";
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                dominantCategory = entry.getKey();
            }
        }

        return dominantCategory;
    }

    // EFFECTS: analyze a journal entry and return the dominant
    // category for the metric of interest
    public String analyze(JournalEntry entry, Map<String, Set<String>> keywords) {
        String content = entry.getContent();
        int wordCount = entry.getWordCount();
        Map<String, Integer> counts = countKeywordOccurrences(content, keywords);
        return findDominantCategoryByCount(counts, wordCount);
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
