package com.example.billsplitter.service.impl;

import com.example.billsplitter.component.MessageByLocaleComponent;
import com.example.billsplitter.dto.AuthenticationRequestDto;
import com.example.billsplitter.dto.AuthenticationResponseDto;
import com.example.billsplitter.dto.ClientDto;
import com.example.billsplitter.entity.Client;
import com.example.billsplitter.enums.ClientRolesEnum;
import com.example.billsplitter.exception.AppException;
import com.example.billsplitter.repo.ClientRepository;
import com.example.billsplitter.service.AuthenticationService;
import com.example.billsplitter.service.JwtTokenService;
import com.example.billsplitter.service.JwtUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageByLocaleComponent messageByLocaleComponent;
    private final JwtTokenService jwtTokenService;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(ClientRepository clientRepository,
                                     PasswordEncoder passwordEncoder,
                                     MessageByLocaleComponent messageByLocaleComponent,
                                     JwtTokenService jwtTokenService,
                                     JwtUserDetailsService jwtUserDetailsService,
                                     AuthenticationManager authenticationManager) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageByLocaleComponent = messageByLocaleComponent;
        this.jwtTokenService = jwtTokenService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponseDto signUp(ClientDto clientDto) {
        Client client = new Client();
        client.setPassword(passwordEncoder.encode(clientDto.getPassword()));
        client.setUsername(clientDto.getUsername());
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setRole(ClientRolesEnum.ROLE_USER);

        clientRepository.save(client);
        var jwt = jwtTokenService.generateToken(client.getUsername());
        return AuthenticationResponseDto.builder().accessToken(jwt).build();
    }

    @Override
    public AuthenticationResponseDto signIn(AuthenticationRequestDto authenticationRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword()));
        } catch (final BadCredentialsException ex) {
            throw new AppException.AuthorizationFailed(messageByLocaleComponent.getMessage("401.unauthorized"));
        }

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequestDto.getUsername());
        final AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
        authenticationResponseDto.setAccessToken(jwtTokenService.generateToken(userDetails.getUsername()));
        return authenticationResponseDto;
    }
}
