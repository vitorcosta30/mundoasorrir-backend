package com.mundoasorrir.mundoasorrirbackend.Services;

import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import com.mundoasorrir.mundoasorrirbackend.Domain.File.File;
import com.mundoasorrir.mundoasorrirbackend.Repositories.EventRepository;
import com.mundoasorrir.mundoasorrirbackend.Repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor

public class EventService {

    @Autowired
    private final EventRepository eventRepository;

    public Stream<Event> getAllEventsFromUsername(String username) {
        return eventRepository.findByEnrolledUsers_Username(username).stream();
    }

}
