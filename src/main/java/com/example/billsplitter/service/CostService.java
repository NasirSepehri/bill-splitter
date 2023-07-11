package com.example.billsplitter.service;

import com.example.billsplitter.dto.cost.CostDto;
import com.example.billsplitter.dto.cost.PaymentsResponseDto;

import java.util.List;

public interface CostService {
    List<CostDto> getAllCostByEventId(Long eventId, Long clientId);

    CostDto add(CostDto costDto, Long clientId);

    String delete(Long costId, Long clientId);

    PaymentsResponseDto calculatePayments(final Long eventId, final Long clientId);
}
