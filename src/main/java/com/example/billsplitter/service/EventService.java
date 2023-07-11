package com.example.billsplitter.service;

import com.example.billsplitter.dto.event.AddEventDto;
import com.example.billsplitter.dto.event.AddMemberDto;
import com.example.billsplitter.dto.event.EditEventDto;
import com.example.billsplitter.dto.event.EventDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public interface EventService {
    List<EventDto> getAllEventByUsername(String username);

    EventDto addEvent(AddEventDto eventDto, @NotNull UUID clientId);

    EventDto editEvent(EditEventDto editEventDto, UUID clientId);

    void delete(Long eventId, UUID clientId);

    AddMemberDto addMember(AddMemberDto addMemberDto, UUID clientId);

    void deleteMember(Long eventId, String memberUsername, UUID clientId);
}
