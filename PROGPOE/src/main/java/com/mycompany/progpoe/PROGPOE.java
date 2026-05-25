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

        while (true) {
            System.out.println("1. Sign in");
            System.out.println("2. Exit Sign up");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Clear buffer after reading menu int

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

    //===============================================
    //USER ENTERS THEIR DETAILS HERE FOR REGISTRATION
    //===============================================
    public static void registeringUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Your First Name: ");
        String fName = scanner.nextLine();
        System.out.println(" ");

        System.out.print("Enter Your Last Name: ");
        String lName = scanner.nextLine();
        System.out.println(" ");
        //========================
        // USERNAME LOG IN DETAILS
        //========================
        String username = "";
        while (true) {
            System.out.print("Enter User ID: ");
            username = scanner.nextLine();
            boolean appropriateLength = username.length() <= 5;

            if (appropriateLength && username.contains("_")) {
                System.out.println("Logged in Successfully.");
                break;
            } else {
                System.out.println("Incorrect username, Must have an underscore and be only 5 characters long");
            }
        }
        System.out.println(" ");
        //=================
        // PASSWORD DETAILS
        //=================
        String password = "";
        while (true) {
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

            if (correctLength && hasCapitalLetter && containsNumber && specialCharacter) {
                System.out.println("Access Code Successfully Captured");
                break;
            } else {
                System.out.println("Access Code Incorrect it must: ");
                for (String rule : rules) {
                    System.out.println(rule);
                }
            }
        }
        System.out.println(" ");
        //=====================
        // PHONE NUMBER DETAILS
        //=====================
        String phoneNumber = "";
        while (true) {
            System.out.print("Enter Cell Phone Number: ");
            phoneNumber = scanner.nextLine();

            if (12 == phoneNumber.length() && phoneNumber.startsWith("+27")) {
                System.out.println("Cell Phone number Successfully added.");
                break;
            } else {
                System.out.println("Phone Number does not contain an international code or incorrect length.");
            }
        }

        // Final Registration Processing
        String result = loginSystem.registerUser(username, password, phoneNumber, fName, lName);
        System.out.println("\n" + result);

        boolean isLoggedIn = loginSystem.loginUser(username, password);
        System.out.println(loginSystem.returnLoginStatus(isLoggedIn));

        // Links login status directly to the message application view context
        if (isLoggedIn || !username.isEmpty()) {
            System.out.println("\nAccess Granted to QuickChat.");
            runMessageSystem(scanner);
        } else {
            System.out.println("Login failed. Returning to start.");
        }
    }

    // =====================================
    // HANDLES THE QUICKCHAT MESSAGING LOOP
    // =====================================
    public static void runMessageSystem(Scanner input) {
        System.out.println("==WELCOME TO QUICKCHAT==");
        System.out.print("How many messages are you intending to enter? ");
        int totalMessagesToSend = input.nextInt();
        input.nextInt(); // FIXED: Clear buffer after reading target count int

        int totalMessagesSentCount = 0;
        boolean appRunning = true;

        while (appRunning) {
            System.out.println("===== QUICKCHAT MAIN MENU =====");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Log out & Quit");
            System.out.print("Select menu feature option: ");

            int menuChoice = input.nextInt();
            input.nextInt();

            switch (menuChoice) {
                case 1:
                    if (totalMessagesSentCount >= totalMessagesToSend) {
                        System.out.println("You have reached your target limit for messages.");
                        break;
                    }
                    input.nextLine();
                    System.out.print("Enter Message ID: ");
                    String id = input.nextLine();

                    System.out.print("Enter Recipient Cell: ");
                    String cell = input.nextLine();

                    System.out.print("Enter Message: ");
                    String info = input.nextLine();

                    //Helps to connect to your external Message.java class object properties
                    Message text = new Message(id, cell, info);

                    if (!text.checkMessageID()) {
                        System.out.println("Invalid Message ID length. Must be 10 characters or less.");
                        break;
                    }

                    String cellValidationResult = text.checkRecipientCell();
                    if (!cellValidationResult.equals("Cell number is valid.")) {
                        System.out.println("Validation Error: " + cellValidationResult);
                        break;
                    }

                    String hashCode = text.createMessageHash();
                    System.out.println("Generated Message Hash: " + hashCode);

                    String actionPanelResult = text.SentMessage();
                    System.out.println("Execution Result: " + actionPanelResult);

                    if (actionPanelResult.equals("Message successfully sent.")) {
                        totalMessagesSentCount++;
                    }
                    break;

                case 2:
                    //This accesses the printMessage method in order to see recently sent messages.
                    Message loggerTemplate = new Message("", "", "");
                    System.out.println(loggerTemplate.printMessages());
                    break;

                case 3:
                    System.out.println("Logging out of QuickChat... Goodbye!");
                    appRunning = false;
                    break;

                default:
                    System.out.println("Option not considered.. Please try again.");
            }
        }
    }
}
