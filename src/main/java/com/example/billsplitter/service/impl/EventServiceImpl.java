package com.example.billsplitter.service.impl;

import com.example.billsplitter.dto.EventDto;
import com.example.billsplitter.entity.Event;
import com.example.billsplitter.mapper.EventMapper;
import com.example.billsplitter.repo.EventRepository;
import com.example.billsplitter.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {


    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }


    @Override
    public List<EventDto> getAllEventByUserId(Long userId) {
        return eventRepository.findAllByUser_IdOrderByIdAsc(userId).stream()
                .map(eventMapper::toDto)
                .toList();
    }

    @Override
    public EventDto addEvent(EventDto eventDto) {
        eventDto.setCreatedDate(Instant.now());
        Event savedEvent = eventRepository.save(eventMapper.toEntity(eventDto));
        return eventMapper.toDto(savedEvent);
    }


}
