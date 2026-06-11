/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.progpoe;

import java.util.Scanner;
import java.util.ArrayList;

public class PROGPOE {

    private static final Login loginSystem = new Login();
    private static final int MAX_MESSAGES = 100; // Array limit cap
    private static final String[] arrMessageID = new String[MAX_MESSAGES];
    private static final String[] arrRecipientCell = new String[MAX_MESSAGES];
    private static final String[] arrMessageBody = new String[MAX_MESSAGES];
    private static final String[] arrMessageHash = new String[MAX_MESSAGES];
    private static final String[] arrMessageStatus = new String[MAX_MESSAGES];
    private static int totalStoredCount = 0;

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
    // USER ENTERS THEIR DETAILS HERE FOR REGISTRATION
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
        input.nextLine(); // Clear buffer cleanly

        int totalMessagesSentCount = 0;
        boolean appRunning = true;

        while (appRunning) {
            System.out.println("\n===== QUICKCHAT MAIN MENU =====");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Log out & Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Select menu feature option: ");

            int menuChoice = input.nextInt();
            input.nextLine(); // Clear numeric buffer

            switch (menuChoice) {
                case 1:
                    if (totalMessagesSentCount >= totalMessagesToSend) {
                        System.out.println("You have reached your target limit for messages.");
                        break;
                    }

                    System.out.print("Enter Message ID: ");
                    String id = input.nextLine();

                    System.out.print("Enter Recipient Cell: ");
                    String cell = input.nextLine();

                    System.out.print("Enter Message: ");
                    String info = input.nextLine();

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

                    // FIXED: Always capture to parallel arrays regardless of specific status string outcome
                    if (totalStoredCount < MAX_MESSAGES) {
                        arrMessageID[totalStoredCount] = id;
                        arrRecipientCell[totalStoredCount] = cell;
                        arrMessageBody[totalStoredCount] = info;
                        arrMessageHash[totalStoredCount] = hashCode;

                        if (actionPanelResult.toLowerCase().contains("sent")) {
                            arrMessageStatus[totalStoredCount] = "Sent";
                            totalMessagesSentCount++;
                        } else if (actionPanelResult.toLowerCase().contains("disregarded")) {
                            arrMessageStatus[totalStoredCount] = "Disregarded";
                        } else {
                            arrMessageStatus[totalStoredCount] = "Stored";
                        }
                        totalStoredCount++;
                    } else {
                        System.out.println("Storage full! Cannot log more entries.");
                    }
                    break;

                case 2:
                    Message loggerTemplate = new Message("", "", "");
                    System.out.println(loggerTemplate.printMessages());
                    break;

                case 3:
                    System.out.println("Logging out of QuickChat... Goodbye!");
                    appRunning = false;
                    break;

                case 4:
                    runStoredMessagesSubMenu(input);
                    break;

                default:
                    System.out.println("Option not considered.. Please try again.");
            }
        }
    }

    // =====================================
    // REPORTS AND SUB-MENU ARRAY INTERFACE
    // =====================================
    public static void runStoredMessagesSubMenu(Scanner input) {
        boolean subRunning = true;
        while (subRunning) {
            System.out.println("\n--- STORED MESSAGES PANEL ---");
            System.out.println("a) Display Sender & Recipient of all stored messages");
            System.out.println("b) Display longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search all messages for a particular recipient");
            System.out.println("e) Delete a message using hash code");
            System.out.println("f) Display full details report");
            System.out.println("g) Return to Main Menu");
            System.out.print("Select sub-feature option: ");

            String choice = input.nextLine().toLowerCase();
            if (choice.isEmpty()) {
                choice = input.nextLine().toLowerCase();
            }

            switch (choice) {
                case "a":
                    if (totalStoredCount == 0) {
                        System.out.println("No records found.");
                        break;
                    }
                    for (int i = 0; i < totalStoredCount; i++) {
                        System.out.println("Log [" + i + "] -> Recipient Cell: " + arrRecipientCell[i]);
                    }
                    break;

                case "b":
                    if (totalStoredCount == 0) {
                        System.out.println("No records found.");
                        break;
                    }
                    int longestIndex = 0;
                    for (int i = 1; i < totalStoredCount; i++) {
                        if (arrMessageBody[i].length() > arrMessageBody[longestIndex].length()) {
                            longestIndex = i;
                        }
                    }
                    System.out.println("Longest Message: " + arrMessageBody[longestIndex]);
                    break;

                case "c":
                    System.out.print("Enter Message ID to search: ");
                    String searchID = input.nextLine();
                    boolean foundID = false;

                    for (int i = 0; i < totalStoredCount; i++) {
                        if (arrMessageID[i] != null && arrMessageID[i].equalsIgnoreCase(searchID)) {
                            System.out.println("\n[Match Found!]");
                            System.out.println("Recipient Cell: " + arrRecipientCell[i]);
                            System.out.println("Message Content: " + arrMessageBody[i]);
                            foundID = true;
                            break;
                        }
                    }
                    if (!foundID) {
                        System.out.println("Message ID not found.");
                    }
                    break;

                case "d":
                    System.out.print("Enter Recipient Cell Number to search: ");
                    String searchCell = input.nextLine();
                    boolean foundCell = false;

                    for (int i = 0; i < totalStoredCount; i++) {
                        if (arrRecipientCell[i] != null && arrRecipientCell[i].equals(searchCell)) {
                            System.out.println("\n-> Message ID: " + arrMessageID[i]);
                            System.out.println("-> Content: " + arrMessageBody[i]);
                            System.out.println("-> Log Status: " + arrMessageStatus[i]);
                            System.out.println("-------------------------------------");
                            foundCell = true;
                        }
                    }
                    if (!foundCell) {
                        System.out.println("No messages matching that recipient cell.");
                    }
                    break;

                case "e":
                    System.out.print("Enter Message Hash to Delete: ");
                    String targetHash = input.nextLine();
                    int deleteIndex = -1;

                    for (int i = 0; i < totalStoredCount; i++) {
                        if (arrMessageHash[i] != null && arrMessageHash[i].equalsIgnoreCase(targetHash)) {
                            deleteIndex = i;
                            break;
                        }
                    }

                    if (deleteIndex != -1) {
                        for (int i = deleteIndex; i < totalStoredCount - 1; i++) {
                            arrMessageID[i] = arrMessageID[i + 1];
                            arrRecipientCell[i] = arrRecipientCell[i + 1];
                            arrMessageBody[i] = arrMessageBody[i + 1];
                            arrMessageHash[i] = arrMessageHash[i + 1];
                            arrMessageStatus[i] = arrMessageStatus[i + 1];
                        }

                        arrMessageID[totalStoredCount - 1] = null;
                        arrRecipientCell[totalStoredCount - 1] = null;
                        arrMessageBody[totalStoredCount - 1] = null;
                        arrMessageHash[totalStoredCount - 1] = null;
                        arrMessageStatus[totalStoredCount - 1] = null;

                        totalStoredCount--;
                        System.out.println("Message entry successfully deleted from logs.");
                    } else {
                        System.out.println("Error: Hash code target not located.");
                    }
                    break;

                case "f":
                    if (totalStoredCount == 0) {
                        System.out.println("No data stored yet.");
                        break;
                    }
                    System.out.println("--- FULL MESSAGES REPORT DUMP ---");
                    for (int i = 0; i < totalStoredCount; i++) {
                        System.out.println("Record #" + (i + 1));
                        System.out.println("ID: " + arrMessageID[i]);
                        System.out.println("Cell: " + arrRecipientCell[i]);
                        System.out.println("Content: " + arrMessageBody[i]);
                        System.out.println("Hash: " + arrMessageHash[i]);
                        System.out.println("Status: " + arrMessageStatus[i]);
                        System.out.println("---------------------------------");
                    }
                    break;

                case "g":
                    subRunning = false;
                    break;

                default:
                    System.out.println("Invalid structural sub-option selection.");
            }
        }
    }
}
