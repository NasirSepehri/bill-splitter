package com.example.billsplitter.dto.cost;

import lombok.Data;

import java.util.Map;

@Data
public class PaymentsResponseDto {

    private Map<String, Float> payments;
}
