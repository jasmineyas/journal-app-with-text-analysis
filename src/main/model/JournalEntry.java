package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * JournalEntry class represents a single journal entry with content and
 * analysis metrics.
 * Each entry tracks creation time, updates, and various text analyses.
 */

public class JournalEntry {
    private LocalDateTime createdTime;
    private LocalDateTime lastUpdatedTime;

    private String content;
    private String preview;
    private String overallMood;
    private String timeOrientation;
    private String primarySense;
    private String usAndThem;
    private Analyzer analyzer;
    // private String mindsetWhileWriting;

    // inspiration: https://750words.com

    // TODO: think about empty content - what to do with it?
    public JournalEntry(String content) {
        this.createdTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.lastUpdatedTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.content = content;
        this.preview = createEntryPreview();
        this.analyzer = new Analyzer();
        analyze();
    }

    // REQUIRES: newContent not be empty
    // MODIFIES: this
    // EFFECTS: update the content,
    // update the lastUpdatedTime
    // update preview
    // re-run analyzer
    public void editContent(String newContent) {
        this.content = newContent;
        this.lastUpdatedTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.preview = createEntryPreview();
        analyze();
    }

    // MODIFIES: this
    // EFFECTS: analyze the content and produce various metrics
    public void analyze() {
        overallMood = analyzer.analyze(this, analyzer.getMoodCategories());
        timeOrientation = analyzer.analyze(this, analyzer.getTimeCategories());
        primarySense = analyzer.analyze(this, analyzer.getSenseCategories());
        usAndThem = analyzer.analyze(this, analyzer.getUsThemCategories());
    }

    // EFFECTS: create entry preview which is the first 10 words
    // of an entry followed by ...
    public String createEntryPreview() {
        ArrayList<String> text = new ArrayList<>(List.of(content.split("\\s+")));
        if (text.size() <= 10) {
            return content;
        }

        StringBuilder preview = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            preview.append(text.get(i));
            if (i < 9) {
                preview.append(" ");
            }
        }

        preview.append("...");

        return preview.toString();
    }

    // EFFECTS: Returns the preview of a journal entry
    public String getEntryPreview() {
        return preview;
    }

    // EFFECTS: Returns the word count of a journal entry
    public int getWordCount() {
        return content.isEmpty() ? 0 : content.split("\\s+").length;
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
    // return mindsetWhileWriting;
    // }

}
