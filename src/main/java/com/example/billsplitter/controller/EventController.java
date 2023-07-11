package com.example.billsplitter.controller;


import com.example.billsplitter.dto.client.JwtUserDetails;
import com.example.billsplitter.dto.event.AddEventDto;
import com.example.billsplitter.dto.event.AddMemberDto;
import com.example.billsplitter.dto.event.EditEventDto;
import com.example.billsplitter.dto.event.EventDto;
import com.example.billsplitter.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "event")
public class EventController {


    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/all")
    @Operation(
            summary = "All events",
            description = "The Response is all client's events")
    List<EventDto> getAllEventByUsername(Principal principal) {
        return eventService.getAllEventByUsername(principal.getName());
    }

    @PostMapping
    @Operation(
            summary = "Add new event",
            description = "Client enter new event fields. The response is new event")
    EventDto add(@RequestBody @Valid AddEventDto addEventDto, Authentication authentication) {
        UUID clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return eventService.addEvent(addEventDto, clientId);
    }


    @PutMapping
    @Operation(
            summary = "Edit event",
            description = "Client enter edited event. The response is edited event")
    EventDto edit(@RequestBody @Valid EditEventDto editEventDto, Authentication authentication) {
        UUID clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return eventService.editEvent(editEventDto, clientId);
    }

    @PostMapping("/member")
    @Operation(
            summary = "Add member to event",
            description = "Client enter new member and event id. The response is added member")
    AddMemberDto addMember(@RequestBody @Valid AddMemberDto addMemberDto, Authentication authentication) {
        UUID clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return eventService.addMember(addMemberDto, clientId);
    }

    @DeleteMapping("/member")
    @Operation(
            summary = "Delete member",
            description = "Client enter event id and member uuid for delete member from event")
    void deleteMember(@RequestParam("eventId") Long eventId, @RequestParam("memberUsername") String memberUsername,
                      Authentication authentication) {
        UUID clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        eventService.deleteMember(eventId, memberUsername, clientId);
    }

    @DeleteMapping
    @Operation(
            summary = "Delete event",
            description = "Client enter event id for delete event.")
    void delete(@RequestParam("eventId") Long eventId, Authentication authentication) {
        UUID clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        eventService.delete(eventId, clientId);
    }

}
