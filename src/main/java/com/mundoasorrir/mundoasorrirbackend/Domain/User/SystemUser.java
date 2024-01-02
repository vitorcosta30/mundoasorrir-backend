package com.mundoasorrir.mundoasorrirbackend.Domain.User;

import com.mundoasorrir.mundoasorrirbackend.Domain.Attendance.Present;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import com.mundoasorrir.mundoasorrirbackend.Domain.File.File;
import com.mundoasorrir.mundoasorrirbackend.Domain.Project.Project;
import com.mundoasorrir.mundoasorrirbackend.Domain.RefreshToken;
import com.mundoasorrir.mundoasorrirbackend.Domain.UserGroup.UserGroup;
import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.VacationRequest;
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
    private List<VacationRequest> vacation;

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
        for (Present present : this.presence) {
            LocalDate localDate = present.getAttendance().getDayAttendance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (localDate.getMonthValue() == month && localDate.getYear() == year) {
                res.add(present);

            }
        }
        return res;
    }
    public List<Present> getPresencesInYear(int year){
        List<Present> res = new ArrayList<>();
        for (Present present : this.presence) {
            LocalDate localDate = present.getAttendance().getDayAttendance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (localDate.getYear() == year) {
                res.add(present);

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
        for (Event value : this.event) {
            if (isOnVacation(value, month, year)) {
                return true;
            }
        }
        return false;
    }
    private boolean isOnVacation(Event event, int month , int year){
        LocalDate start = event.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = event.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return isVacation(event) && isMonthInPeriod(month,year,start,end);
    }

    private boolean isMonthInPeriod(int month, int year, LocalDate start, LocalDate end){
        return isYearContained(year,start.getYear(),end.getYear()) && isOnVacation(month, year, start, end);
    }
    private boolean isOnVacation (int month, int year, LocalDate start, LocalDate end){
        return isOnVacationMultiYear(month,year,start,end) || isOnVacationSingleYear(month,start,end);

    }
    private boolean isOnVacationSingleYear(int month, LocalDate start, LocalDate end){
        return isSingleYear(start.getYear(),end.getYear()) && checkEventSingleYear(month,start.getMonthValue(),end.getMonthValue());
    }
    private boolean isOnVacationMultiYear(int month, int year ,LocalDate start, LocalDate end){
        return isMultiYear(start.getYear(),end.getYear()) && checkMultiYearEvent(month,year,start,end);
    }

    private boolean isYearContained(int year, int start, int end){
        return start<= year && end >= year;
    }
    private boolean isVacation(Event event){
        return event.isVacation();
    }
    private boolean isMultiYear(int start, int end){
        return start != end;
    }
    private boolean isSingleYear(int start, int end){
        return start == end;
    }
    private boolean checkEventSingleYear(int month, int start, int end){
        return start <= month && end >= month;
    }
    private boolean checkMultiYearEvent(int month, int year, LocalDate start, LocalDate end){
        return checkStartingYear(month,year,start.getMonthValue(),start.getYear()) || checkYearsBetween(year,start.getYear(),end.getYear()) || checkEndingYear(month,year,end.getMonthValue(),end.getYear());
    }

    private boolean checkStartingYear(int month, int year, int startMonth, int startYear){
        return month >= startMonth && month <= 12 && year == startYear;
    }

    private boolean checkEndingYear(int month, int year, int endMonth, int endYear){
        return month <= endMonth && month >= 1 && year == endYear;
    }
    private boolean checkYearsBetween(int year, int start, int end){
        int diffYear = end - start;
        return diffYear >= 2 && year > start && year < end;
    }

    public void setSystemRole(Role systemRole) {
        this.systemRole = systemRole;
    }

}
