package com.example.billsplitter.mapper;

import com.example.billsplitter.dto.cost.CostDto;
import com.example.billsplitter.dto.event.EventDto;
import com.example.billsplitter.entity.Cost;
import com.example.billsplitter.entity.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CostMapper {

    default EventDto toEventDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setName(event.getName());
        return eventDto;
    }

    default Event toEvent(EventDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setName(eventDto.getName());
        return event;
    }

    CostDto toDto(Cost cost);

    Cost toEntity(CostDto costDto);
}
