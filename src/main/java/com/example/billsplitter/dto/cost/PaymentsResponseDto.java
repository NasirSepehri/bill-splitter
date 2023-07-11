package com.example.billsplitter.dto.cost;

import com.example.billsplitter.entity.Member;
import lombok.Data;

import java.util.Map;

@Data
public class PaymentsResponseDto {

    private Map<Member, Float> payments;
}
