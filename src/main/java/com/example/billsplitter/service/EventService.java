package com.example.billsplitter.service;

import com.example.billsplitter.dto.event.AddEventDto;
import com.example.billsplitter.dto.event.EventDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface EventService {
    List<EventDto> getAllEventByUsername(String username);

    EventDto addEvent(AddEventDto eventDto, @NotNull Long clientId);
}
