package com.mundoasorrir.mundoasorrirbackend.DTO.Event;

import java.util.Date;

public class EventDTO {
    private Long eventId;

    private Date startDate;

    private Date endDate;

    public EventDTO(Long eventId, Date startDate, Date endDate) {
        this.eventId = eventId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
