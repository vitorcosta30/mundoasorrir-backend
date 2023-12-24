package com.mundoasorrir.mundoasorrirbackend.Repositories;

import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.EventType;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByEnrolledUsers_Username(String username);

    List<Event> findByEventTypeEquals(EventType eventTYpe);

    @Query("Select e.enrolledUsers from Event e where e.startDate < :date and e.endDate > :date")
    List<SystemUser> usersBusy(Date date);


    @Query("Select e from Event e where e.startDate < :date and e.endDate >= :date")
    List<Event> activeEvents(Date date);

}
