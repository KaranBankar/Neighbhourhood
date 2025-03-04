package com.example.neighbourhood;

public class EventModel {
    private String eventId;
    private String name;
    private String date;
    private String time;
    private String description;
    private String imageUrl; // Can be empty

    public EventModel() {
        // Default constructor required for Firebase
    }

    public EventModel(String eventId, String name, String date, String time, String description, String imageUrl) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
