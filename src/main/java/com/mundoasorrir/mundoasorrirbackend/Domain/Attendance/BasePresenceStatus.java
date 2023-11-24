package com.mundoasorrir.mundoasorrirbackend.Domain.Attendance;

import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.Status;

public class BasePresenceStatus {

    /**
     * Coordenador
     */
    public static final PresenceStatus PRESENT = PresenceStatus.valueOf("PRESENT");
    /**
     * Colaborador
     */
    public static final PresenceStatus ABSENT = PresenceStatus.valueOf("ABSENT");
    /**
     * Diretor
     */


    public static final PresenceStatus UNMARKED = PresenceStatus.valueOf("UNMARKED");


    public static final PresenceStatus LATE = PresenceStatus.valueOf("LATE");


    /**
     * get available role types for adding new users
     *
     * @return
     */
    public static PresenceStatus[] status() {
        return new PresenceStatus[] {PRESENT, ABSENT, UNMARKED };
    }
}
