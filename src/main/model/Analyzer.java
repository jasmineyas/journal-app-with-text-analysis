package model;

import java.util.*;
import java.time.LocalDateTime;

public class Analyzer {
    // Analyzer analyzer;
    private static final String ALGORITHM_LOG = "data/algorithm_log.txt";
    private static final String MOOD_KEYWORDS = "data/mood_keywords.txt";
    private static final String PRIMARY_KEYWORDS = "data/primary_keywords.txt";
    private static final String TIME_ORIENTATION_KEYWORDS = "data/time_orientation.txt";
    private static final String US_AND_THEM_KEYWORDS = "data/us_and_them_keywords.txt";

    // TODO: question - should Analyzer have instructor??  I just want it to be a helper class 
    // public Analyzer(JournalEntry entry){

    // }

    public int getVersion(){
        return 0;
    }

    // EFFECT: update alogithm log with the latest VERSION number 
    //         and update the date 
    public void updateAlgorithmLog(){

    }

    public LocalDateTime getLastAlgorithmUpdateDate(){
        return LocalDateTime.now();
    }

    // TODO: REQUIRE/EFFECT/MODIFY? - just effect right? 
    // EFFECT: calculate the overall mood given a journal entry ? 
    public String calculateOverallMood(JournalEntry entry){
        return "Happy";
    }

    public String calculateTimeOrientation(JournalEntry entry) {
        return "Present";
    }

    public String calculatePrimarySense(JournalEntry entry) {
        return "Sight";
    }

    public String calculateUsAndThem(JournalEntry entry) {
        return "Us";
    }

    // TODO: maybe make this into a List<String> 
    public List<String> calculateMindSetWhileWriting(JournalEntry entry) {
        return List.of("Introvert", "positive", "uncertain", "thinking");
    }

    // load keywords below 
    public Set<String> loadMoodKeywords(){
        Set<String> moodKeywords = new HashSet<>();
        return moodKeywords;
    }
    
    public Set<String> loadPTimeOrientationKeywords() {
        Set<String> timeOrientationKeywords = new HashSet<>();
        return timeOrientationKeywords;
    }

    public Set<String> loadPrimaryKeywords() {
        Set<String> primaryKeywords = new HashSet<>();
        return primaryKeywords;
    }

    public Set<String> loadUsAndThemKeywords() {
        Set<String> usAndThemKeywords = new HashSet<>();
        return usAndThemKeywords;
    }


    

}

