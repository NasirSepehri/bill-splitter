package com.example.billsplitter.controller;

import com.example.billsplitter.dto.cost.CostDto;
import com.example.billsplitter.dto.cost.PaymentsResponseDto;
import com.example.billsplitter.service.CostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    List<CostDto> getAllCostByEventId(@RequestParam("eventId") Long eventId, Principal principal) {
        return costService.getAllCostByEventId(eventId, principal.getName());
    }


    @PostMapping
    CostDto add(@RequestBody @Valid CostDto costDto, Principal principal) {
        return costService.add(costDto, principal.getName());
    }

    @DeleteMapping
    String delete(@RequestParam("costId") Long costId, Principal principal) {
        return costService.delete(costId, principal.getName());
    }


    @GetMapping("/payments")
    PaymentsResponseDto getPayments(@RequestParam("eventId") Long eventId, Principal principal) {
        return costService.calculatePayments(eventId, principal.getName());
    }

}
