package com.example.billsplitter.service.impl;

import com.example.billsplitter.component.MessageByLocaleComponent;
import com.example.billsplitter.dto.cost.CostDto;
import com.example.billsplitter.dto.cost.PaymentsResponseDto;
import com.example.billsplitter.entity.Cost;
import com.example.billsplitter.entity.Event;
import com.example.billsplitter.exception.AppException;
import com.example.billsplitter.mapper.CostMapper;
import com.example.billsplitter.repo.CostRepository;
import com.example.billsplitter.repo.EventRepository;
import com.example.billsplitter.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CostServiceImpl implements CostService {


    private final CostRepository costRepository;
    private final EventRepository eventRepository;
    private final CostMapper costMapper;
    private final MessageByLocaleComponent messageByLocaleComponent;

    @Autowired
    public CostServiceImpl(CostRepository costRepository,
                           EventRepository eventRepository,
                           CostMapper costMapper,
                           MessageByLocaleComponent messageByLocaleComponent) {
        this.costRepository = costRepository;
        this.eventRepository = eventRepository;
        this.costMapper = costMapper;
        this.messageByLocaleComponent = messageByLocaleComponent;
    }


    @Override
    public List<CostDto> getAllCostByEventId(Long eventId, Long clientId) {
        getEvent(eventId, clientId);
        return costRepository.findAllByEvent_IdOrderByIdAsc(eventId).stream()
                .map(costMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CostDto add(CostDto costDto, Long clientId) {
        getEvent(costDto.getEvent().getId(), clientId);
        Cost savedCost = costRepository.save(costMapper.toEntity(costDto));
        return costMapper.toDto(savedCost);
    }

    @Override
    @Transactional
    public String delete(Long costId, Long clientId) {
        return costRepository.findById(costId).map(cost -> {
            getEvent(cost.getEvent().getId(), clientId);
            costRepository.deleteById(costId);
            return "Done";
        }).orElseThrow(() -> new AppException.NotFound(messageByLocaleComponent.getMessage("cost.not.found",
                new Object[]{String.valueOf(costId)})));
    }

    @Override
    public PaymentsResponseDto calculatePayments(final Long eventId, final Long clientId) {
        Event event = getEvent(eventId, clientId);
        Map<String, Float> eventMembersMap = event.getEventMembers().stream().collect(Collectors.toConcurrentMap(s -> s, s -> 0.0F));
        event.getCosts().forEach(cost -> {
            List<String> splitBetween = cost.getSplitBetween();
            float payedEachOne = cost.getCostAmount() / splitBetween.size();
            eventMembersMap.put(cost.getPaidBy(), eventMembersMap.get(cost.getPaidBy()) + cost.getCostAmount());
            splitBetween.forEach(client ->
                    eventMembersMap.put(client, eventMembersMap.get(client) - payedEachOne)
            );
        });
        PaymentsResponseDto paymentsResponseDto = new PaymentsResponseDto();
        paymentsResponseDto.setPayments(eventMembersMap);
        return paymentsResponseDto;
    }

    private Event getEvent(Long eventId, Long clientId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new AppException.NotFound(messageByLocaleComponent
                        .getMessage("event.not.found", new Object[]{String.valueOf(eventId)})));
        if (!clientId.equals(event.getClient().getId())) {
            throw new AppException.Forbidden(messageByLocaleComponent.getMessage("permission.denied"));
        }
        return event;
    }

}
