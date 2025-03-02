package persistence;

import org.json.JSONObject;

// Define a contract for classes (Journal and JournalEntry) that need to be
// serialized into JSON format. They both must provide an implementaiton 
// for the method contains here. 

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
