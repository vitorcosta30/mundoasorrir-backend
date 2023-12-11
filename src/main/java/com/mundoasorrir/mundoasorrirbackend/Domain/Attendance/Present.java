package com.mundoasorrir.mundoasorrirbackend.Domain.Attendance;


import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import jakarta.persistence.*;
import org.springframework.data.util.Pair;

@Entity
public class Present {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long presenceId;
    @ManyToOne( cascade =CascadeType.ALL)
    private SystemUser user;

    @ManyToOne(cascade =CascadeType.ALL)
    private Attendance attendance;

    @Embedded
    private PresenceStatus presenceStatus;





    public Present() {

    }

    public Present(SystemUser user, PresenceStatus presenceStatus) {
        this.user = user;
        this.presenceStatus = presenceStatus;
    }

    public Present(SystemUser user) {
        this.user = user;
        this.presenceStatus = new PresenceStatus();
    }

    public Long getPresenceId() {

        return presenceId;
    }

    public void setPresenceId(Long attendanceEventId) {
        this.presenceId = attendanceEventId;
    }

    public SystemUser getUser() {
        return user;
    }

    public void setUser(SystemUser user) {
        this.user = user;
    }

    public void wasPresent(){
        this.presenceStatus.wasPresent();
    }
    public void wasAbsent(){
        this.presenceStatus.wasAbsent();
    }



    public PresenceStatus getPresenceStatus() {
        return presenceStatus;

    }

    public void setPresenceStatus(PresenceStatus presenceStatus) {
        this.presenceStatus = presenceStatus;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }
}
