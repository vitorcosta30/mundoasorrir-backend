package com.mundoasorrir.mundoasorrirbackend.Domain.Event;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long eventId;

    @ManyToMany
    private List<SystemUser> enrolledUsers;

    public boolean isUserEnrolled(SystemUser user){
        return enrolledUsers.contains(user);

    }


}
