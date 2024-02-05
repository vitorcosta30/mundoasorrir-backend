package com.mundoasorrir.mundoasorrirbackend.Domain.Event;

import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.VacationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    Event e1;

    Event e2;


    @BeforeEach
    void setUp() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        e1 = new Event(new ArrayList<>(),dateFormat.parse("2023-12-15"),dateFormat.parse("2023-12-30"), BaseEventType.MEETING);
        e2 = new Event(new ArrayList<>(),dateFormat.parse("2023-12-15"),dateFormat.parse("2023-12-30"), BaseEventType.MISSION);

    }

    @Test
    void isUserEnrolled() {
    }


    @Test
    void getEnrolledUsers() {
    }

    @Test
    void setEnrolledUsers() {
    }

    @Test
    void getStartDate() {
    }

    @Test
    void setStartDate() {
    }

    @Test
    void getEndDate() {
    }

    @Test
    void setEndDate() {
    }

    @Test
    void getEventType() {
    }

    @Test
    void setEventType() {
    }

    @Test
    void getPlace() {
    }

    @Test
    void setPlace() {
    }

    @Test
    void getDescription() {
    }

    @Test
    void busyForTheDay() {
    }

    @Test
    void setDescription() {
    }

    @Test
    void isVacation() {
        Assertions.assertFalse(this.e1.isVacation());
        Assertions.assertFalse(this.e2.isVacation());

    }
}