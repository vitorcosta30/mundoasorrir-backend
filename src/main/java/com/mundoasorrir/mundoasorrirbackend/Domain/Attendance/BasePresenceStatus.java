package com.mundoasorrir.mundoasorrirbackend.Domain.Attendance;

public class BasePresenceStatus {

    /**
     * Coordenador
     */
    public static final PresenceStatus PRESENT = PresenceStatus.valueOf("PRESENTE");
    /**
     * Colaborador
     */
    public static final PresenceStatus ABSENT = PresenceStatus.valueOf("FALTOU");
    /**
     * Diretor
     */


    public static final PresenceStatus UNMARKED = PresenceStatus.valueOf("POR MARCAR");


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
