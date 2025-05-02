package model;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Event class
 * code reference: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
 */

public class EventTest {
    private Event event;
    private Date date;

    // NOTE: these tests might fail if time at which line (2) below is executed
    // is different from time that line (1) is executed. Lines (1) and (2) must
    // run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        event = new Event("User created a new entry"); // (1)
        date = Calendar.getInstance().getTime(); // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("User created a new entry", event.getDescription());
        // assertEquals(d, e.getDate());
        Date actualDate = event.getDate();
        Date expectedDate = date; 
        double buffer = 1000;
        double difference = Math.abs(actualDate.getTime() - expectedDate.getTime());
        assertTrue(difference <= buffer);
    }

    @Test
    public void testToString() {
        assertEquals(date.toString() + "\n\t" + "User created a new entry", event.toString());
    }
}
