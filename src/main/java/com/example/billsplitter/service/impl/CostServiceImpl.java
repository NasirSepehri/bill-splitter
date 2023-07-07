package com.example.billsplitter.service.impl;

import com.example.billsplitter.dto.CostDto;
import com.example.billsplitter.entity.Cost;
import com.example.billsplitter.mapper.CostMapper;
import com.example.billsplitter.repo.CostRepository;
import com.example.billsplitter.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostServiceImpl implements CostService {


    private final CostRepository costRepository;
    private final CostMapper costMapper;

    @Autowired
    public CostServiceImpl(CostRepository costRepository, CostMapper costMapper) {
        this.costRepository = costRepository;
        this.costMapper = costMapper;
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
}
