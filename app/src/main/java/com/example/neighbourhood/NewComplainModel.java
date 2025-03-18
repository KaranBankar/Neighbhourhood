package com.example.neighbourhood;

public class NewComplainModel {
    private String id;
    private String complaint;
    private String date;
    private String mobile;
    private String name;
    private String status; // "Solved" or "Not Solved"

    // Default constructor required for calls to DataSnapshot.getValue(NewComplainModel.class)
    public NewComplainModel() {
    }

    public NewComplainModel(String id, String complaint, String date, String mobile, String name, String status) {
        this.id = id;
        this.complaint = complaint;
        this.date = date;
        this.mobile = mobile;
        this.name = name;
        this.status = status;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}