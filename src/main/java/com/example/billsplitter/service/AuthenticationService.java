package com.example.billsplitter.service;

import com.example.billsplitter.dto.client.AuthenticationRequestDto;
import com.example.billsplitter.dto.client.AuthenticationResponseDto;
import com.example.billsplitter.dto.client.SignUpDto;

public interface AuthenticationService {
    AuthenticationResponseDto signUp(SignUpDto signUpDto);

    AuthenticationResponseDto signIn(AuthenticationRequestDto authenticationRequestDto);
}
