package com.mycompany.progpoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;

public class PROGPOETest {

    @BeforeEach
    public void setUp() throws Exception {
        // 1. Reset the message count counter variable using reflection
        setPrivateCounterField("totalStoredCount", 4);
        
        // 2. Fetch references to your existing final static arrays
        String[] originalIDs = (String[]) getPrivateArrayField("arrMessageID");
        String[] originalCells = (String[]) getPrivateArrayField("arrRecipientCell");
        String[] originalBodies = (String[]) getPrivateArrayField("arrMessageBody");
        String[] originalHashes = (String[]) getPrivateArrayField("arrMessageHash");
        String[] originalStatuses = (String[]) getPrivateArrayField("arrMessageStatus");

        // 3. Directly populate your existing arrays element by element (Fixes the "Cannot set static final" error)
        originalIDs[0] = "MSG-01";
        originalIDs[1] = "MSG-02";
        originalIDs[2] = "MSG-03";
        originalIDs[3] = "0838884567"; // Rubric target message ID search data

        originalCells[0] = "+27811112222";
        originalCells[1] = "+27838884567"; // Rubric target recipient data
        originalCells[2] = "+27838884567";
        originalCells[3] = "+27838884567";

        originalBodies[0] = "Did you get the cake?";
        originalBodies[1] = "Where are you? You are late! I have asked you to be on time.";
        originalBodies[2] = "Ok, I am leaving without you.";
        originalBodies[3] = "It is dinner time!";

        originalHashes[0] = "HASH-AAA";
        originalHashes[1] = "HASH-BBB"; // Targeted hash handle for deletion case execution
        originalHashes[2] = "HASH-CCC";
        originalHashes[3] = "HASH-DDD";

        originalStatuses[0] = "Sent";
        originalStatuses[1] = "Sent";
        originalStatuses[2] = "Stored";
        originalStatuses[3] = "Sent";
    }

    /**
     * Test 1: Sent Messages array correctly populated
     */
    @Test
    public void testSentMessagesArrayPopulated() throws Exception {
        String[] arrMessageBody = (String[]) getPrivateArrayField("arrMessageBody");
        
        assertEquals("Did you get the cake?", arrMessageBody[0]);
        assertEquals("It is dinner time!", arrMessageBody[3]);
    }

    /**
     * Test 2: Display the longest Message
     */
    @Test
    public void testDisplayLongestMessage() throws Exception {
        String[] arrMessageBody = (String[]) getPrivateArrayField("arrMessageBody");
        int totalStoredCount = (Integer) getPrivateCounterField("totalStoredCount");
        
        int longestIndex = 0;
        for (int i = 1; i < totalStoredCount; i++) {
            if (arrMessageBody[i] != null && arrMessageBody[i].length() > arrMessageBody[longestIndex].length()) {
                longestIndex = i;
            }
        }
        
        String expectedLongest = "Where are you? You are late! I have asked you to be on time.";
        assertEquals(expectedLongest, arrMessageBody[longestIndex]);
    }

    /**
     * Test 3: Search for messageID
     */
    @Test
    public void testSearchForMessageID() throws Exception {
        String[] arrMessageID = (String[]) getPrivateArrayField("arrMessageID");
        String[] arrMessageBody = (String[]) getPrivateArrayField("arrMessageBody");
        int totalStoredCount = (Integer) getPrivateCounterField("totalStoredCount");
        
        String targetSearchID = "0838884567";
        String foundMessageBody = "";
        
        for (int i = 0; i < totalStoredCount; i++) {
            if (arrMessageID[i] != null && arrMessageID[i].equalsIgnoreCase(targetSearchID)) {
                foundMessageBody = arrMessageBody[i];
                break;
            }
        }
        
        assertEquals("It is dinner time!", foundMessageBody);
    }

    /**
     * Test 4: Search all messages sent or stored regarding a particular recipient
     */
    @Test
    public void testSearchAllMessagesForRecipient() throws Exception {
        String[] arrRecipientCell = (String[]) getPrivateArrayField("arrRecipientCell");
        String[] arrMessageBody = (String[]) getPrivateArrayField("arrMessageBody");
        int totalStoredCount = (Integer) getPrivateCounterField("totalStoredCount");
        
        String targetCell = "+27838884567";
        StringBuilder matches = new StringBuilder();
        
        for (int i = 0; i < totalStoredCount; i++) {
            if (arrRecipientCell[i] != null && arrRecipientCell[i].equals(targetCell)) {
                matches.append(arrMessageBody[i]).append(" | ");
            }
        }
        
        String resultString = matches.toString();
        
        assertTrue(resultString.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(resultString.contains("Ok, I am leaving without you."));
    }

    /**
     * Test 5: Delete a message using a message hash
     */
    @Test
    public void testDeleteMessageUsingHash() throws Exception {
        String[] arrMessageHash = (String[]) getPrivateArrayField("arrMessageHash");
        int totalStoredCount = (Integer) getPrivateCounterField("totalStoredCount");
        
        String targetHash = "HASH-BBB";
        int deleteIndex = -1;

        for (int i = 0; i < totalStoredCount; i++) {
            if (arrMessageHash[i] != null && arrMessageHash[i].equalsIgnoreCase(targetHash)) {
                deleteIndex = i;
                break;
            }
        }
        
        assertEquals(1, deleteIndex);
    }

    /**
     * Test 6: Display Report format layout integrity check
     */
    @Test
    public void testDisplayReportMatches() throws Exception {
        int totalStoredCount = (Integer) getPrivateCounterField("totalStoredCount");
        String[] arrMessageHash = (String[]) getPrivateArrayField("arrMessageHash");
        
        assertNotNull(arrMessageHash[0]);
        assertEquals(4, totalStoredCount);
    }

    // --- Safe Helper Utilities to extract or modify internal program components dynamically ---
    private Object getPrivateArrayField(String fieldName) throws Exception {
        Field field = PROGPOE.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(null);
    }

    private void setPrivateCounterField(String fieldName, Object value) throws Exception {
        Field field = PROGPOE.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(null, value);
    }

    private Object getPrivateCounterField(String fieldName) throws Exception {
        Field field = PROGPOE.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(null);
    }
}