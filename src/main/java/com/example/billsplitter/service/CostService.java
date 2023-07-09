package com.example.billsplitter.service;

import com.example.billsplitter.dto.CostDto;
import com.example.billsplitter.dto.PaymentsResponseDto;

import java.util.List;

public interface CostService {
    List<CostDto> getAllCostByEventId(Long eventId);

    CostDto add(CostDto costDto);

    void delete(Long costId);

    PaymentsResponseDto calculatePayments(final Long eventId, final String username);
}
