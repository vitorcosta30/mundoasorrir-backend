package com.mundoasorrir.mundoasorrirbackend.Services;

import com.mundoasorrir.mundoasorrirbackend.Domain.Event.BaseEventType;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.EventType;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Message.ErrorMessage;
import com.mundoasorrir.mundoasorrirbackend.Message.SuccessMessage;
import com.mundoasorrir.mundoasorrirbackend.Repositories.EventRepository;
import com.mundoasorrir.mundoasorrirbackend.Repositories.EventTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    public Event create(Event event, String eventType){
        EventType[] eventTypes = BaseEventType.eventTypes();
        List<EventType> eventTypesSaved = eventTypeRepository.findAll();
        if(eventType == null ){
            return null;
        }
        for(int i = 0 ; i < eventTypes.length ; i++){
            if( eventType.equals(eventTypes[i].getName())){
                for(int x = 0 ; x < eventTypesSaved.size();x++){
                    if(eventTypesSaved.get(x).equals(eventTypes[i])){
                        event.setEventType(eventTypesSaved.get(x));
                        return save(event);
                    }
                }
                event.setEventType(eventTypes[i]);
                eventTypeRepository.save(eventTypes[i]);
                return save(event);
            }
        }
        return null;


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
