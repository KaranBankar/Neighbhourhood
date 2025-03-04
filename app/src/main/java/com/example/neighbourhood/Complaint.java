package com.example.neighbourhood;

public class Complaint {
    private String id;
    private String name;
    private String mobile;
    private String date;
    private String complaint;

    public Complaint() {
        // Default constructor required for Firebase
    }

    public Complaint(String id, String name, String mobile, String date, String complaint) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.date = date;
        this.complaint = complaint;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getMobile() { return mobile; }
    public String getDate() { return date; }
    public String getComplaint() { return complaint; }
}

