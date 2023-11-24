package com.mundoasorrir.mundoasorrirbackend.Domain.Event;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.Role;

public class BaseEventType {
    public static final EventType MISSION = EventType.valueOf("MISSION");
    /**
     * Colaborador
     */
    public static final EventType MEETING = EventType.valueOf("MEETING");
    /**
     * Diretor
     */

    public static final EventType VACATION = EventType.valueOf("VACATION");




    /**
     * get available role types for adding new users
     *
     * @return
     */
    public static EventType[] eventTypes() {
        return new EventType[] {MISSION, MEETING };
    }

}
