package com.example.billsplitter.service.impl;

import com.example.billsplitter.component.MessageByLocaleComponent;
import com.example.billsplitter.dto.CostDto;
import com.example.billsplitter.dto.PaymentsResponseDto;
import com.example.billsplitter.entity.Cost;
import com.example.billsplitter.entity.Event;
import com.example.billsplitter.exception.AppException;
import com.example.billsplitter.mapper.CostMapper;
import com.example.billsplitter.repo.CostRepository;
import com.example.billsplitter.repo.EventRepository;
import com.example.billsplitter.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<CostDto> getAllCostByEventId(Long eventId) {
        return costRepository.findAllByEvent_IdOrderByIdAsc(eventId).stream()
                .map(costMapper::toDto)
                .toList();
    }

    @Override
    public CostDto add(CostDto costDto) {
        Cost savedCost = costRepository.save(costMapper.toEntity(costDto));
        return costMapper.toDto(savedCost);
    }

    @Override
    public void delete(Long costId) {
        costRepository.deleteById(costId);
    }

    @Override
    public PaymentsResponseDto calculatePayments(final Long eventId, final String username) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new AppException.NotFound(messageByLocaleComponent
                        .getMessage("event.not.found", new Object[]{String.valueOf(eventId)})));
        checkClientPermission(username, event);
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

    private void checkClientPermission(String username, Event event) {
        if (!username.equals(event.getClient().getUsername())) {
            throw new AppException.Forbidden(messageByLocaleComponent.getMessage("permission.denied"));
        }
    }
}
