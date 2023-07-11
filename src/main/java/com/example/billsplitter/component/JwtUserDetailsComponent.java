package com.example.billsplitter.component;

import com.example.billsplitter.dto.client.JwtUserDetails;
import com.example.billsplitter.entity.Client;
import com.example.billsplitter.enums.ClientRolesEnum;
import com.example.billsplitter.exception.AppException;
import com.example.billsplitter.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class JwtUserDetailsComponent implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final MessageByLocaleComponent messageByLocaleComponent;

    @Autowired
    public JwtUserDetailsComponent(ClientRepository clientRepository,
                                   MessageByLocaleComponent messageByLocaleComponent) {
        this.clientRepository = clientRepository;
        this.messageByLocaleComponent = messageByLocaleComponent;
    }


    @Override
    public JwtUserDetails loadUserByUsername(final String username) {
        final Client client = clientRepository.findByUsername(username).orElseThrow(
                () -> new AppException.NotFound(messageByLocaleComponent.getMessage("user.not.found")));
        final List<SimpleGrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority(ClientRolesEnum.ROLE_USER.name()));
        return new JwtUserDetails(client.getId(), username, client.getPassword(), roles);
    }

}