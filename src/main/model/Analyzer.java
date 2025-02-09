package model;

public class Analyzer {
    Analyzer analyzer;

    // TODO: question - should Analyzer have instructor??  I just want it to be a helper class 
    public Analyzer(JournalEntry entry){

    }

    // TODO: REQUIRE/EFFECT/MODIFY? 
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
    public String calculateMindSetWhileWriting(JournalEntry entry) {
        return "Introvert, positive, uncertain, thinking";
    }
}

