package com.example.billsplitter.service;

import com.example.billsplitter.dto.cost.AddCostDto;
import com.example.billsplitter.dto.cost.CostDto;
import com.example.billsplitter.dto.cost.EditCostDto;
import com.example.billsplitter.dto.cost.PaymentsResponseDto;

import java.util.List;
import java.util.UUID;

public interface CostService {
    List<CostDto> getAllCostByEventId(Long eventId, UUID clientId);

    CostDto add(AddCostDto addCostDto, UUID clientId);

    String delete(Long costId, UUID clientId);

    PaymentsResponseDto calculatePayments(final Long eventId, final UUID clientId);

    CostDto edit(EditCostDto editCostDto, UUID clientId);

}
