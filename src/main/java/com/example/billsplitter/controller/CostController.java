package com.example.billsplitter.controller;

import com.example.billsplitter.dto.client.JwtUserDetails;
import com.example.billsplitter.dto.cost.AddCostDto;
import com.example.billsplitter.dto.cost.CostDto;
import com.example.billsplitter.dto.cost.EditCostDto;
import com.example.billsplitter.dto.cost.PaymentsResponseDto;
import com.example.billsplitter.service.CostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "cost")
public class CostController {

    private final CostService costService;

    @Autowired
    public CostController(CostService costService) {
        this.costService = costService;
    }


    @GetMapping("/all")
    @Operation(
            summary = "All costs",
            description = "Client enter event id. The response is all event's costs")
    List<CostDto> getAllCostByEventId(@RequestParam("eventId") Long eventId, Authentication authentication) {
        UUID clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return costService.getAllCostByEventId(eventId, clientId);
    }


    @PostMapping
    @Operation(
            summary = "Add new cost",
            description = "Client enter new event id and cost field. The response is new cost")
    CostDto add(@RequestBody @Valid AddCostDto addCostDto, Authentication authentication) {
        UUID clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return costService.add(addCostDto, clientId);
    }

    @PutMapping
    @Operation(
            summary = "Edit the cost",
            description = "Client enter cost id and edited cost field. The response is edited cost")
    CostDto edit(@RequestBody @Valid EditCostDto editCostDto, Authentication authentication) {
        UUID clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return costService.edit(editCostDto, clientId);
    }

    @DeleteMapping
    @Operation(
            summary = "Delete the cost",
            description = "Client enter cost id for delete cost.")
    String delete(@RequestParam("costId") Long costId, Authentication authentication) {
        UUID clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return costService.delete(costId, clientId);
    }


    @GetMapping("/payments")
    @Operation(
            summary = "Calculate payments",
            description = "Client enter event id for calculate each user how much should be pay")
    PaymentsResponseDto getPayments(@RequestParam("eventId") Long eventId, Authentication authentication) {
        UUID clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return costService.calculatePayments(eventId, clientId);
    }

}
