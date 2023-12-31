package com.example.billsplitter.dto.event;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddEventDto {

    @Size(min = 1, max = 100)
    private String name;

    private String description;

    private List<String> eventMembers = new ArrayList<>();
}
