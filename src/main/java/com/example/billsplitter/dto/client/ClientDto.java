package com.example.billsplitter.dto.client;

import com.example.billsplitter.dto.event.EventDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ClientDto {


    private UUID id;

    @Size(min = 1, max = 255)
    private String username;

    @Size(min = 1, max = 100)
    private String firstName;

    @Size(min = 1, max = 100)
    private String lastName;

    @Email
    private String email;

    private List<EventDto> events = new ArrayList<>();

}
