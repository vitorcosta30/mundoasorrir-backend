package com.mundoasorrir.mundoasorrirbackend.Domain.Attendance;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long attendanceId;
    @Column(unique = true)
    private Date dayAttendance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attendance")
    private List<Present> userAttendance;


    public Attendance() {
    }


    public Attendance(Date dayAttendance) {
        this.dayAttendance = dayAttendance;
        this.userAttendance = new ArrayList<>();
    }

    public Attendance(Date dayAttendance, List<SystemUser> users) {
        this.dayAttendance = dayAttendance;
        this.userAttendance = intantiatePresenceList(users);
    }

    public boolean isUserIn(SystemUser user){
        for (Present present : this.userAttendance) {
            if (user.equals(present.getUser())) {
                return true;
            }
        }
        return false;
    }

    public void markPresent(SystemUser user){
        if(isUserIn(user)){
            getPresence(user).wasPresent();
        }
    }
    public void markAbsent(SystemUser user){
        if(isUserIn(user)){
            getPresence(user).wasAbsent();
        }
    }
    private Present getPresence(SystemUser user){
        for (Present present : this.userAttendance) {
            if (user.equals(present.getUser())) {
                return present;
            }
        }
        return null;
    }

    private List<Present> intantiatePresenceList(List<SystemUser> users){
        List<Present> userAttedance = new ArrayList<>();
        for (SystemUser user : users) {
            userAttedance.add(new Present(user, this));
        }
        return userAttedance;
    }

    public List<SystemUser> getUsersUnmarked(){
        List<SystemUser> usersUnmarked = new ArrayList<>();
        for (Present present : this.userAttendance) {
            if (present.getPresenceStatus().getStatus().equalsIgnoreCase(BasePresenceStatus.UNMARKED.getStatus())) {
                usersUnmarked.add(present.getUser());
            }
        }
        return usersUnmarked;
    }

    public void setAttendanceId(Long attendanceEventId) {
        this.attendanceId = attendanceEventId;
    }

    public void setDayAttendance(Date dayAttendance) {
        this.dayAttendance = dayAttendance;
    }

    public void setUserAttendance(List<Present> userAttendance) {
        this.userAttendance = userAttendance;
    }
}
