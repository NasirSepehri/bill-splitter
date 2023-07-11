package com.example.billsplitter.dto.event;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditEventDto {

    @NotNull
    private Long eventId;

    @Size(min = 1, max = 100)
    private String name;

    private String description;

}
