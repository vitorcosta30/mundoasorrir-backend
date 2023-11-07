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

    @ManyToMany
    private List<SystemUser> enrolledUsers;

    private Date startDate;

    private Date endDate;

    public Event(Long eventId, List<SystemUser> enrolledUsers, Date startDate, Date endDate) {
        this.eventId = eventId;
        this.enrolledUsers = enrolledUsers;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event() {
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
}
