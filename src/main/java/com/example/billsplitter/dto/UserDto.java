package com.example.billsplitter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {


    private Long id;

    @Size(min = 1, max = 100)
    private String username;

    @Size(min = 1, max = 100)
    private String firstName;

    @Size(min = 1, max = 100)
    private String lastName;

    @Email
    private String email;

    private List<EventDto> events = new ArrayList<>();

}
