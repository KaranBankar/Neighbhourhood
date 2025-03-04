package com.example.neighbourhood;

public class UserMaintenanceModel {
    private String name, date, amount, deadlineDate;

    public UserMaintenanceModel() {
        // Required empty constructor for Firebase
    }

    public UserMaintenanceModel(String name, String date, String amount, String deadlineDate) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.deadlineDate = deadlineDate;
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

    public String getDeadlineDate() {
        return deadlineDate;
    }
}
