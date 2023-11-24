package com.mundoasorrir.mundoasorrirbackend.DTO.Event;

import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;

public class EventMapper {

    public static EventDTO toDTO(Event event){
        return new EventDTO(event.getEventId().toString(),event.getStartDate().toString(),event.getEndDate().toString(),event.getDescription(),event.getPlace());
    }
}
