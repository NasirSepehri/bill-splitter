package com.example.billsplitter.dto.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberDto {

    @NotNull
    private Long eventId;

    @NotBlank
    private String memberUsername;
}
