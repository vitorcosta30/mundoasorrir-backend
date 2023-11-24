package com.mundoasorrir.mundoasorrirbackend.Domain.Attendance;


import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import jakarta.persistence.*;
import org.springframework.data.util.Pair;

@Entity
public class Present {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long attendanceEventId;
    @ManyToOne
    private SystemUser user;

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

    public Long getAttendanceEventId() {

        return attendanceEventId;
    }

    public void setAttendanceEventId(Long attendanceEventId) {
        this.attendanceEventId = attendanceEventId;
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
}
