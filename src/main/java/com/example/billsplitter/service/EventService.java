package com.example.billsplitter.service;

import com.example.billsplitter.dto.event.AddEventDto;
import com.example.billsplitter.dto.event.EditEventDto;
import com.example.billsplitter.dto.event.EventDto;
import com.example.billsplitter.dto.event.MemberDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface EventService {
    List<EventDto> getAllEventByUsername(String username);

    EventDto addEvent(AddEventDto eventDto, @NotNull Long clientId);

    EventDto editEvent(EditEventDto editEventDto, Long clientId);

    void delete(Long eventId, Long clientId);

    MemberDto addMember(MemberDto memberDto, Long clientId);

    void deleteMember(Long eventId, String memberUsername, Long clientId);
}
