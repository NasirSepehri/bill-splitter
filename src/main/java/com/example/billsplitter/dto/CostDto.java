package com.example.billsplitter.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class CostDto {

    private Long id;

    private Float costAmount;

    @Size(min = 1, max = 500)
    private String costDescription;

    private List<String> splitBetween = new ArrayList<>();

    @Size(min = 1, max = 100)
    private String paidBy;

    private EventDto event;


}
