package com.example.billsplitter.service;

import com.example.billsplitter.dto.CostDto;

import java.util.List;

public interface CostService {
    List<CostDto> getAllCostByEventId(Long eventId);

    CostDto add(CostDto costDto);

    void delete(Long costId);
}
