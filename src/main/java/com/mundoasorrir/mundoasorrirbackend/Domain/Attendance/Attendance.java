package com.mundoasorrir.mundoasorrirbackend.Domain.Attendance;

import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import jakarta.persistence.*;
import org.springframework.data.util.Pair;

import java.util.List;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long attendanceEventId;

    @OneToOne
    private Event event;

    @OneToMany
    private List<Present> userAttendance;


    public Attendance() {
    }


}
