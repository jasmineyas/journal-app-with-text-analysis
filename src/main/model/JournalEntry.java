package model;

import java.util.*;
import java.time.LocalDateTime;

public class JournalEntry {
    private LocalDateTime dateTime;
    private String content;
    private String overallMood;
    private String timeOrientation;
    private String primarySense;
    private String usAndThem;
    private String mindsetWhileWriting;

    // inspiration: https://750words.com

    public JournalEntry(LocalDateTime dateTime, String content) {
        this.dateTime = dateTime;
        this.content = content;
    }

    // REQUIRES: this.content not be null 
    // MODIFIES: this 
    // EFFECTS: edit the content and re-run analyzer 
    public void editContent(){
        
    }

    // Question: should this be calculated on the fly - or should this also be stored? 
    // REQUIRES: this.content not be null
    // MODIFIES: this
    // EFFECTS: analyze the content and produce various metrics
    public void analyze() {
    }

    // EFFECT: get text analysis results 
    public List<String> getTextAnalysis(){

    }

    // REQUIRES: this.content not be null 
    // EFFECTS: return the first 30 words of an entry 
    public String getEntryPreview() { 
        return "first 30 words...";
    }


}
