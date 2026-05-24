/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progpoe;

public class Login {

    public static void main(String[] args) {

    }
    //These contains the stored details of the user for authentication!
    private String registeredUsername;
    private String registeredPassword;
    private String lastName;
    private String firstName;
    private String phoneNumber;

    public Boolean checkUserName(String username) {
        //If the condition is met (contains "_", AND length is <=5) then it will return True

        return username.contains("_") && username.length() <= 5;
    }

    public Boolean checkPasswordComplexity(String accessCode) {
        //Checks the condition of the password (8 characters long,
        //has a capital letter, has a number, special character) and returns
        //true if the condition is met.
        boolean correctLength = accessCode.length() >= 8;
        boolean hasCapitalLetter = false;
        boolean containsNumber = false;
        boolean specialCharacter = false;

        for (int p = 0; p < accessCode.length(); p++) {
            char ac = accessCode.charAt(p);

            if (Character.isUpperCase(ac)) {
                hasCapitalLetter = true;
            }
            if (Character.isDigit(ac)) {
                containsNumber = true;
            }
            if (!Character.isLetterOrDigit(ac)) {
                specialCharacter = true;
            }
        }
        return hasCapitalLetter && containsNumber && specialCharacter && correctLength;
    }

    public Boolean checkCellPhoneNumber(String phoneNumber) {
        //Checks if the number contains an international code and has 
        //appropriate length.
        return 12 == phoneNumber.length() && phoneNumber.startsWith("+27");
    }

    public String registerUser(String username, String password, String cell, String fName, String lName) {
        //Calling the first boolean method!
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        } //Calling the second boolean method!
        else if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        } //Checking if they both passed the conditions above.   
        else if (!checkCellPhoneNumber(cell)) {
            return "Cell phone number incorrectly formatted or does not contain international code";
        } else {
            this.registeredUsername = username;
            this.registeredPassword = password;
            this.firstName = fName;
            this.lastName = lName;
            this.phoneNumber = phoneNumber;
            return null;
        }
    }

    public boolean loginUser(String username, String password) {
        // Verifies that entered details match stored registration details
        return username.equals(this.registeredUsername) && password.equals(this.registeredPassword);
    }

    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
