package model;

import java.util.*;
import java.time.LocalDateTime;

public class JournalEntry {
    private LocalDateTime createdTime;
    private LocalDateTime lastUpdatedTime;
    private String content;
    private String preview;
    private String overallMood;
    private String timeOrientation;
    private String primarySense;
    private String usAndThem;
    // private String mindsetWhileWriting;

    // inspiration: https://750words.com

    public JournalEntry(String content) {
        this.createdTime = LocalDateTime.now();
        this.content = content;
        this.preview = createEntryPreview();
    }

    // REQUIRES: this.content not be null
    // MODIFIES: this
    // EFFECTS: edit the content, update the lastUpdatedTime 
    //          and re-run analyzer
    public void editContent(String newContent) {

    }

    // REQUIRES: this.content not be null
    // MODIFIES: this
    // EFFECTS: analyze the content and produce various metrics
    public void analyze() {
    }

    // REQUIRES: this.content not be null
    // EFFECTS: create entry preview which is the first 10 words 
    //          of an entry followed by ... 
    public String createEntryPreview() {
        return "Today I am feeling really happy. I will be going...";
    }

    // EFFECTS: Returns the preview of a journal entry
    public String getEntryPreview(){
        return preview;
    }

    // EFFECTS: Returns the created date of a journal entry
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    // EFFECTS: Returns the last updated date of a journal entry
    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    // EFFECTS: Returns the content of a journal entry
    public String getContent() {
        return content;

    }

    // EFFECTS: Returns the overall mood of a journal entry
    public String getOverallMood() {
        return overallMood;
    }

    // EFFECTS: Returns the time orientation of a journal entry
    public String getTimeOrientation() {
        return timeOrientation;
    }

    // EFFECTS: Returns primary sense of a journal entry
    public String getPrimarySense() {
        return primarySense;
    }

    // EFFECTS: Returns us and them of a journal entry
    public String getUsAndThem() {
        return usAndThem;
    }

    // TODO: leaving this method out for now - add it later 
    // // EFFECTS: Returns mindset while writing of a journal entry
    // public String getMindsetWhileWriting() {
    //     return mindsetWhileWriting;
    // }

}
