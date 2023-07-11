package com.example.billsplitter.dto.client;

import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
public class JwtUserDetails extends User {

    public final UUID id;

    public JwtUserDetails(final UUID id, final String username, final String hash,
                          final Collection<? extends GrantedAuthority> authorities) {
        super(username, hash, authorities);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
