package com.example.billsplitter.controller;

import com.example.billsplitter.dto.client.AuthenticationRequestDto;
import com.example.billsplitter.dto.client.AuthenticationResponseDto;
import com.example.billsplitter.dto.client.SignUpDto;
import com.example.billsplitter.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Login Client",
            description = "Client enter username and password for login. The response is the Token ID")
    @PostMapping("/sign-in")
    public AuthenticationResponseDto authenticate(@RequestBody @Valid final AuthenticationRequestDto authenticationRequestDto) {
        return authenticationService.signIn(authenticationRequestDto);
    }

    @Operation(
            summary = "Add a new client",
            description = "Add a new Client by ClientDto field such as a username, first name and etc. The response is clientDto with client id")
    @PostMapping("/sign-up")
    public ResponseEntity<AuthenticationResponseDto> signUp(@RequestBody @Valid SignUpDto signUpDto) {
        return ResponseEntity.ok(authenticationService.signUp(signUpDto));
    }

}