package com.example.billsplitter.controller;


import com.example.billsplitter.dto.EventDto;
import com.example.billsplitter.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    List<EventDto> getAllEventByUsername(@RequestParam("clientId") Long clientId) {
        return eventService.getAllEventByClientId(clientId);
    }

    @PostMapping
    EventDto add(@RequestBody @Valid EventDto eventDto) {
        return eventService.addEvent(eventDto);
    }
}
