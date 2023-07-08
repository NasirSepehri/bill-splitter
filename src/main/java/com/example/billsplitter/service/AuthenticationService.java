package com.example.billsplitter.service;

import com.example.billsplitter.dto.AuthenticationRequestDto;
import com.example.billsplitter.dto.AuthenticationResponseDto;
import com.example.billsplitter.dto.ClientDto;

public interface AuthenticationService {
    AuthenticationResponseDto signUp(ClientDto clientDto);

    AuthenticationResponseDto signIn(AuthenticationRequestDto authenticationRequestDto);
}
