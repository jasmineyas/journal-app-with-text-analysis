package model;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;

    // NOTE: these tests might fail if time at which line (2) below is executed
    // is different from time that line (1) is executed. Lines (1) and (2) must
    // run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("User created a new entry"); // (1)
        d = Calendar.getInstance().getTime(); // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("User created a new entry", e.getDescription());
        // assertEquals(d, e.getDate());
        Date actualDate = e.getDate();
        Date expectedDate = d; 
        double buffer = 1000;
        double difference = Math.abs(actualDate.getTime() - expectedDate.getTime());
        assertTrue(difference <= buffer);
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "User created a new entry", e.toString());
    }
}
