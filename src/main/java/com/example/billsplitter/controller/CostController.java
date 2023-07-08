package com.example.billsplitter.controller;

import com.example.billsplitter.dto.CostDto;
import com.example.billsplitter.service.CostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    List<CostDto> getAllCostByEventId(@RequestParam("eventId") Long eventId) {
        return costService.getAllCostByEventId(eventId);
    }


    @PostMapping
    CostDto add(@RequestBody @Valid CostDto costDto) {
        return costService.add(costDto);
    }

    @DeleteMapping
    void delete(@RequestParam("costId") Long costId) {
        costService.delete(costId);
    }
}
