package com.example.billsplitter.controller;


import com.example.billsplitter.dto.client.JwtUserDetails;
import com.example.billsplitter.dto.event.AddEventDto;
import com.example.billsplitter.dto.event.AddMemberDto;
import com.example.billsplitter.dto.event.EditEventDto;
import com.example.billsplitter.dto.event.EventDto;
import com.example.billsplitter.service.EventService;
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
    List<EventDto> getAllEventByUsername(Principal principal) {
        return eventService.getAllEventByUsername(principal.getName());
    }

    @PostMapping
    EventDto add(@RequestBody @Valid AddEventDto addEventDto, Authentication authentication) {
        UUID clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return eventService.addEvent(addEventDto, clientId);
    }


    @PutMapping
    EventDto edit(@RequestBody @Valid EditEventDto editEventDto, Authentication authentication) {
        UUID clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return eventService.editEvent(editEventDto, clientId);
    }

    @PostMapping("/member")
    AddMemberDto addMember(@RequestBody @Valid AddMemberDto addMemberDto, Authentication authentication) {
        UUID clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return eventService.addMember(addMemberDto, clientId);
    }

    @DeleteMapping("/member")
    void deleteMember(@RequestParam("eventId") Long eventId, @RequestParam("memberUsername") String memberUsername,
                      Authentication authentication) {
        UUID clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        eventService.deleteMember(eventId, memberUsername, clientId);
    }

    @DeleteMapping
    void delete(@RequestParam("eventId") Long eventId, Authentication authentication) {
        UUID clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        eventService.delete(eventId, clientId);
    }

}
