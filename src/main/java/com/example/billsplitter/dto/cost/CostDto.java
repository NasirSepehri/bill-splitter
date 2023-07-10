package com.example.billsplitter.dto.cost;

import com.example.billsplitter.dto.event.EventDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class CostDto {

    private Long id;

    @NotNull
    private Float costAmount;

    @Size(min = 1, max = 500)
    private String costDescription;

    private List<String> splitBetween = new ArrayList<>();

    @Size(min = 1, max = 100)
    private String paidBy;

    private EventDto event;


}
