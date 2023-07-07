package com.example.billsplitter.controller;


import com.example.billsplitter.dto.EventDto;
import com.example.billsplitter.service.EventService;
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
    List<EventDto> getAllEventByUsername(@RequestParam("userId") Long userId) {
        return eventService.getAllEventByUserId(userId);
    }

    @PostMapping
    EventDto add(@RequestBody EventDto eventDto) {
        return eventService.addEvent(eventDto);
    }
}
