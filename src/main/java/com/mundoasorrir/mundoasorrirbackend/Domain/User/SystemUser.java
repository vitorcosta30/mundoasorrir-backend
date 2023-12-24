package com.mundoasorrir.mundoasorrirbackend.Domain.User;

import com.mundoasorrir.mundoasorrirbackend.Domain.Attendance.Present;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.BaseEventType;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import com.mundoasorrir.mundoasorrirbackend.Domain.File.File;
import com.mundoasorrir.mundoasorrirbackend.Domain.Project.Project;
import com.mundoasorrir.mundoasorrirbackend.Domain.RefreshToken;
import com.mundoasorrir.mundoasorrirbackend.Domain.UserGroup.UserGroup;
import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.Vacation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sys_user")
public class SystemUser {
    @Getter
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;
    @Getter
    private String email;
    @Getter
    @Column(unique=true)
    private String username;

    @Getter
    private String password;
    @Getter
    @ManyToOne()
    private Role systemRole;

    @Getter
    @Column(nullable = false)
    private boolean isActive;

    @Setter
    @Getter
    @ManyToOne(optional = true)
    private Project currentProject;


    @Getter
    @ManyToMany(mappedBy = "enrolledUsers")
    private List<Event> event;

    @ManyToMany(mappedBy = "usersAllowed")
    private List<File> file;
    @OneToMany(mappedBy = "sharedBy")
    private List<File> sharedFiles;


    @ManyToMany(mappedBy = "groupUsers")
    private List<UserGroup> userGroup;
    @OneToMany(mappedBy = "createdBy")
    private List<UserGroup> createdGroups;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Vacation> vacation;

    @OneToOne(cascade = CascadeType.ALL)
    private RefreshToken refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Present> presence;


    public SystemUser(String username,String email, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.isActive = true;
        this.currentProject = null;
    }

    public SystemUser() {
        this.isActive = true;
    }

    public void deactivate(){
        if(this.isActive) {
            this.isActive = false;
        }
    }
    public void activate(){
        if(!this.isActive) {
            this.isActive = true;
        }
    }
    public void removeRelations(){
        removeEvents();
        removeFiles();
        removeGroups();

    }
    public void removeEvents(){
        while(this.event.size() > 0){
            this.event.get(0).getEnrolledUsers().remove(this);
            this.event.remove(0);
        }
    }
    public void removeFiles(){
        while(this.file.size() > 0){
            if(this.file.get(0).isShared(this)){
                this.file.get(0).removeSharedBy();
            }
            this.file.get(0).getUsersAllowed().remove(this);
            this.file.remove(0);

        }
    }
    public void removeGroups(){
        while(this.userGroup.size() > 0){
            if(this.userGroup.get(0).isUserCreator(this)){
                this.userGroup.get(0).removeCreator();
            }
            this.userGroup.get(0).getGroupUsers().remove(this);
            this.userGroup.remove(0);
        }
    }




    public List<Present> getPresencesInMonth(int month, int year){
        List<Present> res = new ArrayList<>();
        for(int i = 0 ; i < this.presence.size(); i++){
            LocalDate localDate = this.presence.get(i).getAttendance().getDayAttendance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if(localDate.getMonthValue() == month && localDate.getYear() == year){
                res.add(this.presence.get(i));

            }
        }
        return res;
    }
    public List<Present> getPresencesInYear(int year){
        List<Present> res = new ArrayList<>();
        for(int i = 0 ; i < this.presence.size(); i++){
            LocalDate localDate = this.presence.get(i).getAttendance().getDayAttendance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if(localDate.getYear() == year){
                res.add(this.presence.get(i));

            }
        }
        return res;
    }


    public void setUserId(Long identifier) {
        this.userId = identifier;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRoles(Role role) {
        this.systemRole = role;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole(){
        return this.systemRole;
    }

    public Set<Role> getSystemRole() {
        Set<Role> roles = new HashSet<>(1);
        roles.add(this.systemRole);


        return roles;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
    public boolean isUserOnVacationInMonth(int month, int year){
        for(int i = 0; i < this.event.size(); i++){
            if(this.event.get(i).getEventType().getName().equals(BaseEventType.VACATION.getName())){
                LocalDate localDateStart = this.event.get(i).getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate localDateEnd = this.event.get(i).getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if((localDateStart.getYear() == year || localDateEnd.getYear() == year) && (localDateStart.getMonthValue() == month && localDateEnd.getMonthValue() == month) ){
                    return true;
                }
            }
        }
        return false;
    }

    public void setSystemRole(Role systemRole) {
        this.systemRole = systemRole;
    }

}
