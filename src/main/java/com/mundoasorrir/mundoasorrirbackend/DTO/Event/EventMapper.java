package com.mundoasorrir.mundoasorrirbackend.DTO.Event;

import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EventMapper {

    public static List<EventDTO> toDTO(List<Event> events){
        List<EventDTO> res = new ArrayList<>();
        for(int i = 0 ; i < events.size(); i++){
            res.add(toDTO(events.get(i)));
        }
        return res;
    }

    public static EventDTO toDTO(Event event){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return new EventDTO(event.getEventId().toString(), dateFormat.format(event.getStartDate()), dateFormat.format(event.getEndDate()),event.getDescription(),event.getPlace(),event.getEventType().getName());
    }
}
