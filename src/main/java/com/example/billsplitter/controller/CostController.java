package com.example.billsplitter.controller;

import com.example.billsplitter.dto.client.JwtUserDetails;
import com.example.billsplitter.dto.cost.CostDto;
import com.example.billsplitter.dto.cost.PaymentsResponseDto;
import com.example.billsplitter.service.CostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "cost")
public class CostController {

    private final CostService costService;

    @Autowired
    public CostController(CostService costService) {
        this.costService = costService;
    }


    @GetMapping("/all")
    List<CostDto> getAllCostByEventId(@RequestParam("eventId") Long eventId, Authentication authentication) {
        Long clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return costService.getAllCostByEventId(eventId, clientId);
    }


    @PostMapping
    CostDto add(@RequestBody @Valid CostDto costDto, Authentication authentication) {
        Long clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return costService.add(costDto, clientId);
    }

    @DeleteMapping
    String delete(@RequestParam("costId") Long costId, Authentication authentication) {
        Long clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return costService.delete(costId, clientId);
    }


    @GetMapping("/payments")
    PaymentsResponseDto getPayments(@RequestParam("eventId") Long eventId, Authentication authentication) {
        Long clientId = ((JwtUserDetails) authentication.getPrincipal()).getId();
        return costService.calculatePayments(eventId, clientId);
    }

}
