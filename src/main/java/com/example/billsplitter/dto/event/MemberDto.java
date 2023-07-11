package com.example.billsplitter.dto.event;

import lombok.Data;

import java.util.UUID;

@Data
public class MemberDto {
    private UUID uuid;
    private String username;
}
