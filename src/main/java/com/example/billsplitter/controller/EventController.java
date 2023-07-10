package com.example.billsplitter.controller;


import com.example.billsplitter.dto.client.JwtUserDetails;
import com.example.billsplitter.dto.event.AddEventDto;
import com.example.billsplitter.dto.event.EventDto;
import com.example.billsplitter.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
        Long clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return eventService.addEvent(addEventDto, clientId);
    }
}
