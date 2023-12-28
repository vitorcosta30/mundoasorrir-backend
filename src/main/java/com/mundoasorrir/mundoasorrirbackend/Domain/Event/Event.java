package com.mundoasorrir.mundoasorrirbackend.Domain.Event;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long eventId;

    @ManyToMany()
    private List<SystemUser> enrolledUsers;

    private String place;

    private String description;

    private Date startDate;

    private Date endDate;
    @ManyToOne
    private EventType eventType;

    public Event( List<SystemUser> enrolledUsers, Date startDate, Date endDate) {
        this.enrolledUsers = enrolledUsers;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event() {
    }

    public Event(List<SystemUser> enrolledUsers, Date startDate, Date endDate, EventType eventType) {
        this.enrolledUsers = enrolledUsers;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventType = eventType;
    }

    public Event(List<SystemUser> enrolledUsers, String place, String description, Date startDate, Date endDate) {
        this.enrolledUsers = enrolledUsers;
        this.place = place;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventType = eventType;
    }


    public boolean isUserEnrolled(SystemUser user){
        return enrolledUsers.contains(user);

    }


    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public List<SystemUser> getEnrolledUsers() {
        return enrolledUsers;
    }

    public void setEnrolledUsers(List<SystemUser> enrolledUsers) {
        this.enrolledUsers = enrolledUsers;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public boolean busyForTheDay(){
        return this.eventType.equals(BaseEventType.VACATION);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVacation(){
        return this.eventType.getName().equals(BaseEventType.VACATION.getName());
    }
}
