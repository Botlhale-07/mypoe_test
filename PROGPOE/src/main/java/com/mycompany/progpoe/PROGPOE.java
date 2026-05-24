/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.progpoe;

import java.util.Scanner;
import java.util.ArrayList;

public class PROGPOE {
    private static Login loginSystem = new Login();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== CHAT APP ===");
//LOG IN REGISTRATION            
        while (true) {
            System.out.println("1. Sign in");
            System.out.println("2. Exit Sign up");
            System.out.println("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextInt();

            switch (option) {
                case 1:
                    registeringUser();
                    break;

                case 2:
                    System.out.println("Exiting Program....Thank you");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Option not considered.");
            }
        }

    }

    public static void registeringUser() {
        Scanner scanner = new Scanner(System.in);
    //This asks the user's First and Last Names    
        System.out.print("Enter Your First Name: ");
        String fName = scanner.nextLine();
        System.out.println(" ");
        
        System.out.print("Enter Your Last Name: ");
        String lName = scanner.nextLine();
        
       System.out.println(" ");
            
//=== USERNAME LOG IN DETAILS ===
        String username = "";
        while (true) {
            //Asks the user for their username input 
            //Enters a loop if username is wrong
            System.out.print("Enter User ID: ");
            username = scanner.nextLine();
            //Tests if it is the specified length 
            boolean appropriateLength = username.length() <= 5;
            
            //This gives out the output and tests the size and if it has an underscore
            if (appropriateLength == true && username.contains("_")) {
                System.out.println("Logged in Successfully.");
                break;
            } else {
                System.out.println("Incorrect username, Must have an underscore and be only 5 characters long");
            }
        }

        System.out.println(" ");

//=== PASSWORD DETAILS ===
            String password = "";
            while (true) {
            //Asks the user for their password input 
            //Enters a loop if password is wrong
            ArrayList<String> rules = new ArrayList<>();
            rules.add("Contain at least 8 characters long");
            rules.add("Contain a Capital letter");
            rules.add("Contain a number");
            rules.add("Contain a special character");
            System.out.print("Enter Your Access Code: ");
            password = scanner.nextLine();
            
            boolean correctLength = password.length() >= 8;
            boolean hasCapitalLetter = false;
            boolean containsNumber = false;
            boolean specialCharacter = false;

            for (int p = 0; p < password.length(); p++) {
                char ac = password.charAt(p);

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
            if (correctLength == true && hasCapitalLetter == true && containsNumber == true && specialCharacter == true) {
                System.out.println("Access Code Successfully Captured");
                break;
            } else {
                System.out.println("Access Code Incorrect it must: ");
                for (int i = 0; i < rules.size(); i++) {
                    System.out.println(rules.get(i));
                }
            }
        }

        System.out.println(" ");

//=== PHONE NUMBER DETAILS ===
        String phoneNumber = "";
        while (true) {
            //Asks the user for their phone number input 
            //Enters a loop if phone number is wrong
            System.out.print("Enter Cell Phone Number: ");
            phoneNumber = scanner.nextLine();
            
            if (12 == phoneNumber.length() && phoneNumber.startsWith("+27")) {
                System.out.println("Cell Phone number Successfully added.");
                break;
            } else {
                System.out.println("Phone Number does not contain an international code or incorrect length.");
            }
        }
        //Final Login
        String result = loginSystem.registerUser(username, password, phoneNumber, fName, lName);
        System.out.println("\n"+ result);
        
        boolean isLoggedIn = loginSystem.loginUser(username, password);
        System.out.println(loginSystem.returnLoginStatus(isLoggedIn));
    }
}
