package com.mundoasorrir.mundoasorrirbackend.Domain.Event;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "event_type")
public class EventType {

    @Id
    @Column(name = "event_type_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long eventTypeId;



    @Column(length = 20, unique = true)
    private String name;


    public EventType(){

    }

    public EventType(String name){
        this.name = name;
    }


    public static EventType valueOf(final String eventType) {
        return new EventType(eventType);
    }

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventType eventType = (EventType) o;
        return Objects.equals(name, eventType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
