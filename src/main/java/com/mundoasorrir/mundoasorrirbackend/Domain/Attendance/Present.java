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

    private Boolean wasPresent;





    public Present() {

    }

    public Present(SystemUser user, Boolean wasPresent) {
        this.user = user;
        this.wasPresent = wasPresent;
    }
}
