package com.example.billsplitter.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class EventDto {

    private Long id;

    @Size(min = 1, max = 100)
    private String name;

    private ClientDto user;

    private List<String> eventMembers = new ArrayList<>();

    private List<CostDto> costs = new ArrayList<>();

    private Instant createdDate;
}
