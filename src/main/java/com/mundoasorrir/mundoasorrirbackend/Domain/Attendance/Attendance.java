package com.mundoasorrir.mundoasorrirbackend.Domain.Attendance;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            userAttedance.add(new Present(users.get(i),this));
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

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceEventId) {
        this.attendanceId = attendanceEventId;
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
