package com.mundoasorrir.mundoasorrirbackend.Services;

import com.mundoasorrir.mundoasorrirbackend.Domain.Event.BaseEventType;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.EventType;
import com.mundoasorrir.mundoasorrirbackend.Domain.File.File;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Repositories.EventRepository;
import com.mundoasorrir.mundoasorrirbackend.Repositories.EventTypeRepository;
import com.mundoasorrir.mundoasorrirbackend.Repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor

public class EventService {

    @Autowired
    private final EventRepository eventRepository;
    @Autowired
    private final EventTypeRepository eventTypeRepository;

    public Stream<Event> getAllEventsFromUsername(String username) {
        return eventRepository.findByEnrolledUsers_Username(username).stream();
    }
    public Event save(Event newEvent){
        return eventRepository.save(newEvent);
    }

    public Event getEventFromId(Long id){
        return this.eventRepository.getReferenceById(id);
    }





    public List<SystemUser> usersBusy(Date date){
        List<Event> events = this.eventRepository.activeEvents(date);
        EventType eventType;
        if(this.eventTypeRepository.findByName(BaseEventType.VACATION.getName()).isPresent()){
            eventType = this.eventTypeRepository.findByName(BaseEventType.VACATION.getName()).get();
        }else{
            eventType = this.eventTypeRepository.save(BaseEventType.VACATION);

        }

        events.retainAll(this.eventRepository.findByEventTypeEquals(eventType));
        List<SystemUser> res = new ArrayList<>();
        for(int i = 0; i < events.size();i++){
            res.addAll(events.get(i).getEnrolledUsers());
        }
        return res;
    }

}
