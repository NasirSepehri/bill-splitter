package com.example.billsplitter.service.impl;

import com.example.billsplitter.component.MessageByLocaleComponent;
import com.example.billsplitter.dto.event.AddEventDto;
import com.example.billsplitter.dto.event.AddMemberDto;
import com.example.billsplitter.dto.event.EditEventDto;
import com.example.billsplitter.dto.event.EventDto;
import com.example.billsplitter.entity.Client;
import com.example.billsplitter.entity.Event;
import com.example.billsplitter.entity.Member;
import com.example.billsplitter.exception.AppException;
import com.example.billsplitter.mapper.EventMapper;
import com.example.billsplitter.repo.EventRepository;
import com.example.billsplitter.service.EventService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {


    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final MessageByLocaleComponent messageByLocaleComponent;


    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            EventMapper eventMapper,
                            MessageByLocaleComponent messageByLocaleComponent) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.messageByLocaleComponent = messageByLocaleComponent;
    }


    @Override
    public List<EventDto> getAllEventByUsername(String username) {
        return eventRepository.findAllByClientUsernameOrderByIdAsc(username).stream()
                .map(eventMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public EventDto addEvent(AddEventDto addEventDto, @NotNull UUID clientId) {

        Event event = new Event();
        event.setName(addEventDto.getName());
        event.setClient(new Client(clientId));
        event.setCreatedDate(Instant.now());
        event.setDescription(addEventDto.getDescription());
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDto(savedEvent);

    }

    @Override
    @Transactional
    public EventDto editEvent(EditEventDto editEventDto, UUID clientId) {
        Event event = getEvent(editEventDto.getEventId(), clientId);
        event.setName(editEventDto.getName());
        event.setDescription(editEventDto.getDescription());
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDto(savedEvent);
    }

    @Override
    @Transactional
    public void delete(Long eventId, UUID clientId) {
        Event event = getEvent(eventId, clientId);
        eventRepository.delete(event);
    }

    @Override
    @Transactional
    //// TODO: 7/11/23 change event table structure
    public AddMemberDto addMember(AddMemberDto addMemberDto, UUID clientId) {
        Event event = getEvent(addMemberDto.getEventId(), clientId);
        event.getEventMembers().add(new Member(addMemberDto.getMemberUsername()));
        return addMemberDto;
    }


    @Override
    public void deleteMember(Long eventId, String memberUsername, UUID clientId) {
        Event event = getEvent(eventId, clientId);
        eventRepository.deleteMemberByEventIdAndMemberUsername(event.getId(), memberUsername);
    }


    private Event getEvent(Long eventId, UUID clientId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new AppException.NotFound(messageByLocaleComponent
                        .getMessage("event.not.found", new Object[]{String.valueOf(eventId)})));
        if (!clientId.equals(event.getClient().getId())) {
            throw new AppException.Forbidden(messageByLocaleComponent.getMessage("permission.denied"));
        }
        return event;
    }

}
