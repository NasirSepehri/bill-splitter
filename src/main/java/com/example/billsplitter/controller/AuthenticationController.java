package com.example.billsplitter.controller;

import com.example.billsplitter.dto.AuthenticationRequestDto;
import com.example.billsplitter.dto.AuthenticationResponseDto;
import com.example.billsplitter.dto.ClientDto;
import com.example.billsplitter.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("auth")
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/sign-in")
    public AuthenticationResponseDto authenticate(@RequestBody @Valid final AuthenticationRequestDto authenticationRequestDto) {
        return authenticationService.signIn(authenticationRequestDto);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthenticationResponseDto> signUp(@RequestBody ClientDto clientDto) {
        return ResponseEntity.ok(authenticationService.signUp(clientDto));
    }

}