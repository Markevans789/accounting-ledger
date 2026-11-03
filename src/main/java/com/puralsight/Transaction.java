package com.puralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private final LocalDate LocalDate = null;
    private LocalTime LocalTime;
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(LocalDate amount, LocalTime date, String vendor,
                       String time, double description) {

        this.time = time;
        this.vendor = vendor;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public LocalTime getTime(){
        return time;
    }

    public String getVendor(){

        return vendor;
    }

    public String getDescription(){
        return description;
    }

    public double getAmount(){
        return amount;
    }

    public LocalDate getDate(){
        return date;
    }
}