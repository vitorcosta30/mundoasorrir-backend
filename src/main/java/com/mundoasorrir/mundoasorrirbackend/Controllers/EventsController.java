package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.mundoasorrir.mundoasorrirbackend.Auth.JwtUtils;
import com.mundoasorrir.mundoasorrirbackend.DTO.Event.EventDTO;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import com.mundoasorrir.mundoasorrirbackend.Message.ResponseFile;
import com.mundoasorrir.mundoasorrirbackend.Repositories.EventRepository;
import com.mundoasorrir.mundoasorrirbackend.Services.EventService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "${mundoasorrir.app.frontend}", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventsController {
    @Autowired
    private EventService eventService;
    @Autowired
    JwtUtils jwtUtils;
    @GetMapping("/getEvents")
    public ResponseEntity<List<Event>> getEvents(HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        List<Event> userEvents = eventService.getAllEventsFromUsername(username).map(event ->{
                return new Event(event.getEventId(),event.getEnrolledUsers(),event.getStartDate(),event.getEndDate());}).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(userEvents);



    }

}
