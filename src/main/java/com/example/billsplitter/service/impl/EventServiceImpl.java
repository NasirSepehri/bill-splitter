package com.example.billsplitter.service.impl;

import com.example.billsplitter.dto.event.AddEventDto;
import com.example.billsplitter.dto.event.EventDto;
import com.example.billsplitter.entity.Client;
import com.example.billsplitter.entity.Event;
import com.example.billsplitter.mapper.EventMapper;
import com.example.billsplitter.repo.ClientRepository;
import com.example.billsplitter.repo.EventRepository;
import com.example.billsplitter.service.EventService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {


    private final EventRepository eventRepository;
    private final ClientRepository clientRepository;
    private final EventMapper eventMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            ClientRepository clientRepository,
                            EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.clientRepository = clientRepository;
        this.eventMapper = eventMapper;
    }


    @Override
    public List<EventDto> getAllEventByUsername(String username) {
        return eventRepository.findAllByClientUsernameOrderByIdAsc(username).stream()
                .map(eventMapper::toDto)
                .toList();
    }

    @Override
    public EventDto addEvent(AddEventDto addEventDto, @NotNull Long clientId) {

        Event event = new Event();
        event.setName(addEventDto.getName());
        event.setClient(new Client(clientId));
        event.setCreatedDate(Instant.now());
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDto(savedEvent);

    }


}
