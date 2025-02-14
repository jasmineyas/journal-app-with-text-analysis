package model;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class Analyzer {

    private static final String ALGORITHM_LOG = "data/algorithm_log.txt";
    private static final String MOOD_KEYWORDS = "data/mood_keywords.txt";
    private static final String PRIMARY_KEYWORDS = "data/primary_keywords.txt";
    private static final String TIME_ORIENTATION_KEYWORDS = "data/time_orientation.txt";
    private static final String US_AND_THEM_KEYWORDS = "data/us_and_them_keywords.txt";

    private Set<String> moodKeywords;
    private Set<String> timeOrientationKeywords;
    private Set<String> primaryKeywords;
    private Set<String> usAndThemKeywords;

    public Analyzer() {
        this.moodKeywords = loadKeywordsFromFile(MOOD_KEYWORDS);
        this.timeOrientationKeywords = loadKeywordsFromFile(TIME_ORIENTATION_KEYWORDS);
        this.primaryKeywords = loadKeywordsFromFile(PRIMARY_KEYWORDS);
        this.usAndThemKeywords = loadKeywordsFromFile(US_AND_THEM_KEYWORDS);
    }

    // EFFECTS: return the version of the algorithm
    public int getVersion(){
        return 0;
    }

    // EFFECTS: update alogithm log with the latest VERSION number 
    //         and update the date 
    public void updateAlgorithmLog(){

    }

    // EFFECTS: return the last time the algorithm was updated
    public LocalDateTime getLastAlgorithmUpdateDate(){
        return LocalDateTime.now();
    }

    // EFFECTS: calculate the overall mood stats given a journal entry
    public String calculateOverallMood(JournalEntry entry){
        return "Happy";
    }

    // EFFECTS: calculate the time orientation stats given a journal entry
    public String calculateTimeOrientation(JournalEntry entry) {
        return "Present";
    }

    // EFFECTS: calculate the primary sense stats given a journal entry
    public String calculatePrimarySense(JournalEntry entry) {
        return "Sight";
    }

    // EFFECTS: calculate the us and them stats given a journal entry
    public String calculateUsAndThem(JournalEntry entry) {
        return "Us";
    }

    // EFFECTS: calculate the mindset stats while writing given a journal entry
    public List<String> calculateMindSetWhileWriting(JournalEntry entry) {
        return List.of("Introvert", "positive", "uncertain", "thinking");
    }

    // Helper method to load keywords from a file
    // REQUIRES: the file path is valid
    // EFFECTS: load keywords from a file and return a set of keywords
    private Set<String> loadKeywordsFromFile(String filePath) {
        Set<String> keywords = new HashSet<>();
        return keywords;
    }


    

}

