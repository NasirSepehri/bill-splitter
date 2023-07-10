package com.example.billsplitter.service;

import com.example.billsplitter.dto.cost.CostDto;
import com.example.billsplitter.dto.cost.PaymentsResponseDto;

import java.util.List;

public interface CostService {
    List<CostDto> getAllCostByEventId(Long eventId, String username);

    CostDto add(CostDto costDto, String username);

    String delete(Long costId, String username);

    PaymentsResponseDto calculatePayments(final Long eventId, final String username);
}
