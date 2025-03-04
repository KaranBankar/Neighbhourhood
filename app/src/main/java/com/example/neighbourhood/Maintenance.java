package com.example.neighbourhood;

public class Maintenance {
    private String name;
    private String date;
    private String amount;
    private String deadline;

    public Maintenance() {
        // Empty constructor required for Firebase
    }

    public Maintenance(String name, String date, String amount, String deadline) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    public String getDeadline() {
        return deadline;
    }
}
