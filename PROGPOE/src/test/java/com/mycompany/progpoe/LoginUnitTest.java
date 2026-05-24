/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.progpoe;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginUnitTest {

    Login login = new Login();

    // --- assertEquals Tests (String Messages) ---

    @Test
    public void testUsernameCorrectlyFormatted() {
        // Test Data: "kyl_1"
        String expected = "Welcome Kyle, Adams it is great to see you.";
        String actual = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Adams");
        assertEquals(expected, actual);
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        // Test Data: "kyle!!!!!!!"
        String expected = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        String actual = login.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Adams");
        assertEquals(expected, actual);
    }

    @Test
    public void testPasswordComplexitySuccess() {
        // Test Data: "Ch&&sec@ke99!"
        String expected = "Welcome Kyle, Adams it is great to see you.";
        String actual = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Adams");
        assertEquals(expected, actual);
    }

    @Test
    public void testPasswordComplexityFailure() {
        // Test Data: "password"
        String expected = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        String actual = login.registerUser("kyl_1", "password", "+27838968976", "Kyle", "Adams");
        assertEquals(expected, actual);
    }
    // --- Cell Phone Number Unit Tests ---

    @Test
    public void testCellPhoneNumberCorrectlyFormatted() {
        // Test Data from assignment: "+27838968976"
        // The table says the system should return: "Cell number successfully captured."
        String expected = "Welcome Kyle, Adams it is great to see you.";
        
        // We test the specific method in the Login class
        String actual = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Adams");
        
        // Note: If your registerUser returns "Username successfully entered" on success, 
        // you may need to call checkCellPhoneNumber directly or adjust your expected string.
        // For the POE table:
        assertEquals(expected, actual);
    }

    @Test
    public void testCellPhoneNumberIncorrectlyFormatted() {
        // Test Data from assignment: "08966553"
        String expected = "Cell phone number incorrectly formatted or does not contain international code";
        
        String actual = login.registerUser("kyl_1", "Ch&&sec@ke99!", "08966553", "Kyle", "Adams");
        
        assertEquals(expected, actual);
    }
    

    // --- assertTrue / assertFalse Tests (Boolean Logic) ---

    @Test
    public void testLoginSuccessful() {
        // First register the user so the details are stored
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Adams");
        
        // Test the login logic
        boolean result = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(result);
    }

    @Test
    public void testLoginFailed() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Adams");
        
        // Test with wrong password
        boolean result = login.loginUser("kyl_1", "wrongPass");
        assertFalse(result);
    }
}