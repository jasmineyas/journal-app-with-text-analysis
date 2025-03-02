package persistence;

import org.json.JSONObject;

/**
 * Define a contract for classes (Journal and JournalEntry) that need to be
 * serialized into JSON format. They both must provide an implementaiton for the
 * method contains here.
 * code reference:
 * https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 * /src/main/persistence/Writable.java
 */

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
