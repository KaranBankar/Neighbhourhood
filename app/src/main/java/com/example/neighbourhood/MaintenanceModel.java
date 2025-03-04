package com.example.neighbourhood;

public class MaintenanceModel {
    String id, name, date, amount, deadlineDate;

    public MaintenanceModel() {
    }

    public MaintenanceModel(String id, String name, String date, String amount, String deadlineDate) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.deadlineDate = deadlineDate;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getAmount() { return amount; }
    public String getDeadlineDate() { return deadlineDate; }
}
