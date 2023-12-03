package com.mundoasorrir.mundoasorrirbackend.Domain.Attendance;

import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import jakarta.persistence.*;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long attendanceEventId;
    @Column(unique = true)
    private Date dayAttendance;

    @OneToMany
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
        return userAttendance.contains(user);

    }

    public void markPresent(SystemUser user){
        if(!isUserIn(user)){
            getPresence(user).wasPresent();
        }else{
            return;
        }
    }
    public void markAbsent(SystemUser user){
        if(!isUserIn(user)){
            getPresence(user).wasAbsent();
        }else{
            return;
        }
    }


    private Present getPresence(SystemUser user){
        for(int i = 0; i < this.userAttendance.size();i++){
            if(user.equals(this.userAttendance.get(i).getUser())){
                return this.userAttendance.get(i);
            }
        }
        return null;
    }

    private List<Present> intantiatePresenceList(List<SystemUser> users){
        List<Present> userAttedance = new ArrayList<>();
        for(int i = 0 ; i < users.size();i++){
            userAttedance.add(new Present(users.get(i)));
        }
        return userAttedance;
    }

    public List<SystemUser> getUsersUnmarked(){
        List<SystemUser> usersUnmarked = new ArrayList<>();
        for(int i = 0 ; i < this.userAttendance.size(); i++){
            if(this.userAttendance.get(i).getPresenceStatus().getStatus().equalsIgnoreCase(BasePresenceStatus.UNMARKED.getStatus())){
                usersUnmarked.add(this.userAttendance.get(i).getUser());
            }
        }
        return usersUnmarked;
    }

    public Long getAttendanceEventId() {
        return attendanceEventId;
    }

    public void setAttendanceEventId(Long attendanceEventId) {
        this.attendanceEventId = attendanceEventId;
    }

    public Date getDayAttendance() {
        return dayAttendance;
    }

    public void setDayAttendance(Date dayAttendance) {
        this.dayAttendance = dayAttendance;
    }

    public List<Present> getUserAttendance() {
        return userAttendance;
    }

    public void setUserAttendance(List<Present> userAttendance) {
        this.userAttendance = userAttendance;
    }
}
