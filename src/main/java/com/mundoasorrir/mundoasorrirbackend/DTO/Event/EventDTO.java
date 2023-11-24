package com.mundoasorrir.mundoasorrirbackend.DTO.Event;

import java.util.Date;

public class EventDTO {
    private String eventId;

    private String startDate;

    private String endDate;

    private String description;

    private String place;

    public EventDTO(String eventId, String startDate, String endDate) {
        this.eventId = eventId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EventDTO(String eventId, String startDate, String endDate, String description, String place) {
        this.eventId = eventId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.place = place;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
}
