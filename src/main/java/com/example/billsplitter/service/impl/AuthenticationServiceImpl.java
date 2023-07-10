package com.example.billsplitter.service.impl;

import com.example.billsplitter.component.MessageByLocaleComponent;
import com.example.billsplitter.dto.client.AuthenticationRequestDto;
import com.example.billsplitter.dto.client.AuthenticationResponseDto;
import com.example.billsplitter.dto.client.SignUpDto;
import com.example.billsplitter.entity.Client;
import com.example.billsplitter.enums.ClientRolesEnum;
import com.example.billsplitter.exception.AppException;
import com.example.billsplitter.repo.ClientRepository;
import com.example.billsplitter.service.AuthenticationService;
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
    private final JwtTokenServiceImpl jwtTokenServiceImpl;
    private final JwtUserDetailsServiceImpl jwtUserDetailsServiceImpl;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(ClientRepository clientRepository,
                                     PasswordEncoder passwordEncoder,
                                     MessageByLocaleComponent messageByLocaleComponent,
                                     JwtTokenServiceImpl jwtTokenServiceImpl,
                                     JwtUserDetailsServiceImpl jwtUserDetailsServiceImpl,
                                     AuthenticationManager authenticationManager) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageByLocaleComponent = messageByLocaleComponent;
        this.jwtTokenServiceImpl = jwtTokenServiceImpl;
        this.jwtUserDetailsServiceImpl = jwtUserDetailsServiceImpl;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponseDto signUp(SignUpDto signUpDto) {
        Client client = new Client();
        client.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        client.setUsername(signUpDto.getUsername());
        client.setFirstName(signUpDto.getFirstName());
        client.setLastName(signUpDto.getLastName());
        client.setEmail(signUpDto.getEmail());
        client.setRole(ClientRolesEnum.ROLE_USER);

        clientRepository.save(client);
        var jwt = jwtTokenServiceImpl.generateToken(client.getUsername());
        return AuthenticationResponseDto.builder().accessToken(jwt).build();
    }

    @Override
    public AuthenticationResponseDto signIn(AuthenticationRequestDto authenticationRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword()));
        } catch (final BadCredentialsException ex) {
            throw new AppException.AuthorizationFailed(messageByLocaleComponent.getMessage("username.or.password.incorrect"));
        }

        final UserDetails userDetails = jwtUserDetailsServiceImpl.loadUserByUsername(authenticationRequestDto.getUsername());
        final AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
        authenticationResponseDto.setAccessToken(jwtTokenServiceImpl.generateToken(userDetails.getUsername()));
        return authenticationResponseDto;
    }
}
