package com.mundoasorrir.mundoasorrirbackend.DTO.Event;

public class EventDTO {
    private String id;

    private String start;

    private String end;

    private String description;

    private String place;

    private String type;

    public EventDTO(String id, String startDate, String endDate) {
        this.id = id;
        this.start = startDate;
        this.end = endDate;
    }

    public EventDTO(String id, String startDate, String endDate, String description, String place, String type) {
        this.id = id;
        this.start = startDate;
        this.end = endDate;
        this.description = description;
        this.place = place;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String eventId) {
        this.id = eventId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
