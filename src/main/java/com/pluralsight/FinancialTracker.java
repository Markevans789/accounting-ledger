package com.pluralsight;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class FinancialTracker {

    /* ------------------------------------------------------------------
       Shared data and formatters
       ------------------------------------------------------------------ */
    private static final ArrayList<Transaction> transactions = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATETIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern(TIME_PATTERN);
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
    private static Instant date;
    private static boolean t;
    private static LocalDate prviousMonth;

    /* ------------------------------------------------------------------
       Main menu
       ------------------------------------------------------------------ */
    public static void main(String[] args) throws IOException {
        loadTransactions(FILE_NAME);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D" -> addDeposit(scanner);
                case "P" -> addPayment(scanner);
                case "L" -> ledgerMenu(scanner);
                case "X" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
        scanner.close();
    }

    /* ------------------------------------------------------------------
       File I/O
       ------------------------------------------------------------------ */

    /**
     * Load transactions from FILE_NAME.
     * • If the file doesn’t exist, create an empty one so that future writes succeed.
     * • Each line looks like: date|time|description|vendor|amount
     */
    public static void loadTransactions(String fileName) throws IOException {
        File file = new File(fileName);

        // Create file if it does not exist
        if (!file.exists()) {
            file.createNewFile();
        }

        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();

            String[] parts = line.split("\\|");

            if (parts.length == 5) {
                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transaction transaction =
                        new Transaction(date, time, description, vendor, amount);

                transactions.add(transaction);
            }
        }

        fileScanner.close();
    }

    /* ------------------------------------------------------------------
       Add new transactions
       ------------------------------------------------------------------ */

    /**
     * Prompt for ONE date+time string in the format
     * "yyyy-MM-dd HH:mm:ss", plus description, vendor, amount.
     * Validate that the amount entered is positive.
     * Store the amount as-is (positive) and append to the file.
     */
    private static void addDeposit(Scanner scanner) {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        System.out.println("Deposit added successfully!");    }

    private static void addPayment(Scanner scanner) {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
    }

    /* ------------------------------------------------------------------
       Ledger menu
       ------------------------------------------------------------------ */
    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A" -> displayLedger();
                case "D" -> displayDeposits();
                case "P" -> displayPayments();
                case "R" -> reportsMenu(scanner);
                case "H" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    /* ------------------------------------------------------------------
       Display helpers: show data in neat columns
       ------------------------------------------------------------------ */
    private static void displayLedger() { System.out.println("=== Ledger ===");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            System.out.println(transactions.get(i));
        }
        Transaction t = null;
        System.out.println(
                t.getDate() + "\t" +
                        t.getTime() + "\t" +
                        t.getDescription() + "\t" +
                        t.getVendor() + "\t" +
                        t.getAmount()
        );
    }

    private static void displayDeposits() {  System.out.println("=== Deposits ===");

        for (int i = transactions.size() - 1; i >= 0; i--) {

            Transaction t = transactions.get(i);

            if (t.getAmount() > 0) {
                System.out.println(t);
            }
        }
    }

    private static void displayPayments() {
        System.out.println("=== Payments ===");

        for (int i = transactions.size() - 1; i >= 0; i--) {

            Transaction t = transactions.get(i);

            if (t.getAmount() < 0) {
                System.out.println(t);
            }

    }
}

    /* ------------------------------------------------------------------
       Reports menu
       ------------------------------------------------------------------ */
    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> {displayMonthtoDate();}
                case "2" -> {displayPreviousMonth();}
                case "3" -> {displayYearToDate();}
                case "4" -> {displayPreviousYear();}
                case "5" -> {displayVendorReport(scanner);}
                case "6" -> customSearch(scanner);
                case "0" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    private static void displayPreviousMonth() {
        Object previousMonth = null;
        LocalDate start = LocalDate.of(prviousMonth.getYear(), previousMonth.getMonthValue, 1);
        
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        
        filterTransactionsByDate(start,end);
    }

    private static void displayVendorReport(Scanner scanner) {
        System.out.print("Enter vendor name: ");
        String vendor = scanner.nextLine();

        filterTransactionsByVendor(vendor);
    }

    private static void displayPreviousYear() {
        LocalDate start = LocalDate.of(LocalDate.now().getYear() - 1, 1, 1);

        LocalDate end = LocalDate.of(LocalDate.now().getYear() - 1, 12, 31);

        filterTransactionsByDate(start, end);
    }

    private static void displayYearToDate() {
        LocalDate start = LocalDate.of(
                LocalDate.now().getYear(), 1, 1);

        LocalDate end = LocalDate.now();

        filterTransactionsByDate(start, end);
    }

    private static void displayMonthtoDate() {
        LocalDate previousMonth = LocalDate.now().minusMonths(1);

        LocalDate start = LocalDate.of(
                previousMonth.getYear(),
                previousMonth.getMonthValue(),
                1);

        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        filterTransactionsByDate(start, end);
    }


    /* ------------------------------------------------------------------
       Reporting helpers
       ------------------------------------------------------------------ */
    private static void filterTransactionsByDate(LocalDate start, LocalDate end) {
        for (Transaction t : transactions){
            String date = t.getDate();
            String localDate = t.getDate();
        }
        if (date.isBefore(Instant.from(start)) & date.isAfter(Instant.from(end))){
            System.out.println(t);
        }

    }

    private static void filterTransactionsByVendor(String vendor) {
        for (Transaction t : transactions){
            if (t.getVendor().equalsIgnoreCase(vendor)){
                System.out.println(t);
            }
        }
    }

    private static void customSearch(Scanner scanner) {
        System.out.print("Vendor (leave black to skip): ");
        String vendor = scanner.nextLine();

        System.out.print("Description (leave black to skip):");

        String description = scanner.nextLine();

        System.out.print("Amount (leave blank)");
        String amountInput = scanner.nextLine();

        Double amount = null;
        if(!amountInput.isBlank()) {
            amount = Double.parseDouble(amountInput);
        }
        
    }

    /* ------------------------------------------------------------------
       Utility parsers (you can reuse in many places)
       ------------------------------------------------------------------ */
    private static LocalDate parseDate(String s) {
       return LocalDate.parse(s);
    }

    private static Double parseDouble(String s) {
        return Double.parseDouble(s);
    }
}
