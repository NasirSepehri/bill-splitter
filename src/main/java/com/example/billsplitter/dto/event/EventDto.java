package com.example.billsplitter.dto.event;

import com.example.billsplitter.dto.client.ClientDto;
import com.example.billsplitter.dto.cost.CostDto;
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

    private String description;

    private ClientDto client;

    private List<MemberDto> eventMembers = new ArrayList<>();

    private List<CostDto> costs = new ArrayList<>();

    private Instant createdDate;
}
