package com.example.billsplitter.service;

import com.example.billsplitter.dto.EventDto;

import java.util.List;

public interface EventService {
    List<EventDto> getAllEventByUserId(Long userId);

    EventDto addEvent(EventDto eventDto);
}
