package model;

import java.util.*;
import java.time.LocalDateTime;

public class JournalEntry {
    private LocalDateTime dateTime;
    private String content;
    private String preview;
    private String overallMood;
    private String timeOrientation;
    private String primarySense;
    private String usAndThem;
    private String mindsetWhileWriting;

    // inspiration: https://750words.com

    public JournalEntry(LocalDateTime dateTime, String content) {
        this.dateTime = dateTime;
        this.content = content;
        this.preview = createEntryPreview();
    }

    // REQUIRES: this.content not be null
    // MODIFIES: this
    // EFFECTS: edit the content and re-run analyzer
    public void editContent() {

    }

    // REQUIRES: this.content not be null
    // MODIFIES: this
    // EFFECTS: analyze the content and produce various metrics
    public void analyze() {
    }

    // EFFECT: get text analysis results for this entry
    public HashMap<String, String> getTextAnalysis() {
        HashMap<String, String> analysisMap = new HashMap<>();
        analysisMap.put("Overall Mood", overallMood);
        analysisMap.put("Mindset while writing", mindsetWhileWriting);
        analysisMap.put("Time Orientation", timeOrientation);
        analysisMap.put("Primary Sense", primarySense);
        analysisMap.put("Us and Them", usAndThem);
        return analysisMap;
    }

    // REQUIRES: this.content not be null
    // EFFECTS: create entry preview which is the first 30 
    //          words of an entry
    public String createEntryPreview() {
        return "first 30 words...";
    }

    // EFFECTS: Returns the preview of a journal entry
    public String getEntryPreview(){
        return preview;
    }

    // EFFECTS: Returns the date of a journal entry
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // EFFECTS: Returns the content of a journal entry
    public String getContent() {
        return content;
    }

}
