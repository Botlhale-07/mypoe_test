/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progpoe;

import java.util.Scanner;
import java.util.ArrayList;

public class Message {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("--Welcome To Quickchat--");
        System.out.print("How many messages are you intending to send?");
        int totalMessagesToSend = input.nextInt();
        input.nextInt();

        //Menu Selection for Messaging or Quitting the program
        //This is where we call the methods we have created down below 
    }
    private final String messageID;
    private final String recipientCell;
    private final String messageInfo;
    public static int totalMessagesSentCount = 0;
    private static final ArrayList<String> sentMessagesLog = new ArrayList<>();

    public Message(String messageID, String recipientCell, String messageInfo) { //Message is a method that utilises the private variables that we have declared above and giving them assignments
        this.messageID = messageID;
        this.recipientCell = recipientCell;
        this.messageInfo = messageInfo;
    }

    //================================
    //CHECKS MESSAGE ID AND ITS RULES
    //================================
    public boolean checkMessageID() {//Method checks if the ID is correct length and is never empty by using a boolean strategy.
        return this.messageID != null && this.messageID.length() <= 10;

    }

    //===================================
    //CHECKS RECIPIENT CELL AND ITS RULES
    //===================================
    public String checkRecipientCell() { //Method implements the validations needed for the perfect cell number.
        if (this.recipientCell != null && 12 == this.recipientCell.length() && this.recipientCell.startsWith("+27")) {
            return "Cell number is valid.";
        }
        return "Cell number is must contain international code and 10 characters long.";
    }

    //====================================
    //CHECKS THE LENGTH OF THE MESSAGE HAS
    //====================================
    public String checkMessageLength() { //Method checks if the Message length is always below the 250 characters limit.
        if (this.messageInfo == null || this.messageInfo.length() > 250) {
            System.out.println("Please enter a message of less than 250 characters.");
        }
        return "Message Sent.";
    }

    //=======================================================================
    //CREATES A MESSAGE HASH THAT IS USED FOR TRACKING RECENTLY SENT MESSAGES.
    //=======================================================================
    public String createMessageHash() {//This method creates the messageHash that puts together the number of messages sent, the message and the first 2 numbers of the messageID
        if (this.messageID == null || this.messageInfo == null || this.messageID.length() < 2) {
            return "INVALID-HASH";
        }
        String idPart = this.messageID.substring(0, 2);
        int messageLength = this.messageInfo.length();
        String[] words = this.messageInfo.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String rawHash = idPart + ":" + messageLength + ":" + firstWord + lastWord;//This is where they are put together.
        return rawHash.toUpperCase();//The message is returned as an uppercase.
    }

    //=======================================
    //MENU FOR SELECTION OF WHERE MESSAGES GO
    //=======================================
    public String SentMessage() {//This method is used send the message that the user has put in.
        Scanner input = new Scanner(System.in);
        System.out.println("Message Action Panel Menu");
        System.out.println("1. Send message");
        System.out.println("2. Store message");
        System.out.println("3. Disregard message");
        System.out.print("Select an option: ");
        int choice = input.nextInt();

        switch (choice) {
            case 1://Option 1 puts the details together
                String lengthCheck = checkMessageLength();
                if (lengthCheck.startsWith("Please")) {
                    return lengthCheck;
                }
                totalMessagesSentCount++;
                String sentDetails = "ID: " + messageID + "|Hash: " + createMessageHash() + "| Recipient: " + recipientCell + "| Msg: " + messageInfo;//The messageHash is called and the details are put together

                sentMessagesLog.add(sentDetails);
                System.out.println(" " + sentDetails);
                return "Message successfully sent.";//If everything is perfect then this message will be shown at the end.

            case 2:
                return storeMessage();//This option stores the message but it will be made soon.

            case 3:
                return "Message diregarded";//If you have diregarded your message this will appear.

            default:
                return "Choice not considered. No choice was selected.";//If no option was chosen then this will appear.

        }

    }

    //=================================
    //PRINTS OUT RECENTLY SENT MESSAGES
    //=================================
    public String printMessages() {
        if (sentMessagesLog.isEmpty()) {
            return "No messages have been sent.";
        }
        StringBuilder sb = new StringBuilder("---Sent Messages Log---");
        for (String msg : sentMessagesLog) {
            sb.append(msg).append(" ");
        }
        return sb.toString();
    }

    //===================================
    //RETURNS NUMBER OF MESSAGES SENT
    //===================================
    public int returnTotalMessages() {      //This method returns the total number of messsages that the user specified when asked and messages that have been sent.
        return totalMessagesSentCount;
    }

    //===================================
    //STORES MESSAGES FOR USE LATER ON
    //===================================
    public String storeMessage() {
        String jsonFormat = "{" + "messageID: " + this.messageID + "," + "recipientCell: " + this.recipientCell + "," + "content: " + this.messageInfo + "}";

        System.out.println("Storing to JSON format: ");
        System.out.println(jsonFormat);
        return "Message successfully stored in JSON format.";

    }

}
