package com.example.billsplitter.service.impl;

import com.example.billsplitter.component.MessageByLocaleComponent;
import com.example.billsplitter.dto.cost.AddCostDto;
import com.example.billsplitter.dto.cost.CostDto;
import com.example.billsplitter.dto.cost.EditCostDto;
import com.example.billsplitter.dto.cost.PaymentsResponseDto;
import com.example.billsplitter.dto.event.MemberDto;
import com.example.billsplitter.entity.Cost;
import com.example.billsplitter.entity.Event;
import com.example.billsplitter.entity.Member;
import com.example.billsplitter.exception.AppException;
import com.example.billsplitter.mapper.CostMapper;
import com.example.billsplitter.mapper.MemberMapper;
import com.example.billsplitter.repo.CostRepository;
import com.example.billsplitter.repo.EventRepository;
import com.example.billsplitter.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

@Service
public class CostServiceImpl implements CostService {


    private final CostRepository costRepository;
    private final EventRepository eventRepository;
    private final CostMapper costMapper;
    private final MessageByLocaleComponent messageByLocaleComponent;
    private final MemberMapper memberMapper;

    @Autowired
    public CostServiceImpl(CostRepository costRepository,
                           EventRepository eventRepository,
                           CostMapper costMapper,
                           MessageByLocaleComponent messageByLocaleComponent,
                           MemberMapper memberMapper) {
        this.costRepository = costRepository;
        this.eventRepository = eventRepository;
        this.costMapper = costMapper;
        this.messageByLocaleComponent = messageByLocaleComponent;
        this.memberMapper = memberMapper;
    }


    @Override
    public List<CostDto> getAllCostByEventId(Long eventId, UUID clientId) {
        Event event = getEvent(eventId, clientId);
        return event.getCosts().stream()
                .map(costMapper::toDto)
                .sorted(Comparator.comparing(CostDto::getId))
                .toList();
    }

    @Override
    @Transactional
    public CostDto add(AddCostDto addCostDto, UUID clientId) {
        Event event = getEvent(addCostDto.getEventId(), clientId);
        validateSplitMembers(event.getEventMembers(), addCostDto.getSplitBetween());
        validatePayedBy(event.getEventMembers(), addCostDto.getPaidBy());
        Cost cost = costMapper.toEntity(addCostDto);
        cost.setEvent(event);
        return costMapper.toDto(costRepository.save(cost));
    }

    private void validateSplitMembers(List<Member> eventMembers, List<MemberDto> splitBetween) {
        if (splitBetween.stream().anyMatch(sbm -> eventMembers.stream().noneMatch(member -> member.getUuid().equals(sbm.getUuid())))) {
            throw new AppException.BadRequest(messageByLocaleComponent.getMessage("split.members.must.be.member.of.event.client",
                    new Object[]{eventMembers.stream().map(member -> member.getUuid().toString())
                            .collect(Collectors.joining(","))}));
        }
    }

    private void validatePayedBy(List<Member> eventMembers, MemberDto paidBy) {
        if (eventMembers.stream().noneMatch(member -> member.getUuid().equals(paidBy.getUuid()))) {
            throw new AppException.BadRequest(messageByLocaleComponent.getMessage("paid.by.client.must.be.member.of.event.client",
                    new Object[]{eventMembers.stream().map(member -> member.getUuid().toString())
                            .collect(Collectors.joining(","))}));
        }
    }


    @Override
    @Transactional
    public CostDto edit(EditCostDto editCostDto, UUID clientId) {
        return costRepository.findById(editCostDto.getCostId()).map(cost -> {
            Event event = getEvent(cost.getEvent().getId(), clientId);
            validateSplitMembers(event.getEventMembers(), editCostDto.getSplitBetween());
            validatePayedBy(event.getEventMembers(), editCostDto.getPaidBy());
            cost.setCostDescription(editCostDto.getCostDescription());
            cost.setCostAmount(editCostDto.getCostAmount());
            cost.setPaidBy(memberMapper.toEntity(editCostDto.getPaidBy()));
            cost.setSplitBetween(editCostDto.getSplitBetween().stream()
                    .map(memberMapper::toEntity).collect(toCollection(ArrayList::new)));
            return costMapper.toDto(costRepository.save(cost));
        }).orElseThrow(() -> new AppException.NotFound(messageByLocaleComponent.getMessage("cost.not.found")));

    }

    @Override
    @Transactional
    public String delete(Long costId, UUID clientId) {
        return costRepository.findById(costId).map(cost -> {
            getEvent(cost.getEvent().getId(), clientId);
            costRepository.delete(cost);
            return "Done";
        }).orElseThrow(() -> new AppException.NotFound(messageByLocaleComponent.getMessage("cost.not.found",
                new Object[]{String.valueOf(costId)})));
    }

    @Override
    public PaymentsResponseDto calculatePayments(final Long eventId, final UUID clientId) {
        Event event = getEvent(eventId, clientId);
        Map<Member, Float> eventMembersMap = event.getEventMembers().stream().collect(Collectors.toConcurrentMap(s -> s, s -> 0.0F));
        event.getCosts().forEach(cost -> {
            List<Member> splitBetween = cost.getSplitBetween();
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
