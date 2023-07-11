package com.example.billsplitter.dto.cost;

import com.example.billsplitter.dto.event.MemberDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class AddCostDto {

    @NotNull
    private Long eventId;

    @NotNull
    private Float costAmount;

    @Size(min = 1, max = 500)
    private String costDescription;

    private List<MemberDto> splitBetween = new ArrayList<>();

    @NotNull
    private MemberDto paidBy;

}
