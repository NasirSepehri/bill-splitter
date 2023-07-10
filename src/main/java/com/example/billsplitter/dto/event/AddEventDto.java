package com.example.billsplitter.dto.event;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddEventDto {

    @Size(min = 1, max = 100)
    private String name;
}
