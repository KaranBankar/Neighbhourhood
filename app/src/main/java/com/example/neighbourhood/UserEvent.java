package com.example.neighbourhood;

public class UserEvent {
    private String eventId, name, description, date, time, userName;

    public UserEvent() {
        // Empty constructor required for Firebase
    }

    public UserEvent(String eventId, String name, String description, String date, String time, String userName) {
        this.eventId = eventId;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.userName = userName;
    }

    public String getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUserName() {
        return userName;
    }
}
