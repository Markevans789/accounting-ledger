package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;


    public Transaction(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public Transaction(double amount, LocalDate date, String vendor, LocalTime time, String description) {
    }

    public Transaction(double amount, String dateTime, String vendor, String description) {
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    public static void displayMonthToDate() {
        System.out.println("Month-to-Date Report");
    }

    private static void displayPreviousMonth() {
        System.out.println("Previous Month Report");
    }

}