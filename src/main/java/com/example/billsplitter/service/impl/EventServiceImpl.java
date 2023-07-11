package com.example.billsplitter.service.impl;

import com.example.billsplitter.dto.event.AddEventDto;
import com.example.billsplitter.dto.event.EventDto;
import com.example.billsplitter.entity.Client;
import com.example.billsplitter.entity.Event;
import com.example.billsplitter.mapper.EventMapper;
import com.example.billsplitter.repo.EventRepository;
import com.example.billsplitter.service.EventService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {


    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }


    @Override
    public List<EventDto> getAllEventByUsername(String username) {
        return eventRepository.findAllByClientUsernameOrderByIdAsc(username).stream()
                .map(eventMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public EventDto addEvent(AddEventDto addEventDto, @NotNull Long clientId) {

        Event event = new Event();
        event.setName(addEventDto.getName());
        event.setClient(new Client(clientId));
        event.setCreatedDate(Instant.now());
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDto(savedEvent);

    }


}
